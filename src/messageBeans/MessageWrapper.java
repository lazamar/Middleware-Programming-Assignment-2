package messageBeans;

import java.io.Serializable;

import entityBeans.PatientInterface;
import entityBeans.TreatmentInterface;

public class MessageWrapper implements MessageWrapperInterface, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PatientInterface p;
	private TreatmentInterface t;
	
	public MessageWrapper(PatientInterface pi, TreatmentInterface ti){
		this.setPatient(pi);
		this.setT(ti);
	}

	public PatientInterface getPatient() {
		return p;
	}

	public void setPatient(PatientInterface p) {
		this.p = p;
	}

	public TreatmentInterface getTreatment() {
		return t;
	}

	public void setT(TreatmentInterface t) {
		this.t = t;
	}

}
