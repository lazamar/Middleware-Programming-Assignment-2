package entityBeans;
import java.io.Serializable;

import javax.ejb.Remote;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Remote(StaffMemberInterface.class)
public class StaffMember implements Serializable, StaffMemberInterface {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String name, role;

	public StaffMember() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public int getId() {
		return id;
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
	public String getRole() {
		return role;
	}

	@Override
	public void setRole(String role) {
		this.role = role;
	}
}
