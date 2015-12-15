package entityBeans;

import java.io.Serializable;

import javax.ejb.Remote;
import javax.persistence.Entity;

@Entity
@Remote(PatientInterface.class)
public class Patient extends Client implements Serializable, PatientInterface{

	private static final long serialVersionUID = 1L;

	public Patient() {
		super();
		// TODO Auto-generated constructor stub
	}

	private boolean hasInsurance;

	@Override
	public boolean isHasInsurance() {
		return hasInsurance;
	}

	@Override
	public void setHasInsurance(boolean hasInsurance) {
		this.hasInsurance = hasInsurance;
	}
}
