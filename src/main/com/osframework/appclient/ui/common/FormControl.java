package com.osframework.appclient.ui.common;

import java.awt.Component;
import com.osframework.modellibrary.reference.security.*;

public class FormControl implements IHtmlApplicationControl {
	
	private Component component = null;
	
	private IControlDef controlDefition = null;
	
	private ApplicationControlModel applicationControlModel = null;
	
	private ApplicationCustomControlModel custom = null;
	
	private int xpos = 0;
	
	private int ypos = 0;
	
	private int height = 1;
	
	private int width = 1;
	
	public FormControl(Component c, IControlDef def, ApplicationControlModel acm, ApplicationCustomControlModel custom) {
		this.component = c;
		this.controlDefition = def;
		this.applicationControlModel = acm;
		this.custom = custom;
	}

	public Component getComponent() {
		return component;
	}

	public void setComponent(Component component) {
		this.component = component;
	}

	public IControlDef getControlDefition() {
		return controlDefition;
	}

	public void setControlDefition(IControlDef controlDefition) {
		this.controlDefition = controlDefition;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getXpos() {
		return xpos;
	}

	public void setXpos(int xpos) {
		this.xpos = xpos;
	}

	public int getYpos() {
		return ypos;
	}

	public void setYpos(int ypos) {
		this.ypos = ypos;
	}
	
	public String toString() {
		if (this.getWidth() == 1 && this.getHeight() == 1) {
			return "cc.xy(" + this.getXpos() + "," + this.getYpos() + ");";
		} else {
			return "cc.xywh(" + this.getXpos() + "," + this.getYpos() 
							+ "," + this.getWidth() + "," + this.getHeight() + ");";
		}
	}

	public ApplicationControlModel getApplicationControlModel() {
		return applicationControlModel;
	}

	public void setApplicationControlModel(ApplicationControlModel acm) {
		this.applicationControlModel = acm;
	}

	public int getModelColumnRefId() {
		return this.applicationControlModel.getModelColumnRefId();
	}

	public String getLabel() {
		return this.applicationControlModel.getLabel();
	}

	public boolean isLocked() {
		return this.applicationControlModel.isLocked();
	}

	public boolean isDisabled() {
		return this.applicationControlModel.isDisabled();
	}

	public String getHtmlScript() {
		if (this.custom != null) {
			return this.custom.getHtmlScript();
		} else {
			return null;
		}
	}


}

