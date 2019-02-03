//************ AUTO GENERATED DO NOT EDIT *********//
package com.osframework.modellibrary.reference.group;

public class AuthorFramework implements java.io.Serializable {

	private static final long serialVersionUID = 1L;


	private int AuthorRefId = 0;
	public AuthorFramework(int AuthorRefId) {
		this.AuthorRefId = AuthorRefId;		
	}

	public int getAuthorRefId() {
		return AuthorRefId;
	}

	public static AuthorFramework get(int AuthorRefId) {
		return new AuthorFramework(AuthorRefId);
	}

}
