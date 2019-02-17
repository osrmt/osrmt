//************ AUTO GENERATED DO NOT EDIT *********//
package com.osrmt.modellibrary.reference.group;

public class ViewGroup implements java.io.Serializable {

	private static final long serialVersionUID = 1L;


	private int ViewRefId = 0;
	public ViewGroup(int ViewRefId) {
		this.ViewRefId = ViewRefId;		
	}

	public int getViewRefId() {
		return ViewRefId;
	}

	public static ViewGroup get(int ViewRefId) {
		return new ViewGroup(ViewRefId);
	}

}
