package sessionBeans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import entityBeans.Patient;
import entityBeans.PatientInterface;
import entityBeans.StaffMember;
import entityBeans.StaffMemberInterface;
import entityBeans.Treatment;
import entityBeans.TreatmentInterface;

@Stateless
@Remote(RetrieveInterface.class)
public class Retrieve implements Serializable, RetrieveInterface {

	
	private static final long serialVersionUID = 1L;
	@PersistenceContext
	private static EntityManager entitymanager;
	private static Lock lock = new ReentrantLock();
	
	// -------------- Static Methods ----------------------
	protected Retrieve() {
		super();
		start();
	}

	public static void end() {
	}
	
	// -------------- Interface Methods ----------------------
	
	@Override
	public void start() {
	}
	
	//======= Retrieve Objects =========

	@Override
	public List<PatientInterface> allPatients() {
		
		Query query = entitymanager.createQuery("Select p from Patient p");
		@SuppressWarnings("unchecked")
		List<PatientInterface> pList = query.getResultList();

		for (PatientInterface p : pList) {
			System.out.println("Patient: " + p.getName() + ", with id: "
					+ p.getId());
		}

		return pList;
	}
	
	@Override
	public List<StaffMemberInterface> allStaffMembers() {
		Query query = entitymanager.createQuery("Select sm from StaffMember sm");
		@SuppressWarnings("unchecked")
		List<StaffMemberInterface> smList = query.getResultList();
		return smList;
	}
	@Override
	public List<TreatmentInterface> pTreatments(int id) {
		Query query = entitymanager
				.createQuery("Select t from Treatment t where t.patient.id = :patient");
		query.setParameter("patient", id);
		@SuppressWarnings("unchecked")
		List<TreatmentInterface> tList = query.getResultList();

		for (TreatmentInterface t : tList) {
			System.out.println("Treatement: " + t.getId()
					+ ", with start date: " + t.getStartDate().toString());
		}

		return tList;
	}

	@Override
	public List<PatientInterface> pByName(String name) {
		Query query = entitymanager
				.createQuery("Select p from Patient p where p.name = :name");
		query.setParameter("name", name);
		@SuppressWarnings("unchecked")
		List<PatientInterface> pList = query.getResultList();

		for (PatientInterface p : pList) {
			System.out.println("Patient: " + p.getName() + ", with id: "
					+ p.getId());
		}

		return pList;
	}

	
	@Override
	public StaffMemberInterface getStaffMemberById(int id) {
		// Prepare query
		Query query = entitymanager
				.createQuery("Select sm from StaffMember sm where sm.id = :id");
		query.setParameter("id", id);
		//Get Results
		@SuppressWarnings("unchecked")
		List<StaffMemberInterface> smList = query.getResultList();
		StaffMemberInterface sMember = null;
		if(smList.size() > 0){
			 sMember = smList.get(0);
		}
		return sMember;
	}
	
	@Override
	public PatientInterface getPatientById(int id) {
		// Prepare query
		Query query = entitymanager
				.createQuery("Select p from Patient p where p.id = :id");
		query.setParameter("id", id);
		//Get Results
		@SuppressWarnings("unchecked")
		List<PatientInterface> pList = query.getResultList();
		PatientInterface patient = null;
		if(pList.size() > 0){
			patient = pList.get(0);			
		}
		return patient;
	}
	
	@Override
	public TreatmentInterface getTreatmentById(int id) {
		// Prepare query
		Query query = entitymanager
				.createQuery("Select t from Treatment t where t.id = :id");
		query.setParameter("id", id);
		//Get Results
		@SuppressWarnings("unchecked")
		List<TreatmentInterface> tList = query.getResultList();
		TreatmentInterface treat = null;
		if(tList.size() > 0){
			treat = tList.get(0);			
		}
		return treat;
	}
	
	//======= Create/Modify Objects =========
	// These modules create change, so all of them must update lastUpdate
	@Override
	public TreatmentInterface newTreatment(boolean active, String diagnosis, int duration, BigDecimal price,
							 BigDecimal extrafees, int patientId, int sMemberId){
		
		TreatmentInterface treat1 = new Treatment();
		treat1.setActive(active);
		treat1.setEstimatedDuration(duration);
		treat1.setStartDate(new Date());
		treat1.setEndDate(new Date());
		treat1.setEstimatedPrice(price);
		treat1.setDiagnosis(diagnosis);
		// TODO: Make get patient and sMember by their ID.
		PatientInterface patient = getPatientById(patientId);
		StaffMemberInterface sMember = getStaffMemberById(sMemberId);
		treat1.setPatient(patient);
		treat1.setPractitioner(sMember);
		treat1.setAdditionalFees(extrafees);
		return treat1;
	}
	
	@Override
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
	
	@Override 
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

}
