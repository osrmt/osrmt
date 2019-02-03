package com.osframework.appclient.ui.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import com.osframework.appclient.ui.components.MultiColumnList;
import com.osframework.framework.logging.Debug;

/**
 * DoubleClickListener is a MouseListener which 
 * executes call(ActionEvent) on a double click.
 * 
 * Advanced use of this class includes executing 
 * custom code from a double click
 * 
 * Example
 * 			MultiColumnList control = new MultiColumnList();
 * 			control.addMouseListener(new DoubleClickListener(control) {
				public void call(ActionEvent me) {
					try {
						if (me.getSource() != null && me.getSource() instanceof MultiColumnList) {
							MultiColumnList list = (MultiColumnList) me.getSource();
							if (list.isRowSelected()) {						
								Object o = list.getSelectedRow();
								if (o != null) {
									ArtifactModel m = (ArtifactModel) o;
									ArtifactFormController afc = new ArtifactFormController();
									ArtifactModel am = RequirementServices.getArtifact(m.getArtifactId());
									afc.start(am.getArtifactRefId(), am, m);
								}
							}
						}
					} catch (Exception ex) {
						Debug.LogException(this, ex);
					}
				}
			});

 *
 */
public abstract class DoubleClickListener implements ActionListener, MouseListener {

	MultiColumnList list;
	private int numberOfClicks = 1;
	
	public void actionPerformed(ActionEvent e) {
		call(e);
	}

	public DoubleClickListener() {
		super();
	}

	public DoubleClickListener(boolean singleClick) {
		super();
		if (singleClick) {
			numberOfClicks = 0;
		}
	}

	public DoubleClickListener(MultiColumnList list) {
		super();
		this.list = list;
	}

	public void mouseClicked(MouseEvent e) {
		try {
			if (e.getClickCount() > numberOfClicks) {
				ActionEvent ae = new ActionEvent(e.getSource(), e.getID(),null);
				if (list != null) {
					ae.setSource(list);
				}
				call(ae);
			}
		} catch (Exception ex) {
			Debug.LogException(this, ex);
		}
	}

	public void mousePressed(MouseEvent e) {

	}

	public void mouseReleased(MouseEvent e) {

	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}
	
	public abstract void call(ActionEvent me);

}
