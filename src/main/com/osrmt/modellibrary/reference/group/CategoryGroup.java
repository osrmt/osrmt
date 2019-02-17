//************ AUTO GENERATED DO NOT EDIT *********//
package com.osrmt.modellibrary.reference.group;

public class CategoryGroup implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	public static final int BUSINESSREQUIREMENTS = 977;
	public static final int DATAREQUIREMENT = 979;
	public static final int SYSTEMREQUIREMENT = 978;
	public static final int USECASE = 976;

	private int CategoryRefId = 0;
	public CategoryGroup(int CategoryRefId) {
		this.CategoryRefId = CategoryRefId;		
	}

	public int getCategoryRefId() {
		return CategoryRefId;
	}

	public static CategoryGroup get(int CategoryRefId) {
		return new CategoryGroup(CategoryRefId);
	}

}
