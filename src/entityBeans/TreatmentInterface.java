package entityBeans;

import java.math.BigDecimal;
import java.util.Date;

public interface TreatmentInterface{

	StaffMemberInterface getPractitioner();

	void setPractitioner(StaffMemberInterface practitioner);

	Date getEndDate();

	void setEndDate(Date endDate);

	int getId();

	void setId(int id);

	String getDiagnosis();

	void setDiagnosis(String diagnosis);

	BigDecimal getEstimatedPrice();

	void setEstimatedPrice(BigDecimal estimatedPrice);

	BigDecimal getAdditionalFees();

	void setAdditionalFees(BigDecimal additionalFees);

	int getEstimatedDuration();

	void setEstimatedDuration(int estimatedDuration);

	boolean isActive();

	void setActive(boolean active);

	PatientInterface getPatient();

	void setPatient(PatientInterface patient);

	Date getStartDate();

	void setStartDate(Date startDate);

}