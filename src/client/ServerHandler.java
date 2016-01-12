package client;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import messageBeans.MessageWrapper;
import messageBeans.MessageWrapperInterface;
import entityBeans.PatientInterface;
import entityBeans.TreatmentInterface;
import sessionBeans.RetrieveInterface;

public class ServerHandler {
	private static Lock lock = new ReentrantLock();
	private static Condition newModification = lock.newCondition();
	private static RetrieveInterface retrieve;


	public static void setServer(RetrieveInterface retrieveObj){
		retrieve = retrieveObj;
	}

	public static void loadData()  {
		// Get all patients in the system
		DataContainer.setPList(retrieve.allPatients());

		// Get the treatments of all patients.
		List<List<TreatmentInterface>>tLists = new ArrayList<java.util.List<TreatmentInterface>>();
		for (PatientInterface patient: DataContainer.getPList()){
			tLists.add(retrieve.pTreatments(patient.getId()));
		}

		DataContainer.setTLists(tLists);
		//Get all practitioners
		DataContainer.setSmList(retrieve.allStaffMembers());

	}

	public static void commit()  {
		lock.lock(); // Acquire the lock
		try {
			// Setup queue
			System.out.println("Preparing message queue.");
			QueueConnection cnn = null;
			QueueSender sender = null;
			QueueSession session = null;
			Properties prop = new Properties();
			prop.put(Context.INITIAL_CONTEXT_FACTORY,   "org.jnp.interfaces.NamingContextFactory");
			prop.put(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
			prop.put(Context.PROVIDER_URL, "localhost");
			Context ctx = new InitialContext(prop);
			Queue queue = (Queue) ctx.lookup("queue/treatments-server/patients");
			QueueConnectionFactory factory = (QueueConnectionFactory) ctx.lookup("ConnectionFactory");
			cnn = factory.createQueueConnection();
			session = cnn.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);
			sender = session.createSender(queue);

			//Wait if the user is making changes
			while (!changesToBeCommitted()) {
				System.out.println("Waiting for user changes to save data");
				newModification.await();
			}
			System.out.println("Saving data..");
			// Save the new Treatment
			TreatmentInterface newTreatment = DataContainer.getNewTreatment();
			if (newTreatment != null){
				MessageWrapperInterface msg = new MessageWrapper(null, newTreatment);
				ObjectMessage objectMessage = session.createObjectMessage((Serializable) msg);
				sender.send(objectMessage); 
				System.out.println("Message sent. Treatment saved.");
			}



			//Save patient modifications
			List<PatientInterface> modifiedPatients = DataContainer.getModifiedPatients();

			for(PatientInterface p: modifiedPatients){
				// Send message
				MessageWrapperInterface msg = new MessageWrapper(p, null);
				ObjectMessage objectMessage = session.createObjectMessage((Serializable) msg);
				sender.send(objectMessage); 
				System.out.println("Message sent. Patient saved.");
			}

			DataContainer.resetChanges();


		} catch (InterruptedException | NamingException | JMSException ex) {
			System.out.println("Error in message queue.");
		} finally {
			lock.unlock(); // Release the lock
		}
	}

	public static void modificationAdded() {
		lock.lock();
		newModification.signalAll();
		lock.unlock();
	}

	public static boolean changesToBeCommitted(){
		boolean response = false;
		if(DataContainer.getNewTreatment() != null || DataContainer.getModifiedPatients().size() > 0){
			response = true;
		}
		return response;
	}


	public static TreatmentInterface newTreatment(boolean active, String diagnosis, int duration, BigDecimal price,
			BigDecimal extrafees, int patientId, int sMemberId) {
		return retrieve.newTreatment(active, diagnosis, duration, price, extrafees, patientId, sMemberId);
	}
}
