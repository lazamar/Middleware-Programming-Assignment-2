package client;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import entityBeans.PatientInterface;
import entityBeans.StaffMemberInterface;
import entityBeans.TreatmentInterface;

public class DataContainer {
	private static List<java.util.List<TreatmentInterface>> tLists; //Two dimensional List
	private static List<PatientInterface> pList;
	private static List<StaffMemberInterface> smList;
	private static TreatmentInterface newTreatment = null;
	private static List<PatientInterface> modifiedPatients = new ArrayList<PatientInterface>();
	
	public static java.util.List<java.util.List<TreatmentInterface>> getTLists() {
		return tLists;
	}
	public static void setTLists(java.util.List<java.util.List<TreatmentInterface>> tLists) {
		DataContainer.tLists = tLists;
	}
	public static java.util.List<StaffMemberInterface> getSmList() {
		return smList;
		
	}
	public static void setSmList(java.util.List<StaffMemberInterface> smList) {
		DataContainer.smList = smList;
	}
	public static java.util.List<PatientInterface> getPList() {
		return pList;
	}
	public static void setPList(java.util.List<PatientInterface> pList) {
		DataContainer.pList = pList;
	}
	
	public static void newTreatment(boolean active, String diagnosis, int duration, BigDecimal price,
			 BigDecimal extrafees, int patientId, int sMemberId) {
		DataContainer.newTreatment = ServerHandler.newTreatment(active, diagnosis, duration, price, extrafees, patientId, sMemberId);
		ServerHandler.modificationAdded();
	}
	
	public static TreatmentInterface getNewTreatment() {
		return newTreatment;
	}
	
	public static void setModifiedPatient(PatientInterface p){
		modifiedPatients.add(p);
		ServerHandler.modificationAdded();
	}
	
	public static List<PatientInterface> getModifiedPatients() {
		return modifiedPatients;
	}
	
	public static void resetChanges() {
		newTreatment = null;
		modifiedPatients.clear();
	}


}
