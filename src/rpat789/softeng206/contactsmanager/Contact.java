package rpat789.softeng206.contactsmanager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Contact implements Serializable {
	
	private String firstName;
	private String lastName;
	private String dateOfBirth;
	private String homePhone;
	private String workPhone;
	private String mobile;
	private String homeAddress;
	private String workAddress;
	private String emailAddress;
	private static List<Contact> contactList = new ArrayList<Contact>();

	
	Contact(String firstName, String lastName, String dateOfBirth, String homePhone, 
			String workPhone, String mobile, String homeAddress, String workAddress, String emailAddress) {
		
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.homePhone = homePhone;
		this.workPhone = workPhone;
		this.mobile = mobile;
		this.homeAddress = homeAddress;
		this.workAddress = workAddress;
		this.emailAddress = emailAddress;
		
	}
	
	public String getName() {
		if (lastName == null) {
			return this.firstName;
		} else {
			return this.firstName+" " +this.lastName;
		}
	}
	
	public String getMobNumber() {
		return this.mobile;
	}
	
	public String getHomeNumber() {
		return this.homePhone;
	}
	
	public String getWorkNumber() {
		return this.workPhone;
	}
	
	public String getHomeAddress() {
		return this.homeAddress;
	}
	
	public String getWorkAddress() {
		return this.workAddress;
	}
	
	public String getEmail() {
		return this.emailAddress;
	}
	
	public String getDOB(){
		return this.dateOfBirth;
	}

}
