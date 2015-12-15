package client;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
//import org.eclipse.wb.swt.SWTResourceManager;

import entityBeans.PatientInterface;
import entityBeans.StaffMemberInterface;
import entityBeans.TreatmentInterface;

class MainWindow implements Runnable{

	protected Shell shlLhm;
	private Text txtName;
	private Text txtAddress;
	private Text txtHasInsurance;
	private Text txtId;
	private Text txtDuration;
	private Text txtCost;
	private Text txtAdditionalFees;
	private Text txtPractitioner;
	private Text txtDiagnosis;
	private Text txtActive;
	private List lstTreatments;
	private List lstPatients;
	private DateTime dtRegDate;
	private java.util.List<java.util.List<TreatmentInterface>> tLists; //Two dimensional List
	private java.util.List<PatientInterface> pList;
	private java.util.List<StaffMemberInterface> smList;

	/**
	 * @wbp.parser.entryPoint
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void open() throws RemoteException {
		Display display = Display.getDefault();
		createContents();
		shlLhm.open();
		shlLhm.layout();
		while (!shlLhm.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	protected void createContents() throws RemoteException {

		shlLhm = new Shell();
		shlLhm.addShellListener(new ShellAdapter() {
			@Override
			public void shellClosed(ShellEvent e) {
				System.out.println("Closed!");
			}
		});
//		shlLhm.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
		shlLhm.setSize(704, 380);
		shlLhm.setText("LH Medical");
		shlLhm.setLayout(null);
		lstPatients = new List(shlLhm, SWT.BORDER);
		lstPatients.setBounds(115, 10, 238, 66);
		lstPatients.setItems(new String[] {});
//		lstPatients.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));

		txtName = new Text(shlLhm, SWT.BORDER);
		txtName.setBounds(115, 105, 238, 29);

		txtAddress = new Text(shlLhm, SWT.BORDER);
		txtAddress.setBounds(115, 140, 238, 29);

		txtHasInsurance = new Text(shlLhm, SWT.BORDER);
		txtHasInsurance.setBounds(115, 210, 75, 29);

		Label lblName = new Label(shlLhm, SWT.NONE);
		lblName.setBounds(0, 105, 109, 17);
		lblName.setAlignment(SWT.RIGHT);
		lblName.setText("Name");

		Label lblAddress = new Label(shlLhm, SWT.NONE);
		lblAddress.setBounds(0, 141, 109, 17);
		lblAddress.setAlignment(SWT.RIGHT);
		lblAddress.setText("Address");

		Label lblRegistrationDate = new Label(shlLhm, SWT.NONE);
		lblRegistrationDate.setBounds(0, 175, 109, 17);
		lblRegistrationDate.setAlignment(SWT.RIGHT);
		lblRegistrationDate.setText("Registration Date");

		Label lblHasInsurance = new Label(shlLhm, SWT.NONE);
		lblHasInsurance.setBounds(0, 210, 109, 17);
		lblHasInsurance.setAlignment(SWT.RIGHT);
		lblHasInsurance.setText("Has Insurance");

		final Button btnModify = new Button(shlLhm, SWT.NONE);
		
		btnModify.setBounds(244, 313, 109, 27);
		btnModify.setText("Modify");

		Button btnAddTreatment = new Button(shlLhm, SWT.NONE);
		
		btnAddTreatment.setBounds(115, 313, 123, 27);
		btnAddTreatment.setText("Add Treatment");

		Label lblPatients = new Label(shlLhm, SWT.NONE);
		lblPatients.setAlignment(SWT.RIGHT);
		lblPatients.setBounds(47, 10, 62, 17);
		lblPatients.setText("Patients");

		Label label = new Label(shlLhm, SWT.SEPARATOR | SWT.HORIZONTAL);
		label.setBounds(10, 82, 678, 17);

		lstTreatments = new List(shlLhm, SWT.BORDER);
		lstTreatments.setBounds(115, 245, 238, 62);

		txtId = new Text(shlLhm, SWT.NONE);
		txtId.setEnabled(false);
		txtId.setBounds(471, 105, 44, 29);

		txtDuration = new Text(shlLhm, SWT.BORDER);
		txtDuration.setBounds(619, 210, 69, 29);

		txtCost = new Text(shlLhm, SWT.BORDER);
		txtCost.setBounds(471, 210, 75, 29);

		txtAdditionalFees = new Text(shlLhm, SWT.BORDER);
		txtAdditionalFees.setBounds(471, 245, 75, 29);

		Label lblNewLabel = new Label(shlLhm, SWT.NONE);
		lblNewLabel.setAlignment(SWT.RIGHT);
		lblNewLabel.setBounds(0, 245, 109, 17);
		lblNewLabel.setText("Treatments");

		txtPractitioner = new Text(shlLhm, SWT.BORDER);
		txtPractitioner.setBounds(471, 280, 217, 29);

		txtDiagnosis = new Text(shlLhm, SWT.BORDER | SWT.MULTI);
		txtDiagnosis.setBounds(471, 140, 217, 62);

		Label lblId = new Label(shlLhm, SWT.NONE);
		lblId.setAlignment(SWT.RIGHT);
		lblId.setBounds(377, 105, 88, 17);
		lblId.setText("ID");

		Label lblActive = new Label(shlLhm, SWT.NONE);
		lblActive.setText("Active");
		lblActive.setAlignment(SWT.RIGHT);
		lblActive.setBounds(551, 105, 62, 17);

		txtActive = new Text(shlLhm, SWT.BORDER);
		txtActive.setBounds(619, 105, 69, 29);

		Label lblCost = new Label(shlLhm, SWT.NONE);
		lblCost.setText("Cost");
		lblCost.setAlignment(SWT.RIGHT);
		lblCost.setBounds(377, 210, 88, 17);

		Label lblAdditionalFees = new Label(shlLhm, SWT.NONE);
		lblAdditionalFees.setText("Additional Fees");
		lblAdditionalFees.setAlignment(SWT.RIGHT);
		lblAdditionalFees.setBounds(362, 245, 103, 17);

		Label lblDuration = new Label(shlLhm, SWT.NONE);
		lblDuration.setText("Duration");
		lblDuration.setAlignment(SWT.RIGHT);
		lblDuration.setBounds(552, 210, 61, 17);

		dtRegDate = new DateTime(shlLhm, SWT.BORDER);
		dtRegDate.setBounds(115, 175, 114, 27);

		Label lblDiagnosis = new Label(shlLhm, SWT.NONE);
		lblDiagnosis.setText("Diagnosis");
		lblDiagnosis.setAlignment(SWT.RIGHT);
		lblDiagnosis.setBounds(362, 141, 103, 17);

		Label lblPractitioner = new Label(shlLhm, SWT.NONE);
		lblPractitioner.setText("Practitioner");
		lblPractitioner.setAlignment(SWT.RIGHT);
		lblPractitioner.setBounds(359, 280, 103, 17);
		
		Button btnUpdate = new Button(shlLhm, SWT.NONE);
		btnUpdate.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					update();
				} catch (RemoteException e1) {

					e1.printStackTrace();
				}
			}
		});
		btnUpdate.setBounds(604, 10, 84, 27);
		btnUpdate.setText("Update");
		
		//===== Set Event Listeners ====
		
		btnAddTreatment.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//Create a new window in another thread.
				// We pass the data it will need to save the record created
				// in there.
				int index = lstPatients.getSelectionIndex();
				if(index < 0) {
					lstPatients.setSelection(0);
					index = 0;
				}
				PatientInterface patient = pList.get(index);
				Display.getDefault().syncExec(new NewTreatmentWindow(patient, smList));
			}
		});
		
		btnModify.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(btnModify.getText() == "Modify"){
					btnModify.setText("Save");
					enablePatientFields(true);					
				} else {
					btnModify.setText("Modify");
					int index = lstPatients.getSelectionIndex();
					PatientInterface patient = pList.get(index);
					try {
						patient.setAddress(txtAddress.getText());	
						patient.setName(txtName.getText());
						String dateStr = Integer.toString(dtRegDate.getDay()) + '/' + Integer.toString(dtRegDate.getMonth()) + '/' + Integer.toString(dtRegDate.getYear());
						SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
						patient.setRegistrationDate(formatter.parse(dateStr));
						
						DataContainer.setModifiedPatient(patient);
						enablePatientFields(false);	
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		
		lstPatients.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				setPatient();			
			}
		});

		lstTreatments.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				setTreatment();
			}
		});
		
		// Fill Window With Data now.
		update();
	}

	// Put selected patient's treatment data in the appropriate text fields
	public void setTreatment(){
		int pindex = lstPatients.getSelectionIndex();
		int tindex = lstTreatments.getSelectionIndex();
		if(pindex < 0){
			return;
		} else if (tindex < 0) {
			lstTreatments.setSelection(0);
			tindex = 0;
		}
		TreatmentInterface treat = tLists.get(pindex).get(tindex);

		txtId.setText("" + treat.getId());
		txtActive.setText((treat.isActive()) ? "Yes" : "No");
		txtDiagnosis.setText("" + treat.getDiagnosis());
		txtCost.setText("£" + treat.getEstimatedPrice());
		txtAdditionalFees.setText("£" + treat.getAdditionalFees());
		txtDuration.setText("" + treat.getEstimatedDuration());
		txtPractitioner.setText("" + treat.getPractitioner().getName());

	}

	// Put selected patient's data in the appropriate text fields
	public void setPatient(){
		int index = lstPatients.getSelectionIndex();
		if(index < 0) {
			lstPatients.setSelection(0);
			index = 0;
		}
		PatientInterface patient = pList.get(index);

		txtName.setText(patient.getName());
		txtAddress.setText(patient.getAddress());
		dtRegDate.setData(patient.getRegistrationDate());
		String insurance;
		if(patient.isHasInsurance()){
			insurance = "Yes";
		} else {
			insurance = "No";
		}
		txtHasInsurance.setText(insurance);
		
		//Populate Treatments list
		lstTreatments.removeAll();
		java.util.List<TreatmentInterface> tList = tLists.get(index);
		for (TreatmentInterface treat: tList ) {
			lstTreatments.add(treat.getStartDate().toString());
		}
		if (tList.size() > 0){
			setTreatment();
		}

	}
	
	public void enableTreatmentFields(boolean enabled) {
		txtActive.setEditable(enabled);
		txtDuration.setEditable(enabled);
		txtAdditionalFees.setEditable(enabled);
		txtPractitioner.setEditable(enabled);
		txtCost.setEditable(enabled);
		txtDiagnosis.setEditable(enabled);
	}
	
	public void enablePatientFields(boolean enabled){
		dtRegDate.setEnabled(enabled);
		txtAddress.setEditable(enabled);
		txtHasInsurance.setEditable(enabled);	
	}
	
	public void update() throws RemoteException{
		int pindex = lstPatients.getSelectionIndex();
		if(pindex < 0) {
			pindex = 0;
		}
		ServerHandler.loadData();
		// Get all patients loaded
		pList = DataContainer.getPList();
		// Get treatments loaded.
		tLists = DataContainer.getTLists();
		//Get all practitioners loaded
		smList = DataContainer.getSmList();
		
		//======= Fill Window ======
		for (PatientInterface patient: pList) {
			lstPatients.add(patient.getName());
		}
		
		lstPatients.setSelection(pindex);
		setPatient();
		
		enableTreatmentFields(false);
		enablePatientFields(false);
	}
}