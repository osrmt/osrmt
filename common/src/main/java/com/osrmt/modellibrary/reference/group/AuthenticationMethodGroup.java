//************ AUTO GENERATED DO NOT EDIT *********//
package com.osrmt.modellibrary.reference.group;

public class AuthenticationMethodGroup implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	public static final int DATABASE = 1206;
	public static final int LDAP = 1205;

	private int AuthenticationMethodRefId = 0;
	public AuthenticationMethodGroup(int AuthenticationMethodRefId) {
		this.AuthenticationMethodRefId = AuthenticationMethodRefId;		
	}

	public int getAuthenticationMethodRefId() {
		return AuthenticationMethodRefId;
	}

	public static AuthenticationMethodGroup get(int AuthenticationMethodRefId) {
		return new AuthenticationMethodGroup(AuthenticationMethodRefId);
	}

}
