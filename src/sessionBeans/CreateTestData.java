package sessionBeans;

import java.math.BigDecimal;
import java.util.Date;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import entityBeans.Patient;
import entityBeans.StaffMember;
import entityBeans.StaffMemberInterface;
import entityBeans.Treatment;
import entityBeans.TreatmentInterface;

@Stateless
@Remote(CreateTestDataInterface.class)
public class CreateTestData implements CreateTestDataInterface {
	@PersistenceContext
	private static EntityManager entitymanager;
	
	public void doIt() {
		
		// Create Patient
		Patient p1 = new Patient();
		p1.setName("John Smith");
		p1.setRegistrationDate(new Date());
		p1.setAddress("Ealing Broadway");
		p1.setHasInsurance(true);

		Patient p2 = new Patient();
		p2.setName("Karl Marx");
		p2.setRegistrationDate(new Date());
		p2.setAddress("Highgate Cementery");
		p2.setHasInsurance(true);

		Patient p3 = new Patient();
		p3.setName("Ludwig Feuerbach");
		p3.setRegistrationDate(new Date());
		p3.setAddress("Prussia");
		p3.setHasInsurance(false);

		Patient p4 = new Patient();
		p4.setName("Soren Kierkegaard");
		p4.setRegistrationDate(new Date());
		p4.setAddress("Denmark");
		p4.setHasInsurance(true);

		Patient p5 = new Patient();
		p5.setName("Friedrich Hegel");
		p5.setRegistrationDate(new Date());
		p5.setAddress("Prussia");
		p5.setHasInsurance(false);

		Patient p6 = new Patient();
		p6.setName("Martin-Heidegger");
		p6.setRegistrationDate(new Date());
		p6.setAddress("Freiburg im Breisgau");
		p6.setHasInsurance(false);

		// Create Practitioners
		StaffMemberInterface prac1 = new StaffMember();
		prac1.setName("Mother Theresa");
		prac1.setRole("Nurse");

		// Create Practitioners
		StaffMemberInterface prac2 = new StaffMember();
		prac2.setName("Gregory House");
		prac2.setRole("Doctor");

		// Create Practitioners
		StaffMemberInterface prac3 = new StaffMember();
		prac3.setName("Gabriel John Utterson");
		prac3.setRole("Doctor");

		// Create Treatments
		TreatmentInterface treat1 = new Treatment();
		treat1.setActive(true);
		treat1.setDiagnosis("Tuberculosis");
		treat1.setEstimatedDuration(40);
		treat1.setStartDate(new Date());
		treat1.setEndDate(new Date());
		treat1.setEstimatedPrice(new BigDecimal(1000.99));
		treat1.setPatient(p1);
		treat1.setPractitioner(prac1);

		TreatmentInterface treat2 = new Treatment();
		treat2.setActive(true);
		treat2.setDiagnosis("Flu");
		treat2.setEstimatedDuration(15);
		treat2.setStartDate(new Date());
		treat2.setEndDate(new Date());
		treat2.setEstimatedPrice(new BigDecimal(1200.99));
		treat2.setPatient(p2);
		treat2.setPractitioner(prac2);

		TreatmentInterface treat3 = new Treatment();
		treat3.setActive(true);
		treat3.setDiagnosis("Meningitis");
		treat3.setEstimatedDuration(7);
		treat3.setStartDate(new Date());
		treat3.setEndDate(new Date());
		treat3.setEstimatedPrice(new BigDecimal(150.99));
		treat3.setPatient(p3);
		treat3.setPractitioner(prac3);

		TreatmentInterface treat4 = new Treatment();
		treat4.setActive(true);
		treat4.setDiagnosis("Sore throat.");
		treat4.setEstimatedDuration(16);
		treat4.setStartDate(new Date());
		treat4.setEndDate(new Date());
		treat4.setEstimatedPrice(new BigDecimal(604.99));
		treat4.setPatient(p4);
		treat4.setPractitioner(prac1);

		TreatmentInterface treat5 = new Treatment();
		treat5.setActive(true);
		treat5.setDiagnosis("Grumpyness");
		treat5.setEstimatedDuration(4);
		treat5.setStartDate(new Date());
		treat5.setEndDate(new Date());
		treat5.setEstimatedPrice(new BigDecimal(899.99));
		treat5.setPatient(p5);
		treat5.setPractitioner(prac2);

		TreatmentInterface treat6 = new Treatment();
		treat6.setActive(true);
		treat6.setDiagnosis("Headache");
		treat6.setEstimatedDuration(12);
		treat6.setStartDate(new Date());
		treat6.setEndDate(new Date());
		treat6.setEstimatedPrice(new BigDecimal(433.99));
		treat6.setPatient(p6);
		treat6.setPractitioner(prac3);

		entitymanager.persist(p1);
		entitymanager.persist(p2);
		entitymanager.persist(p3);
		entitymanager.persist(p4);
		entitymanager.persist(p5);
		entitymanager.persist(p6);
		entitymanager.persist(prac1);
		entitymanager.persist(prac2);
		entitymanager.persist(prac3);
		entitymanager.persist(treat1);
		entitymanager.persist(treat2);
		entitymanager.persist(treat3);
		entitymanager.persist(treat4);
		entitymanager.persist(treat5);
		entitymanager.persist(treat6);

		System.out.println("Success!! I think everything was saved.");
	}
}
