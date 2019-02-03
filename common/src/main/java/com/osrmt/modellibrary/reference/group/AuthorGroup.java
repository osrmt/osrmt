//************ AUTO GENERATED DO NOT EDIT *********//
package com.osrmt.modellibrary.reference.group;

public class AuthorGroup implements java.io.Serializable {

	private static final long serialVersionUID = 1L;


	private int AuthorRefId = 0;
	public AuthorGroup(int AuthorRefId) {
		this.AuthorRefId = AuthorRefId;		
	}

	public int getAuthorRefId() {
		return AuthorRefId;
	}

	public static AuthorGroup get(int AuthorRefId) {
		return new AuthorGroup(AuthorRefId);
	}

}
