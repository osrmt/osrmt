//************ AUTO GENERATED DO NOT EDIT *********//
package com.osframework.modellibrary.reference.common;

import com.osframework.modellibrary.common.IControlModel;;

public class ReferenceModel extends ReferenceDataModel implements Comparable {

	static final long serialVersionUID = 1L;

	public ReferenceModel() {

	}
	
	
	
	/**
	 * 
	 * Build the display code from the display
	 *
	 */
	public void setDisplayCode() {
		if (getDisplay() != null) {
			String code = getDisplayCode(getDisplay());
			setDisplayCode(code);
		}
	}
	
	public static String getDisplayCode(String s) {
		if (s != null) {
			s = s.toUpperCase();
			s = s.replace(" ","");
			return s;
		} else {
			return s;
		}
	}
	
	/**
	 * Default sort for ReferenceModels is by the sequence.
	 * @param o
	 * @return
	 */
	public int compareTo(Object o) {
		ReferenceModel rm = (ReferenceModel) o;
		if (rm == null) {
			return 0;
		} else if (getAppTypeRefDisplay().equals(rm.getAppTypeRefDisplay())) {
			if (getDisplaySequence() == rm.getDisplaySequence()) {
				return 0;
			} else if (getDisplaySequence() < rm.getDisplaySequence()) {
				return -1;
			} else {
				return 1;
			}
		} else {
			return getAppTypeRefDisplay().compareTo(rm.getAppTypeRefDisplay());
		}
	}
	
	
	public String toString() {
		return this.getDisplay();
	}

}