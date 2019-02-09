package com.osrmt.appclient.common;


import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.ref.*;
import javax.swing.*;

import java.util.*;
import java.text.*;
import javax.swing.event.*;
import javax.swing.text.*;

import com.jgoodies.forms.builder.*;
import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.*;

import com.osframework.appclient.ui.common.ISApplicationMediator;
import com.osframework.appclient.ui.components.*;
import com.osframework.appclient.ui.controls.*;
import com.osframework.modellibrary.common.*;
import com.osframework.modellibrary.reference.security.*;
import com.osframework.appclient.services.*;
import com.osframework.appclient.ui.listeners.*;
import com.osframework.modellibrary.reference.common.*;
import com.osframework.modellibrary.reference.group.*;
import com.osframework.framework.logging.*;
import com.osframework.framework.utility.*;
import com.osrmt.appclient.services.RequirementServices;
import com.osrmt.modellibrary.reqmanager.*;
import com.osrmt.modellibrary.reference.group.*;
import com.osframework.appclient.ui.common.*;

public class ArtifactListPanel extends UIPanelListButton implements ICustomBind, IApplyChanges{

	private static final long serialVersionUID = 1L;

	private ArtifactList removedList = new ArtifactList();
	private ArtifactModel parent = new ArtifactModel();
	private JFrame frame = new JFrame();
	private ComponentTypeGroup componentGroup;
	public static final int listWidth=400;
	private boolean locked = false;
	private ChangeListener change = null;
	
	public ArtifactListPanel(){
		super();
		initForm();
	}
	
