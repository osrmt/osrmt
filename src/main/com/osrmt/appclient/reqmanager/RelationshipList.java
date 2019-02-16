package com.osrmt.appclient.reqmanager;

import java.awt.*;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Point;
import java.awt.event.*;
import java.io.*;
import java.lang.ref.WeakReference;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Random;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.osframework.appclient.services.Application;
import com.osframework.appclient.services.ReferenceServices;
import com.osframework.appclient.services.SecurityServices;
import com.osframework.appclient.services.SystemServices;
import com.osframework.appclient.ui.common.*;
import com.osframework.appclient.ui.components.*;
import com.osframework.appclient.ui.controls.*;
import com.osframework.appclient.ui.listeners.DoubleClickListener;
import com.osframework.appclient.ui.listeners.UIActionListener;
import com.osframework.appclient.ui.tree.UITreeModel;
import com.osframework.framework.logging.Debug;
import com.osframework.framework.utility.*;
import com.osframework.modellibrary.system.*;
import com.osframework.modellibrary.common.ResultList;
import com.osframework.modellibrary.reference.group.ApplicationFramework;
import com.osframework.modellibrary.reference.group.FormButtonTextFramework;
import com.osframework.modellibrary.reference.group.SystemMessageFramework;
import com.osframework.modellibrary.reference.security.*;
import com.osrmt.appclient.common.ApplicationObject;
import com.osrmt.appclient.common.ReqTreeSearch;
import com.osrmt.appclient.services.RequirementServices;
import com.osrmt.modellibrary.reference.group.ApplicationGroup;
import com.osrmt.modellibrary.reference.group.RelationGroup;
import com.osrmt.modellibrary.reqmanager.ArtifactList;
import com.osrmt.modellibrary.reqmanager.ArtifactModel;
import com.osrmt.modellibrary.reqmanager.RequirementTreeModel;

public class RelationshipList extends MultiColumnList implements IParent, ICustomBind {
	
	private static final long serialVersionUID = 1L;
	private ArtifactModel artifact;
	private RelationshipList self = this;
	private JFrame frame;
	private JDialog dialog;
	private boolean locked = false;
	private ChangeListener changeListener = null;

	public RelationshipList() {
		try {
			this.setPreferredSize(new java.awt.Dimension(50,50));
			jtable.setPreferredSize(new java.awt.Dimension(50,50));
			updateList();
		} catch (Exception ex) {
			Debug.LogException(this, ex);
		}
	}
	
	public void initialize(ArtifactModel artifact) throws Exception {
		this.artifact = artifact;
		updateList();
	}
	
	private void updateList() {
		try {
			if (artifact != null) {
				new TimedAction(0.5) {
					public void executeTask() {
						try {
							ArtifactList artifactList = RequirementServices.getDependentItems(artifact.getArtifactId());
							ApplicationControlList controls = SecurityServices.getAppControlsByUser(ApplicationFramework.get(ApplicationGroup.ARTIFACTRELATIONSHIPCOLUMNS));
							artifactList.setColumnOrder(controls);
							setTableModel(artifactList,80);
							addMouseListener(getOpenListener(self));
							addMouseListener(new MouseAdapter() { 
					            public void mousePressed(MouseEvent e) 
					            { 
					            	try {
					                    if (!locked && SwingUtilities.isRightMouseButton(e) == true) 
					                    { 
					    					Object o = getSelectedRow();
					                        JPopupMenu pop = new JPopupMenu("");
					    					if (o != null) {
						                        pop.add(getNewAction());
						                        pop.add(getOpenAction(e));
						                        pop.addSeparator();
						                        pop.add(getDeleteAction());
					    	                } else {
						                        pop.add(getNewAction());
					    	                }
					                        pop.setLocation(300, 300);
					                        pop.show(e.getComponent(), e.getX(), e.getY()); 
					                    } 
					            	} catch (Exception ex) {
					            		Debug.LogException(this, ex);
					            	}
					            } 
					        });
						} catch (Exception ex) {
							Debug.LogException(this, ex);
						}
					}
				};
			}
		} catch (Exception ex) {
			Debug.LogException(this, ex);
		}
	
	}
	
