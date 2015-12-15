package entityBeans;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Remote;

@Remote
public interface RetrieveInterface {

	void start();

	List<PatientInterface> pByName(String name);

	List<TreatmentInterface> pTreatments(int id);

	List<PatientInterface> allPatients();
	
	TreatmentInterface newTreatment(boolean active, String diagnosis, int duration, BigDecimal price,
			 BigDecimal extrafees, int patientId, int sMemberId);

	StaffMemberInterface getStaffMemberById(int id);

	PatientInterface getPatientById(int id);

	List<StaffMemberInterface> allStaffMembers();

	TreatmentInterface getTreatmentById(int id);

	void saveTreatment(TreatmentInterface treat);

	void savePatient(PatientInterface p);

}