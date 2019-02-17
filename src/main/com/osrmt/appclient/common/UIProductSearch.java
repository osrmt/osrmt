/**
 * 
 */
package com.osrmt.appclient.common;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.osframework.appclient.services.*;
import com.osframework.appclient.ui.common.LayoutUtility;
import com.osframework.appclient.ui.components.UIReferenceSearch;
import com.osframework.framework.logging.Debug;
import com.osframework.modellibrary.reference.common.ReferenceDisplayList;

/**
 * 
 *
 */
public class UIProductSearch extends UIReferenceSearch {

	public UIProductSearch(JFrame parentFrame) {
		super(parentFrame);
	}

	@Override
	public void addControls() {
		try {
			DefaultFormBuilder builder = LayoutUtility.getDefaultBuilder(controlColumns, 0);
			ReferenceDisplayList referenceDisplayList = SecurityServices.getProductList();
			list.addList(referenceDisplayList.elements());
			LayoutUtility.addControl(1, 0, 1, builder,referenceGroup,list);
			if (referenceDisplayList.getSize() > 0) {
				list.setSelectedIndex(0);
			}
			panelCenter.add(builder.getPanel());
			this.PanelOkCancel.addCancelActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
		} catch (Exception ex) {
			Debug.LogException(this, ex);
		}
	}

}
