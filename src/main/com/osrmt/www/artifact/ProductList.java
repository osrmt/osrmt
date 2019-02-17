package com.osrmt.www.artifact;
import java.util.*;
import com.osframework.modellibrary.common.*;
import com.osframework.modellibrary.reference.security.*;
import com.osframework.appclient.services.*;
import com.osframework.appclient.ui.listeners.*;
import com.osframework.modellibrary.reference.common.*;
import com.osframework.modellibrary.reference.group.*;
import com.osframework.framework.logging.*;
import com.osframework.framework.utility.*;
import javax.servlet.http.*;
import org.apache.struts.*;
import org.apache.struts.action.*;
import com.osrmt.www.services.*;

public class ProductList {
	
	private ArrayList products = new ArrayList();

	public ProductList() {
		super();
		loadProducts();
	}
	
	private void loadProducts() {
		try {
			products = LocalReferenceServices.getProducts();
		} catch (Exception ex) {
			Debug.LogException(this, ex);
		}
	}
	
	
	

	public ArrayList getProducts() {
		return products;
	}

	public void setProducts(ArrayList products) {
		this.products = products;
	}
	
	

}

