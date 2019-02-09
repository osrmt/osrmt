package com.osframework.appclient.ui.components;



import com.osframework.appclient.ui.controls.*;
import com.osframework.appclient.ui.common.*;

public class UIUserField extends UIComboBox implements IGetCombo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UIUserField() {
		
	}
		
	public void setText(String text) {
		GUI.selectValue(getModel(), text);
	}

}
