//************ AUTO GENERATED DO NOT EDIT *********//
package com.osframework.modellibrary.reference.group;

public class AuthenticationMethodFramework implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	public static final int DATABASE = 1206;
	public static final int LDAP = 1205;

	private int AuthenticationMethodRefId = 0;
	public AuthenticationMethodFramework(int AuthenticationMethodRefId) {
		this.AuthenticationMethodRefId = AuthenticationMethodRefId;		
	}

	public int getAuthenticationMethodRefId() {
		return AuthenticationMethodRefId;
	}

	public static AuthenticationMethodFramework get(int AuthenticationMethodRefId) {
		return new AuthenticationMethodFramework(AuthenticationMethodRefId);
	}

}
