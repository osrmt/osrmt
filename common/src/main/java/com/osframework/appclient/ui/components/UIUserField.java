package com.osframework.appclient.ui.components;



import com.osframework.appclient.ui.common.GUI;
import com.osframework.appclient.ui.controls.IGetCombo;
import com.osframework.appclient.ui.controls.UIComboBox;

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
