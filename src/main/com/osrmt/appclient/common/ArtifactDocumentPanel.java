/**
 * 
 */
package com.osrmt.appclient.common;

import java.util.*;
import javax.swing.JPanel;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import com.osframework.appclient.ui.editor.*;
import com.osframework.appclient.ui.common.IReceiveMessage;
import com.osframework.appclient.ui.common.ISApplicationMediator;
import com.osframework.appclient.ui.common.ISEvent;
import com.osframework.appclient.ui.common.UIContext;
import com.osframework.framework.logging.*;
import com.osframework.appclient.ui.controls.*;
import com.osrmt.modellibrary.reqmanager.*;
import com.osrmt.appclient.services.*;
/**
 * 
 *
 */
public class ArtifactDocumentPanel extends JPanel implements ICustomBind {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UIDocumentTable table = null;
	private boolean locked = false;
	
	public ArtifactDocumentPanel() {
		super(new java.awt.BorderLayout());
	}

	public void initialize(final int artifactId) throws Exception {
		java.util.List<IDocumentLine> lines = new java.util.ArrayList<IDocumentLine>();
		ArtifactDocumentList list = RequirementServices.getArtifactDocumentLines(artifactId);
		if (!locked && list.size() == 0) {
			ArtifactDocumentModel line = new ArtifactDocumentModel();
			line.setArtifactId(artifactId);
			line.setLineText("...");
			list.add(line);
		}
		Enumeration e1 = list.elements();
		while (e1.hasMoreElements()) {
			ArtifactDocumentModel adm = (ArtifactDocumentModel) e1.nextElement();
			lines.add(adm.getDocumentLine());
		}
		final UIDocumentTableModel model = new UIDocumentTableModel(lines);
		table = new UIDocumentTable(model);
		this.add(table, java.awt.BorderLayout.CENTER);
		model.addTableModelListener(new TableModelListener() {

			public void tableChanged(TableModelEvent e) {
				try {
					if (!locked) {
						ISApplicationMediator.getInstance().receive(ISEvent.MADECHANGES, UIContext.contextArtifactDocumentPanel);
					}
				} catch (Exception ex) {
					Debug.LogException(this, ex);
				}
			}
			
		});
		ISApplicationMediator.getInstance().register(new IReceiveMessage() {

			public boolean receive(ISEvent event, Object value) throws Exception {
				if (locked) {
					return true;
				}
				UIContext context = (UIContext) value;
				if (event == ISEvent.APPLIEDCHANGES && context.equals(UIContext.contextArtifactDocumentPanel)) {
					ArtifactDocumentList list = new ArtifactDocumentList();
					int seq = 0;
					for (IDocumentLine line : model.getTextLines()) {
						ArtifactDocumentModel m = new ArtifactDocumentModel();
						m.setLineSeq(seq);
						if (line instanceof ImageLineModel) {
							//ImageLineModel image = (ImageLineModel) line;
							//m.seti
							m.setLineText("image");
						} else if (line instanceof DocumentTextLineModel){
							DocumentTextLineModel d = (DocumentTextLineModel) line;
							if (d.getValue() != null) {
								m.setLineText(String.valueOf(d.getValue()));
							}
						}
						list.add(m);
						seq++;
					}
					RequirementServices.UpdateArtifactDocument(artifactId, list);
					return true;
				}
				return false;
			}
			
		}, this);
	}

	public void setLocked(boolean locked) {
		locked = true;
	}
	
	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		if (!enabled) {
			setLocked(true);
		}
	}

	public void addChangeListener(ChangeListener change) {
		// TODO Auto-generated method stub
		
	}
}
