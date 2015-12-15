package entityBeans;

import java.util.Date;

public interface ClientInterface{

	String getAddress();

	void setAddress(String address);

	String getName();

	void setName(String name);

	Date getRegistrationDate();

	void setRegistrationDate(Date registrationDate);

	int getId();

}