//************ AUTO GENERATED DO NOT EDIT *********//
package com.osframework.modellibrary.reference.security;


public class ApplicationUserModel extends ApplicationUserDataModel implements Comparable {

	static final long serialVersionUID = 1L;
	private static boolean firstNameLastFormat = false;
	
	public ApplicationUserModel() {

	}
	
	public void setFirstName(String firstName) {
		super.setFirstName(firstName);
		super.setDisplayName(format(getLastName(), getFirstName()));
	}
	
	public void setLastName(String lastName) {
		super.setLastName(lastName);
		super.setDisplayName(format(getLastName(), getFirstName()));
	}
	
	@Override
	public String getDisplayName() {
		if (isFirstNameLastFormat()) {
			return getFirstName() + " " + getLastName();
		} else {
			return getLastName() + ", " + getFirstName();
		}
	}
	
	public String format(String lastName, String firstName) {
		if (isFirstNameLastFormat()) {
			return firstName + " " + lastName;
		} else {
			return lastName + ", " + firstName;
		}
	}
	public int compareTo(Object arg0) {
		return 0;
	}

	public static boolean isFirstNameLastFormat() {
		return firstNameLastFormat;
	}

	public static void setFirstNameLastFormat(boolean firstLast) {
		firstNameLastFormat = firstLast;
	}
	
}