	public ActionListener getAddAction() {
		return new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				try {
					ArtifactList list = (ArtifactList) getListControl().getResultList();
					ArtifactModel m = createModel(list.size());
					list.add(m);
					notifyChange();
					getListControl().setTableModel(list,listWidth);
					getListControl().selectRow(list.size()-1);
					getListControl().getJtable().scrollRectToVisible(getListControl().getJtable().getCellRect(list.size()-1,0,true)); 
					getListControl().getJtable().requestFocus(); 

				} catch (Exception ex) {
					Debug.LogException(this, ex);
				}
			};
		};
	}
	private void notifyChange() throws Exception {
		ISApplicationMediator.getInstance().receive(ISEvent.MADECHANGES, UIContext.contextArtifactPanel);

	}
	
	private ArtifactModel createModel(int seq) {
		ArtifactModel m = new ArtifactModel();
		m.setArtifactSeq(seq);
		m.setArtifactLevelRefId(ArtifactLevelGroup.COMPONENT);
		m.setArtifactRefId(parent.getArtifactRefId());
		m.setComponentTypeRefId(componentGroup.getComponentTypeRefId());
		return m;
	}
	
	public ActionListener getRemoveAction() {
		return new UIActionListener(frame){
			public void actionExecuted(ActionEvent e) {
				try {
					int position = getSelectedIndex();
					ArtifactList list = (ArtifactList) getListControl().getResultList();
					assignSeq(list);
					if (position >= 0 && position < list.size()) {
						ArtifactModel m  = (ArtifactModel) list.elementAt(position);
						m.setNotActive();
						if (m.getArtifactId() > 0) {
							removedList.add(m);
						}
						list.remove(m);
						getListControl().setTableModel(list,listWidth);
					}
				} catch (Exception ex) {
					Debug.LogException(this, ex);
				}
			};
		};
	}
	
	public ActionListener getMoveUpAction() {
		return new UIActionListener(frame){
			public void actionExecuted(ActionEvent e) {
				try {
					int position = getSelectedIndex();
					if (moveUp(position)) {
						setSelectedIndex(position-1);
					}
				} catch (Exception ex) {
					Debug.LogException(this, ex);
				}
			};
		};
	}
	
	public ActionListener getMoveDownAction() {
		return new UIActionListener(frame){
			public void actionExecuted(ActionEvent e) {
				try {
					int position = getSelectedIndex();
					if (moveDown(position)) {
						setSelectedIndex(position+1);
					}
				} catch (Exception ex) {
					Debug.LogException(this, ex);
				}
			};
		};
	}
	
	public boolean applyOutstandingChanges() {
		return applyOutstandingChanges(null);
	}
	
	private void applyChanges() throws Exception {
		if (locked) {
			return;
		}
		ArtifactList list = (ArtifactList) getListControl().getResultList();
		Enumeration e1 = list.elements();
		int index = 0;
		while (e1.hasMoreElements()) {
			ArtifactModel am = (ArtifactModel) e1.nextElement();
			if (am.isNew() && am.getArtifactName() == null || am.getArtifactName().trim().length() < 1) {
				list.remove(am);
				getListControl().getResultList().fireTableRowsDeleted(index, index);
				index--;
			}
			index++;
		}
		assignSeq(list);
		RequirementServices.UpdateArtifact(list, parent, RelationGroup.get(RelationGroup.RELATED), false);
		RequirementServices.UpdateArtifact(removedList);
		list.resetModified();
		removedList = new ArtifactList();
	}
	
	public boolean applyOutstandingChanges(String label) {
		try {
			if (label == null) {
				return true;
			}
			if (JOptionPane.showConfirmDialog(frame, ReferenceServices.getDisplay(SystemMessageFramework.APPLYCHANGES)
					+ " " + label,
					ReferenceServices.getDisplay(FormButtonTextFramework.APPLY), JOptionPane.OK_CANCEL_OPTION)
					== JOptionPane.OK_OPTION) {
				applyChanges();
				return true;
			} else {
				return false;
			}
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			return false;
		}
	}
	
	public ActionListener getApplyAction() {
		return new UIActionListener(frame){
			public void actionExecuted(ActionEvent e) throws Exception {
				applyChanges();
				buttonR5.setEnabled(false);
			};
		};
	}
	
	public void setSelectedIndex(int position) {
		listControl.selectRow(position);
	}
	
	public boolean moveUp(int position) {
		ArtifactList list = (ArtifactList) getListControl().getResultList();
		assignSeq(list);
		if (position > 0 && position < list.size()) {
			ArtifactModel m  = (ArtifactModel) list.elementAt(position);
			ArtifactModel above  = (ArtifactModel) list.elementAt(position-1);
			int oldSeq = above.getArtifactSeq();
			m.setArtifactSeq(oldSeq);
			above.setArtifactSeq(oldSeq+1);
			getListControl().setTableModel(list,listWidth);
			return true;
		} else {
			return false;
		}
	}
	
	public boolean moveDown(int position) {
		ArtifactList list = (ArtifactList) getListControl().getResultList();
		assignSeq(list);
		if (position >= 0 && position < list.size()-1) {
			ArtifactModel m  = (ArtifactModel) list.elementAt(position);
			ArtifactModel above  = (ArtifactModel) list.elementAt(position+1);
			int oldSeq = above.getArtifactSeq();
			m.setArtifactSeq(oldSeq);
			above.setArtifactSeq(oldSeq-1);
			//TODO hack - firetablechange not working
			getListControl().setTableModel(list,listWidth);
			return true;
		} else {
			return false;
		}
	}
	
	private void assignSeq(ArtifactList list) {
		Enumeration e1 = list.elements();
		int i=0;
		while (e1.hasMoreElements()) {
			ArtifactModel m = (ArtifactModel) e1.nextElement();
			m.setArtifactSeq(i);
			i++;
		}		
	}
	
	public void initForm() {
		
		buttonR1.setText(ReferenceServices.getMsg(FormButtonTextFramework.MOVEUP));
		buttonR1.addActionListener(getMoveUpAction());
		buttonR2.setText(ReferenceServices.getMsg(FormButtonTextFramework.MOVEDOWN));
		buttonR2.addActionListener(getMoveDownAction());
		buttonR3.setText(ReferenceServices.getMsg(FormButtonTextFramework.ADDROW));
		buttonR3.addActionListener(getAddAction());
		buttonR4.setText(ReferenceServices.getMsg(FormButtonTextFramework.REMOVEROW));
		buttonR4.addActionListener(getRemoveAction());
		buttonR5.setText(ReferenceServices.getMsg(FormButtonTextFramework.APPLY));
		buttonR5.addActionListener(getApplyAction());
		this.setPreferredSize(new Dimension(400,400));
		this.setSize(new Dimension(400,400));
		this.getButtonR1().setEnabled(false);
		this.getButtonR2().setEnabled(false);
		this.getButtonR3().setEnabled(false);
		this.getButtonR4().setEnabled(false);
		this.getButtonR5().setEnabled(false);

	}
	
	protected JPanel buildButtonStack() {
        JPanel panel = new JPanel();
        CellConstraints cc = new CellConstraints();
        panel.setBorder(Borders.DIALOG_BORDER);
        ButtonStackBuilder builder = new ButtonStackBuilder();
        builder.addGridded(buttonR1);                      
        builder.addRelatedGap();                   
        builder.addGridded(buttonR2);   
        builder.addRelatedGap();                   
        builder.addGridded(buttonR3);
        builder.addRelatedGap();                   
        builder.addGridded(buttonR4);   
        builder.addRelatedGap();
        builder.addGridded(buttonR5);   
        panel.add(builder.getPanel(),  cc.xy(3, 0));                   
        return panel;
	}
	
	public void setListControl(UIValueList uilist, ArtifactModel model, JFrame frame, ComponentTypeGroup componentGroup) {
		super.setListControl(uilist);
		this.componentGroup = componentGroup;
		this.frame = frame;
		this.parent = model;
		this.getButtonR1().setEnabled(model.getArtifactId() != 0);
		this.getButtonR2().setEnabled(model.getArtifactId() != 0);
		this.getButtonR3().setEnabled(model.getArtifactId() != 0);
		this.getButtonR4().setEnabled(model.getArtifactId() != 0);
		this.getButtonR5().setEnabled(model.getArtifactId() != 0);
		uilist.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (hasOutstandingChanges()) {
					buttonR5.setEnabled(true);
				}				
			}
		});
	}
	
	protected Component buildAddRemovePropertiesPanel() {

        return new UIJPanel();
    }

	public boolean hasOutstandingChanges() {
		if (locked) {
			return false;
		} else if (removedList.size() > 0) {
			notifyListener();
			return true;
		} else {
			ArtifactList list = (ArtifactList) getListControl().getResultList();
			Enumeration e1 = list.elements();
			while (e1.hasMoreElements()) {
				ArtifactModel am = (ArtifactModel) e1.nextElement();
				if (am.hasModified()) {
					notifyListener();
					return true;
				}
			}
			return false;
		}
	}


	public void setLocked(boolean locked) {
		this.getButtonR1().setEnabled(!locked);
		this.getButtonR2().setEnabled(!locked);
		this.getButtonR3().setEnabled(!locked);
		this.getButtonR4().setEnabled(!locked);
		this.getButtonR5().setEnabled(!locked);
		this.locked = locked;
		getListControl().setEnabled(!locked);
	}
	
	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		setLocked(!enabled);
	}

	public void addChangeListener(ChangeListener change) {
		if (change != null) {
			this.change = change;
		}
	}
	
	private void notifyListener() {
		if (change != null ) {
			change.stateChanged(new ChangeEvent(this));
		}
	}
}
