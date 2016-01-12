package messageBeans;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.ejb.MessageDriven;
import javax.ejb.ActivationConfigProperty;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import entityBeans.Patient;
import entityBeans.PatientInterface;
import entityBeans.StaffMember;
import entityBeans.Treatment;
import entityBeans.TreatmentInterface;


@MessageDriven(activationConfig =
        {
        @ActivationConfigProperty(propertyName="destinationType", propertyValue="javax.jms.Queue"),
        @ActivationConfigProperty(propertyName="destination", propertyValue="queue/treatments-server/patients")
        })
public class Save implements MessageListener
{
	@PersistenceContext
	private static EntityManager entitymanager;
    private static Lock lock = new ReentrantLock();
   
   public void onMessage(Message recvMsg)
   {
	   ObjectMessage objectMessage = (ObjectMessage) recvMsg;
	   PatientInterface p;
	   TreatmentInterface t;
	try {
		MessageWrapper msg = (MessageWrapper) objectMessage.getObject();
		p = msg.getPatient();
		t = msg.getTreatment();
		if (p != null) {
			savePatient(p);
		 System.out.println("----------------");
	        System.out.println("Message received and Patient " + p.getName() + " saved.");
	      System.out.println("----------------");
		} else {
			saveTreatment(t);
			System.out.println("----------------");
	        System.out.println("Message received and Treatment " + t.getDiagnosis() + " saved.");
	      System.out.println("----------------");
		}
		   
     
	} catch (JMSException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  
   }
   
   public void savePatient(PatientInterface p){
		lock.lock(); // Acquire the lock

		Patient modifiedPatient = entitymanager.find(Patient.class, p.getId());
		if(modifiedPatient != null){
			modifiedPatient.setAddress(p.getAddress());
			modifiedPatient.setHasInsurance(p.isHasInsurance());
			modifiedPatient.setName(p.getName());
			modifiedPatient.setRegistrationDate(p.getRegistrationDate());
		} else {	
			entitymanager.persist(p);
		}

		lock.unlock();
	}

	public void saveTreatment(TreatmentInterface treat){
		lock.lock(); // Acquire the lock
		boolean existingRecord = true;

		Treatment newTreat = entitymanager.find(Treatment.class, treat.getId());	
		if(newTreat == null){
			newTreat = new Treatment();
			existingRecord = false;
		}
		newTreat.setActive(treat.isActive());
		newTreat.setAdditionalFees(treat.getAdditionalFees());
		newTreat.setEstimatedPrice(treat.getEstimatedPrice());
		newTreat.setDiagnosis(treat.getDiagnosis());
		newTreat.setEndDate(treat.getEndDate());
		newTreat.setEstimatedDuration(treat.getEstimatedDuration());
		newTreat.setPatient(entitymanager.find(Patient.class, treat.getPatient().getId()));
		newTreat.setPractitioner(entitymanager.find(StaffMember.class, treat.getPractitioner().getId()));
		newTreat.setStartDate(treat.getStartDate());
		if(!existingRecord){
			entitymanager.persist(newTreat);
		}
		lock.unlock();
	}
}

