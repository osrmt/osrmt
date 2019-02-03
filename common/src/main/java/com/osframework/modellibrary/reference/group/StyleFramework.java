//************ AUTO GENERATED DO NOT EDIT *********//
package com.osframework.modellibrary.reference.group;

public class StyleFramework implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	public static final int VERDANABOLD16 = 1631;

	private int StyleRefId = 0;
	public StyleFramework(int StyleRefId) {
		this.StyleRefId = StyleRefId;		
	}

	public int getStyleRefId() {
		return StyleRefId;
	}

	public static StyleFramework get(int StyleRefId) {
		return new StyleFramework(StyleRefId);
	}

}
