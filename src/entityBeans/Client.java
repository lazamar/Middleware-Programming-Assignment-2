package entityBeans;

import java.io.Serializable;
import java.util.Date;

import javax.ejb.Remote;
import javax.persistence.*;

@Entity
@Remote(ClientInterface.class)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Client implements Serializable, ClientInterface {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String name, address;
	@Temporal(TemporalType.DATE)
	private Date registrationDate;

	protected Client() {

	}

	@Override
	public String getAddress() {
		return address;
	}

	@Override
	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public Date getRegistrationDate() {
		return registrationDate;
	}


	@Override
	public void setRegistrationDate(Date registrationDate){
		this.registrationDate = registrationDate;
	}


	@Override
	public int getId() {
		return id;
	}

}
