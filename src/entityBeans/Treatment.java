package entityBeans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.ejb.Remote;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Remote(TreatmentInterface.class)
public class Treatment implements Serializable, TreatmentInterface{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@ManyToOne
	private Patient patient;

	@ManyToOne
	private StaffMember practitioner;
	@Basic(optional = false)
	private String diagnosis;

	private BigDecimal estimatedPrice, additionalFees;
	private int estimatedDuration;
	private boolean active;
	@Temporal(TemporalType.DATE)
	private Date startDate;

	@Basic(optional = false)
	@Temporal(TemporalType.DATE)
	private Date endDate;

	public Treatment() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public StaffMemberInterface getPractitioner() {
		return practitioner;
	}

	@Override
	public void setPractitioner(StaffMemberInterface practitioner) {
		this.practitioner = (StaffMember) practitioner;
	}

	@Override
	public Date getEndDate() {
		return endDate;
	}

	@Override
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String getDiagnosis() {
		return diagnosis;
	}

	@Override
	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}

	@Override
	public BigDecimal getEstimatedPrice() {
		return estimatedPrice;
	}

	@Override
	public void setEstimatedPrice(BigDecimal estimatedPrice) {
		this.estimatedPrice = estimatedPrice;
	}

	@Override
	public BigDecimal getAdditionalFees() {
		return additionalFees;
	}

	@Override
	public void setAdditionalFees(BigDecimal additionalFees) {
		this.additionalFees = additionalFees;
	}

	@Override
	public int getEstimatedDuration() {
		return estimatedDuration;
	}

	@Override
	public void setEstimatedDuration(int estimatedDuration) {
		this.estimatedDuration = estimatedDuration;
	}

	@Override
	public boolean isActive() {
		return active;
	}

	@Override
	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public PatientInterface getPatient() {
		return patient;
	}

	@Override
	public void setPatient(PatientInterface patient) {
		this.patient = (Patient) patient;
	}

	@Override
	public Date getStartDate() {
		return startDate;
	}

	@Override
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
}
