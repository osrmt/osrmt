/**
 * 
 */
package com.osrmt.appclient.reqmanager;

import java.awt.Cursor;

import com.osframework.appclient.ui.common.GUI;
import com.osframework.appclient.ui.components.MultiColumnList;
import com.osframework.appclient.ui.controls.UIValueList;
import com.osframework.appclient.ui.listeners.DoubleClickListener;
import com.osframework.framework.logging.Debug;
import com.osrmt.appclient.services.RequirementServices;
import com.osrmt.modellibrary.reqmanager.*;

/**
 * 
 *
 */
public class ArtifactHistoryList extends UIValueList {

	private ArtifactHistoryList self = this;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public ArtifactHistoryList() {
		this.addMouseListener(getOpenListener());
	}

	private DoubleClickListener getOpenListener() {
		return new DoubleClickListener(this) {
			public void call(java.awt.event.ActionEvent ae) {
				try {
					if (self != null) {
						if (self != null) {
							self.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
						}
						if (self.isRowSelected()) {						
							Object o = self.getSelectedRow();
							if (o != null) {
								ArtifactHistoryModel cachedFromself = (ArtifactHistoryModel) o;
								ArtifactReadOnlyFormController afc = null;
								if (GUI.hasDialog(self)) {
									afc = new ArtifactReadOnlyFormController(GUI.getDialog(self));
								} else {
									afc = new ArtifactReadOnlyFormController(new javax.swing.JFrame());
								}
								ArtifactModel am = RequirementServices.getArtifactFromHistory(cachedFromself.getArtifactHistoryId());
								afc.start(am);
							}
						}
						if (self != null) {
							self.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
						}
					}
				} catch (Exception ex) {
					if (self != null) {
						self.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
					}
					Debug.LogException(this, ex);
				}
			}
		};
	}
	

}
