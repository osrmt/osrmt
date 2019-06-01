package com.osframework.appclient.ui.common;

import java.util.Random;

public class UIContext  {

	private int contextId = 0;
	private static java.util.Random rand = new Random(new java.util.GregorianCalendar().getTimeInMillis());
	
	public static UIContext contextReferenceWizard = new UIContext(rand.nextInt());
	public static UIContext contextUserWizard = new UIContext(rand.nextInt());
	public static UIContext contextArtifactPanel = new UIContext(rand.nextInt());
	public static UIContext contextTextTable = new UIContext(rand.nextInt());
	public static UIContext contextArtifactDocumentPanel = new UIContext(rand.nextInt());
	
	public static UIContext get(int context) {
		return new UIContext(context);
	}
	public UIContext(int context) {
		contextId = context;
	}

	public int getContextId() {
		return contextId;
	}

	public void setContextId(int contextId) {
		this.contextId = contextId;
	}
	
	 @Override
	public boolean equals(Object obj) {
		 if (obj != null) {
			 return getContextId() == ((UIContext)obj).getContextId();
		 }
		return false;
	}
	 
}
