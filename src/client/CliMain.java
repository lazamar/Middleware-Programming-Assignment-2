package client;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

import entityBeans.PatientInterface;
import entityBeans.StaffMemberInterface;
import entityBeans.TreatmentInterface;
import sessionBeans.RetrieveInterface;

public class CliMain {
	static RetrieveInterface retrieve;
	static String divider = "=====================================================";
	
	public static void main(String[] args) throws NotBoundException, IOException {

		String name = "rmi://localhost/RetServer";
		retrieve =  (RetrieveInterface) Naming.lookup(name);

		List<PatientInterface> pList = retrieve.allPatients();
		int i = 1;
		for (PatientInterface patient: pList) {
			System.out.println(divider);
			System.out.println(i + "- \t ID: \t" + patient.getId());
			System.out.println("\t Patient: \t" + patient.getName());
			System.out.println("\t Address: \t" + patient.getAddress());
			System.out.println(divider);
			i++;
		}
		

//		List<TreatmentInterface> tList = retrieve.pTreatments(pList.get(2).getId());

		
//		retrieve.newTreatment(true, 20, new BigDecimal(1489), patient.getId(), practitioner.getId());
		
		String option = null;
		int index = 0;
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		while(option != "q") {
			System.out.println("Choose an index");
			option = reader.readLine();
			index = ((Integer.parseInt(option)) -1) % pList.size();
			moreInfo(pList.get(index));
		}
	}
	
	public static void moreInfo(PatientInterface patient) throws RemoteException {
		System.out.println(divider);
		System.out.println("Patient: \t\t" + patient.getName());
		System.out.println("Address: \t\t" + patient.getAddress());
		System.out.println("Registration Date: \t" + patient.getRegistrationDate());
		System.out.println("Has Insurance: \t\t" + patient.isHasInsurance());
		System.out.println("\n---Treatments---");
		
		List<TreatmentInterface> tList = retrieve.pTreatments(patient.getId());
		for(TreatmentInterface treat: tList){
			StaffMemberInterface sMember = treat.getPractitioner();
			
			System.out.println("Treatment ID: \t" + treat.getId() );
			System.out.println("Practitioner:");
			System.out.println("\tName: \t" + sMember.getName());
			System.out.println("\tRole: \t" + sMember.getRole());
			System.out.println("Estimated Duration: \t" + treat.getEstimatedDuration() );
			System.out.println("Estimated Price: \t£" + treat.getEstimatedPrice() );
			System.out.println("Active: \t\t" + treat.isActive());
			if(treat.getDiagnosis() != null) {
				System.out.println("Diagnosis: \t" + treat.getDiagnosis());
			}
			if(treat.getAdditionalFees() != null) {
				System.out.println("Additional Fees: \t£" + treat.getAdditionalFees());
			}	
			if(tList.size() > 1){
				System.out.println("\n----------------");
			}
		}
		System.out.println(divider);		
	}
}
