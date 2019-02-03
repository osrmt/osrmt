//************ AUTO GENERATED DO NOT EDIT *********//
package com.osframework.modellibrary.reference.group;

public class ProductFramework implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	public static final int EXAMPLE = 6094029;

	private int ProductRefId = 0;
	public ProductFramework(int ProductRefId) {
		this.ProductRefId = ProductRefId;		
	}

	public int getProductRefId() {
		return ProductRefId;
	}

	public static ProductFramework get(int ProductRefId) {
		return new ProductFramework(ProductRefId);
	}

}
