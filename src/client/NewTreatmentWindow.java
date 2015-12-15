package client;
import java.math.BigDecimal;
import java.rmi.RemoteException;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import entityBeans.PatientInterface;
import entityBeans.StaffMemberInterface;


public class NewTreatmentWindow implements Runnable{

	protected Shell shell;
	private Text txtTreatmentId;
	private Text txtDiagnosis;
	private Text txtCost;
	private Text txtDuration;
	private Text txtAdditionalFees;
	private Text txtPractitioner;
	private Text txtPatientId;
	private Text txtName;
	private PatientInterface patient;
	private java.util.List<StaffMemberInterface> smList;
	
	protected NewTreatmentWindow(PatientInterface pat, java.util.List<StaffMemberInterface> practitionersList) {
		patient = pat;
		smList = practitionersList;
	}
	/**
	 * @wbp.parser.entryPoint
	 */
	@Override
	public void run() {		
		try {	
			open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void open() throws RemoteException {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	// Create contents of the window.
	protected void createContents() throws RemoteException {
		shell = new Shell();
		shell.addShellListener(new ShellAdapter() {
			@Override
			public void shellClosed(ShellEvent e) {
				System.out.println("Closed!");
			}
		});
		shell.setSize(359, 352);
		shell.setText("SWT Application");
		
		Label lblTreatmentId = new Label(shell, SWT.NONE);
		lblTreatmentId.setText("Treatment ID");
		lblTreatmentId.setAlignment(SWT.RIGHT);
		lblTreatmentId.setBounds(201, 45, 88, 17);
		
		txtTreatmentId = new Text(shell, SWT.NONE);
		txtTreatmentId.setEnabled(false);
		txtTreatmentId.setBounds(295, 45, 44, 29);
		
		Label label_2 = new Label(shell, SWT.NONE);
		label_2.setText("Diagnosis");
		label_2.setAlignment(SWT.RIGHT);
		label_2.setBounds(13, 81, 103, 17);
		
		txtDiagnosis = new Text(shell, SWT.BORDER | SWT.MULTI);
		txtDiagnosis.setBounds(122, 80, 217, 62);
		
		Label label_3 = new Label(shell, SWT.NONE);
		label_3.setText("Cost");
		label_3.setAlignment(SWT.RIGHT);
		label_3.setBounds(28, 150, 88, 17);
		
		txtCost = new Text(shell, SWT.BORDER);
		txtCost.setBounds(122, 150, 75, 29);
		
		Label label_4 = new Label(shell, SWT.NONE);
		label_4.setText("Duration");
		label_4.setAlignment(SWT.RIGHT);
		label_4.setBounds(203, 150, 61, 17);
		
		txtDuration = new Text(shell, SWT.BORDER);
		txtDuration.setBounds(270, 150, 69, 29);
		
		Label label_5 = new Label(shell, SWT.NONE);
		label_5.setText("Additional Fees");
		label_5.setAlignment(SWT.RIGHT);
		label_5.setBounds(13, 185, 103, 17);
		
		txtAdditionalFees = new Text(shell, SWT.BORDER);
		txtAdditionalFees.setBounds(122, 185, 75, 29);
		
		Label label_6 = new Label(shell, SWT.NONE);
		label_6.setText("Practitioner");
		label_6.setAlignment(SWT.RIGHT);
		label_6.setBounds(10, 220, 103, 17);
		
		txtPractitioner = new Text(shell, SWT.BORDER);
		txtPractitioner.setBounds(122, 220, 217, 29);
		
		Button btnSave = new Button(shell, SWT.NONE);
		btnSave.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					saveTreatment();
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}	
				shell.close();
			}
		});
		btnSave.setBounds(255, 284, 84, 27);
		btnSave.setText("Save");
		
		Button btnCancel = new Button(shell, SWT.NONE);
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.close();
			}
		});
		btnCancel.setText("Cancel");
		btnCancel.setBounds(165, 284, 84, 27);
		
		Label lblTreament = new Label(shell, SWT.NONE);
		lblTreament.setBounds(54, 45, 62, 17);
		lblTreament.setText("Patient ID");
		
		txtPatientId = new Text(shell, SWT.NONE);
		txtPatientId.setEnabled(false);
		txtPatientId.setBounds(122, 45, 44, 29);
		
		Label lblName = new Label(shell, SWT.NONE);
		lblName.setText("Name");
		lblName.setAlignment(SWT.RIGHT);
		lblName.setBounds(54, 10, 62, 17);
		
		txtName = new Text(shell, SWT.BORDER);
		txtName.setBounds(122, 10, 217, 29);
		
		//Fill content
		txtPatientId.setText("" + patient.getId());
		txtName.setText(patient.getName());
		txtPractitioner.setText(smList.get(0).getName());
	}
	
	private void saveTreatment() throws RemoteException {
		boolean active = true; // TODO: Change that and make it work
		String diagnosis = txtDiagnosis.getText();
		int duration =  Integer.parseInt(txtDuration.getText());
		BigDecimal price = (BigDecimal.valueOf(Double.parseDouble(txtCost.getText())));
		BigDecimal extrafees = (BigDecimal.valueOf(Double.parseDouble(txtAdditionalFees.getText())));
		int patientId = patient.getId();
		int sMemberId = smList.get(0).getId(); //TODO: Change this and make it work.
		System.out.println(active + ", "  + 
				diagnosis + ", "  +
				duration + ", "  +
				price + ", "  +
				patientId + ", "  + 
				sMemberId);
		
		DataContainer.newTreatment(true, diagnosis, duration, price, extrafees, patientId, sMemberId);
	}
}
