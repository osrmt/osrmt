package com.osframework.modellibrary.reference.security;

public class FindStaffModel implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	public static int MIN_COL_WIDTH = 40;
	
	private String lastName;
	private String firstName;
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}
