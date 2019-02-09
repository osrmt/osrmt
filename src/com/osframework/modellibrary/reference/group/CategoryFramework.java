//************ AUTO GENERATED DO NOT EDIT *********//
package com.osframework.modellibrary.reference.group;

public class CategoryFramework implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	public static final int BUSINESSREQUIREMENTS = 977;

	private int CategoryRefId = 0;
	public CategoryFramework(int CategoryRefId) {
		this.CategoryRefId = CategoryRefId;		
	}

	public int getCategoryRefId() {
		return CategoryRefId;
	}

	public static CategoryFramework get(int CategoryRefId) {
		return new CategoryFramework(CategoryRefId);
	}

}
