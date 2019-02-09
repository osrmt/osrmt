//************ AUTO GENERATED DO NOT EDIT *********//
package com.osrmt.modellibrary.reference.group;

public class ProductGroup implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	public static final int EXAMPLE = 6094029;
	public static final int EXAMPLE2 = 2002799;

	private int ProductRefId = 0;
	public ProductGroup(int ProductRefId) {
		this.ProductRefId = ProductRefId;		
	}

	public int getProductRefId() {
		return ProductRefId;
	}

	public static ProductGroup get(int ProductRefId) {
		return new ProductGroup(ProductRefId);
	}

}