	public JMenuItem getNewAction() {
		//TODO these menu item strings should come from the database (ideally app cached)
        JMenuItem menu = new JMenuItem(ReferenceServices.getMsg(FormButtonTextFramework.NEWACTION));
        menu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					final ReqTreeSearch search = new ReqTreeSearch(getTreeModel(artifact));
					UIActionListener updateAction = new UIActionListener(frame) {
						public void actionExecuted(ActionEvent e) throws Exception {
							try {
								ArtifactList list = search.getAllSelectedArtifacts();
								Enumeration e1 = list.elements();
								while (e1.hasMoreElements()) {
									ArtifactModel am = (ArtifactModel) e1.nextElement();
									if (am != null && 
										artifact.getArtifactId() != am.getArtifactId()) {
										RequirementTreeModel rtm = new RequirementTreeModel();
										rtm.setChildId(am.getArtifactId());
										rtm.setChildArtifactRefId(am.getArtifactRefId());
										rtm.setParentId(artifact.getArtifactId());
										rtm.setParentArtifactRefId(artifact.getArtifactRefId());
										rtm.setRelationRefId(RelationGroup.DEPENDENT);
										RequirementServices.UpdateRelationship(rtm);
										notifyListener();
									} else {
										Debug.displayGUIMessage(ReferenceServices.getMsg(SystemMessageFramework.INVALIDARTIFACTSELECTION));
									}
								}
								search.dispose();
								updateList();	
							} catch (Exception ex) {
								Debug.LogException(this, ex);
							}
						}
					};					
					search.initialize(updateAction, artifact);
					search.setTitle(ReferenceServices.getDisplay(artifact.getArtifactRefId())
							+ " " + artifact.getArtifactName());
					search.setVisible(true);
				} catch (Exception ex) {
					Debug.LogException(this, ex);
				}
			}			
		});
        return menu;
	}
	
	public JMenuItem getOpenAction(MouseEvent e) {
        JMenuItem menu = new JMenuItem(ReferenceServices.getMsg(FormButtonTextFramework.OPENACTION));
        menu.addActionListener(getOpenListener(this));
        return menu;
	}
	
	public JMenuItem getDeleteAction() {
        JMenuItem menu = new JMenuItem(ReferenceServices.getMsg(FormButtonTextFramework.DELETE));
        menu.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					if (isRowSelected()) {
						Enumeration e1 = getSelectedRows().elements();
						while (e1.hasMoreElements()) {
							ArtifactModel m = (ArtifactModel) e1.nextElement();
							//TODO replace with getting the requirement_tree_id 
							RequirementTreeModel rtm = new RequirementTreeModel();
							rtm.setParentId(artifact.getArtifactId());
							rtm.setChildId(m.getArtifactId());
							rtm.setRelationRefId(RelationGroup.DEPENDENT);
							RequirementServices.deleteRelationship(rtm);
							notifyListener();
						}
						updateList();
					}
				} catch (Exception ex) {
					Debug.LogException(this, ex);
				}
			}
			
		});
        return menu;
	}
	
	private DoubleClickListener getOpenListener(final RelationshipList source) {
		return new DoubleClickListener(this) {
			private RelationshipList src = source;
			public void call(java.awt.event.ActionEvent ae) {
				try {
					MultiColumnList list = null;
					if (ae.getSource() != null && ae.getSource() instanceof MultiColumnList ) {
						list = (MultiColumnList) ae.getSource();
					} else if (src != null) {
						list = (MultiColumnList) src;
					}
					if (list != null) {
						if (src != null) {
							src.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
						}
						if (list.isRowSelected()) {						
							Object o = list.getSelectedRow();
							if (o != null) {
								ArtifactModel cachedFromList = (ArtifactModel) o;
								ArtifactReadOnlyFormController afc = null;
								if (GUI.hasDialog(list)) {
									afc = new ArtifactReadOnlyFormController(GUI.getDialog(list));
								} else {
									afc = new ArtifactReadOnlyFormController(frame);
								}
								ArtifactModel am = RequirementServices.getArtifact(cachedFromList.getArtifactId());
								afc.start(am);
							}
						}
						if (src != null) {
							src.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
						}
					}
				} catch (Exception ex) {
					if (src != null) {
						src.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
					}
					Debug.LogException(this, ex);
				}
			}
		};
	}
	
	public void setValue(Object o1, Object o2) {
		// TODO Auto-generated method stub
		
	}

	public void setValue(Object o) {
		
	}
	
	public UITreeModel getTreeModel(ArtifactModel m) {
		return ApplicationObject.getRequirementManagerController().getReqTree().getTreeModel();
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}
	
	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		setLocked(!enabled);
	}


	public void addChangeListener(ChangeListener change) {
		if (changeListener != null) {
			changeListener = change;
		}		
	}
	
	private void notifyListener() {
		if (changeListener != null) {
			changeListener.stateChanged(new ChangeEvent(this));
		}		
	}

}
