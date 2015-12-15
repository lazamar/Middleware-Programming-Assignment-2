package client;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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
			//Wait if the user is making changes
			while (!changesToBeCommitted()) {
				System.out.println("Waiting for user changes to save data");
				newModification.await();
			}
			System.out.println("Saving data..");
			// Save the new Treatment
			TreatmentInterface newTreatment = DataContainer.getNewTreatment();
			if (newTreatment != null){
				retrieve.saveTreatment(newTreatment);
			}
			
			//Save patient modifications
			List<PatientInterface> modifiedPatients = DataContainer.getModifiedPatients();
			for(PatientInterface p: modifiedPatients){
				retrieve.savePatient(p);
			}
			
			DataContainer.resetChanges();
			

		} catch (InterruptedException ex) {

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
