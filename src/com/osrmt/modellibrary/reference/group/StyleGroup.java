//************ AUTO GENERATED DO NOT EDIT *********//
package com.osrmt.modellibrary.reference.group;

public class StyleGroup implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	public static final int VERDANABOLD16 = 1631;

	private int StyleRefId = 0;
	public StyleGroup(int StyleRefId) {
		this.StyleRefId = StyleRefId;		
	}

	public int getStyleRefId() {
		return StyleRefId;
	}

	public static StyleGroup get(int StyleRefId) {
		return new StyleGroup(StyleRefId);
	}

}
