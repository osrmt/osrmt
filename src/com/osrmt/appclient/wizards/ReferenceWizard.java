package com.osrmt.appclient.wizards;

import java.awt.*;
import java.awt.event.*;
import java.awt.print.*;
import java.util.*;

import javax.print.*;
import javax.swing.*;
import javax.swing.event.*;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.osframework.appclient.services.*;
import com.osframework.appclient.ui.common.*;
import com.osframework.appclient.ui.listeners.*;
import com.osframework.appclient.ui.components.*;
import com.osframework.appclient.ui.controls.*;
import com.osframework.framework.logging.Debug;
import com.osframework.framework.utility.*;
import com.osframework.modellibrary.common.*;
import com.osframework.modellibrary.reference.common.*;
import com.osframework.modellibrary.reference.group.*;
import com.osframework.modellibrary.reference.security.*;
import com.osframework.modellibrary.reportwriter.*;
import com.osframework.modellibrary.system.*;
import com.osrmt.appclient.services.ReqReferenceServices;
import com.osrmt.appclient.system.*;
import com.osrmt.modellibrary.reference.group.*;
import com.osframework.appclient.ui.common.*;


public class ReferenceWizard {
	
	private JFrame frame;
	private UIValueList uiReferenceGroupList = new UIValueList();
	private UIValueList uiReferenceList = new UIValueList();
	private WizardScreen screen1;
	private WizardScreen screen2;
	private WizardScreen screen3;
	private String selectedGroup = null;
	private boolean systemWizard;
	private ReferenceWizard self = this;

	public ReferenceWizard(JFrame owner) throws HeadlessException {
		this.frame = owner;
	}
	
	private void customCopy() {
		
		ReferenceModel rm = (ReferenceModel) uiReferenceList.getSelectedRow();
		/*
		if (rm.getReferenceGroup().equals("Artifact")) {
			screen2.getButtons().getCmdFinish().setEnabled(true);
			screen2.getButtons().getCmdFinish().setText(ReferenceServices.getDisplay(FormButtonTextFramework.COPY));
			screen2.getButtons().getCmdFinish().addActionListener(addCopyArtifactListener(rm));
		}
		*/
		
	}
	
	private UIActionListener addCopyArtifactListener(final ReferenceModel fromArtifact) {
		return new UIActionListener(frame) {
			public void actionExecuted(ActionEvent e) throws Exception {
				final UIStandardDialog dialog = new UIStandardDialog(frame);
				dialog.setTitle(ReferenceServices.getDisplay(FormTitleFramework.DUPLICATEARTIFACT) + " " + fromArtifact.getDisplay());
				dialog.getButtonPanel().getCmdButton1().setEnabled(false);
				final UIValueList targetArtifactList =new UIValueList();
				targetArtifactList.setTableModel(getReferenceList(), 60);
				targetArtifactList.addListSelectionListener(new UIListSelectionListener(frame){
					public void valueChangedExecuted(ListSelectionEvent e) throws Exception {
						dialog.getButtonPanel().getCmdButton1().setEnabled(true);
					}
				});
				dialog.getCenterPanel().add(targetArtifactList, BorderLayout.CENTER);
				dialog.getButtonPanel().getCmdButton0().addActionListener(new UIActionListener(frame) {
					public void actionExecuted(ActionEvent e) throws Exception {
							dialog.dispose();
							screen1.dispose();
							screen2.dispose();
					}
				});
				dialog.getButtonPanel().getCmdButton1().addActionListener(new UIActionListener(frame) {
					public void actionExecuted(ActionEvent e) throws Exception {
						ReferenceModel rm2 = (ReferenceModel) targetArtifactList.getSelectedRow();
						if (JOptionPane.showConfirmDialog(dialog, ReferenceServices.getDisplay(FormButtonTextFramework.COPYARTIFACTFORMS)
								+ " " + fromArtifact.getDisplay() + " --> " + rm2.getDisplay(),
								ReferenceServices.getDisplay(FormTitleFramework.DUPLICATEARTIFACT),JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
							SecurityServices.duplicateArtifactControls(fromArtifact.getRefId(), rm2.getRefId());
						}
						dialog.dispose();
						screen1.dispose();
						screen2.dispose();
					}
				});
				dialog.pack();
				dialog.setVisible(true);
			}
		};
	}

	
	public void start(boolean systemWizard) {
		this.systemWizard = systemWizard;
		screen1 = getScreen1();
		screen2 = getScreen2();
		screen3 = getScreen3();
		screen1.initialize(null, screen2);
		// override next being available - until selected
		screen1.getButtons().getCmdNext().setEnabled(false);
		ISApplicationMediator.getInstance().register(new IReceiveMessage() {

			public boolean receive(ISEvent event, Object value) throws Exception {
				if (event.equals(ISEvent.APPLIEDCHANGES) && value.equals(UIContext.contextReferenceWizard)) {
					screen3.getButtons().getCmdFinish().setEnabled(true);
					return true;
				}
				return false;
			}
			
		}, self);
		uiReferenceGroupList.addListSelectionListener(new UIListSelectionListener(frame){
			public void valueChangedExecuted(ListSelectionEvent e) throws Exception {
				screen1.getButtons().getCmdNext().setEnabled(true);
				if (uiReferenceGroupList.getSelectedRow() instanceof ReferenceGroupModel) {
					selectedGroup = ((ReferenceGroupModel) uiReferenceGroupList.getSelectedRow()).getDisplay();
				}
			}
		});
		uiReferenceGroupList.addMouseListener(new DoubleClickListener() {
			public void call(ActionEvent me) {
				screen1.getButtons().getCmdNext().getActionListeners()[0].actionPerformed(me);
			}
		});
		screen1.setVisible(true);
	}
	
	public WizardScreen getScreen1() {
		return new WizardScreen(frame) {
			private static final long serialVersionUID = 1L;

			/**
			 * Build the panel with a list of users
			 */
			public JPanel getCenterPanel() {
				return getReferenceGroupPanel();
			}
			
			public ActionListener getFinishAction() {
				return null;
			}

			/**
			 * Store the selected user in this screen
			 */
			public ActionListener getNextAction() {
				return new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						ReferenceGroupModel m = (ReferenceGroupModel) uiReferenceGroupList.getSelectedRow();
						screen1.setUserObject(m);
						addScreen2Listeners();
						screen2.initialize(screen1, screen3);
						
					}
				};
			}

			public Dimension getSize() {
				return UIProperties.getWINDOW_SIZE_600_400();
			}
			
			public Point getLocation() {
				return UIProperties.getPoint200_200();
			}

			public String getTitle() {
				return ReferenceServices.getDisplay(
						FormTitleGroup.REFERENCEWIZARDSELECTGROUP);
			}
			
		};
	}
	
	public WizardScreen getScreen2() {
		return new WizardScreen(frame) {
			private static final long serialVersionUID = 1L;

			/**
			 * Build the panel with a list of users
			 */
			public JPanel getCenterPanel() {
				return getReferencePanel();
			}
			
			public ActionListener getFinishAction() {
				return null;
			}

			/**
			 * Store the selected user in this screen
			 */
			public ActionListener getNextAction() {
				return new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						ReferenceModel m = (ReferenceModel) uiReferenceList.getSelectedRow();
						screen2.setUserObject(m);
						screen3.initialize(screen2, null);						
						screen3.getButtons().getCmdFinish().setEnabled(false);
					}
				};
			}

			public Dimension getSize() {
				return UIProperties.getWINDOW_SIZE_600_400();
			}
			
			public Point getLocation() {
				return UIProperties.getPoint200_200();
			}

			public String getTitle() {
				return ReferenceServices.getDisplay(
						FormTitleGroup.REFERENCEWIZARDSELECTREFERENCE) + ": " + selectedGroup;
			}
			
		};
	}
	
	public WizardScreen getScreen3() {
		return new WizardScreen(frame) {
			private static final long serialVersionUID = 1L;

			/**
			 * Build the panel with a list of users
			 */
			public JPanel getCenterPanel() {
				return getReferenceForm();
			}
			
			public ActionListener getFinishAction() {
				return new UIActionListener(frame) {
					public void actionExecuted(ActionEvent e) throws Exception {
						ISApplicationMediator.getInstance().deregister(self);
						dispose();
					}
				};
			}

			/**
			 * Store the selected user in this screen
			 */
			public ActionListener getNextAction() {
				return null;
			}

			public Dimension getSize() {
				return UIProperties.getWINDOW_SIZE_600_400();
			}
			
			public Point getLocation() {
				return UIProperties.getPoint200_200();
			}

			public String getTitle() {
				return getSelectedReference().getDisplay();
			}
			
		};
	}
	
	private ReferenceGroupModel getSelectedReferenceGroup() {
		if (screen1.getUserObject() != null) {
			return (ReferenceGroupModel) screen1.getUserObject();
		} else {
			return new ReferenceGroupModel();
		}
	}
	
	private void addScreen2Listeners() {
		uiReferenceList.addListSelectionListener(new UIListSelectionListener(frame){
			public void valueChangedExecuted(ListSelectionEvent e) throws Exception {
				screen2.getButtons().getCmdNext().setEnabled(true);
				customCopy();
			}
		});
		uiReferenceList.addMouseListener(new DoubleClickListener() {
			public void call(ActionEvent me) {
				screen2.getButtons().getCmdNext().getActionListeners()[0].actionPerformed(me);
			}
		});
	}
	
	private ReferenceModel getSelectedReference() {
		if (screen2.getUserObject() != null) {
			return (ReferenceModel) screen2.getUserObject();
		} else {
			return new ReferenceModel();
		}
	}
	
	private JPanel getReferenceGroupPanel() {
		try {
			uiReferenceGroupList.setTableModel(getReferenceGroupList(), 60);
			
			UIJPanel panel = new UIJPanel();
			panel.add(uiReferenceGroupList, BorderLayout.CENTER);
			return panel;
		} catch (Exception e) {
			Debug.LogException(this, e);
			return new UIJPanel();
		}
	}

	private JPanel getReferencePanel() {
		try {
			uiReferenceList.setTableModel(getReferenceList(), 60);
			UIPanelListButton panel = new UIPanelListButton(UIPanelListButton.NO_RIGHT_SIDE_BUTTONS);;
			panel.getButtonB3().setVisible(false);//properties
			switch (getSelectedReferenceGroup().getModificationRefId()) {
			case ReferenceModificationFramework.FULL:
				panel.getButtonB2().setVisible(true);//remove
				break;
			case ReferenceModificationFramework.ADD:
				panel.getButtonB2().setVisible(false);//remove
				break;
			case ReferenceModificationFramework.DISPLAY:
				panel.getButtonB2().setVisible(false);//remove
				panel.getButtonB1().setVisible(false);//add
				break;
			}
			panel.getButtonB1().addActionListener(addNewReferenceListener(uiReferenceList));
			panel.getButtonB2().addActionListener(addRemoveReferenceListener(uiReferenceList));
			panel.setListControl(uiReferenceList);
			return panel;
		} catch (Exception e) {
			Debug.LogException(this, e);
			return new UIJPanel();
		}
	}
	
	private void restrictReference(ApplicationControlList acl) {
		Enumeration e1 = acl.elements();
		while (e1.hasMoreElements()) {
			ApplicationControlModel acm = (ApplicationControlModel) e1.nextElement();
			switch (getSelectedReferenceGroup().getModificationRefId()) {
			case ReferenceModificationFramework.DISPLAY:
				if (acm.getControlRefDisplay().compareTo("Display")!=0) {
					acm.setLocked();
				}
				break;
			}
		}
	}

	/**
	 * Panel with user list and add button
	 * @return
	 */
	private JPanel getReferenceForm() {
		
		try {
			ApplicationControlList acl = SecurityServices.getAppControlsByUser(ApplicationFramework.get(ApplicationGroup.REFERENCEFORM));
			ReferenceModel rm = getSelectedReference();
			restrictReference(acl);
			final ReferenceModel updatedRef = ReferenceServices.getReference(rm.getRefId());
			JPanel panel = ControlPanel.getPanel(frame, acl, updatedRef);

			final UIStandardPanel applyPanel = new UIStandardPanel();
			applyPanel.getCenterPanel().add(panel, BorderLayout.CENTER);
			applyPanel.getButtonPanel().initialize(UIPanelButton.APPLY);
			applyPanel.getButtonPanel().getCmdButton0().addActionListener(new UIActionListener(frame){
				public void actionExecuted(ActionEvent e) throws Exception {
					ReferenceServices.updateReference(updatedRef);
					applyPanel.getButtonPanel().getCmdButton0().setEnabled(false);
					ISApplicationMediator.getInstance().receive(ISEvent.APPLIEDCHANGES, UIContext.contextReferenceWizard);
				}
			});
			
			return applyPanel;
		} catch (Exception e) {
			Debug.LogException(this, e);
			return new UIJPanel();
		}
	}
	
	private ReferenceList getReferenceList() throws ResultColumnException {
		ReferenceList list = ReferenceServices.getActiveReferenceList(getSelectedReferenceGroup().getReferenceGroup()); 
		ApplicationControlList acl1 = SecurityServices.getAppControlsByUser(ApplicationFramework.get(ApplicationGroup.REFERENCESEARCHRESULTS));
		list.setColumnOrder(acl1);
		return list;
	}
	
	private ReferenceGroupList getReferenceGroupList() throws ResultColumnException {
		ReferenceGroupList reference = ReqReferenceServices.getManagedReferenceGroups(systemWizard);
		Enumeration e1 = reference.elements();
		while (e1.hasMoreElements()) {
			ReferenceGroupModel g = (ReferenceGroupModel) e1.nextElement();
			g.setDescription(GUI.substring(g.getDescription(), 50));
		}
		ApplicationControlList acl1 = SecurityServices.getAppControlsByUser(ApplicationFramework.get(ApplicationGroup.REFGROUPSEARCHRESULTS));
		reference.setColumnOrder(acl1);
		return reference;
	}

	private ActionListener addNewReferenceListener(final MultiColumnList uiRefList) {
		final UIStandardDialog dialog = new UIStandardDialog(frame);
		final ReferenceModel addReferenceModel = new ReferenceModel();
		addReferenceModel.setReferenceGroup(getSelectedReferenceGroup().getReferenceGroup());
		return new UIActionListener(frame) {
			public void actionExecuted(ActionEvent e) throws Exception {
				dialog.setTitle(ReferenceServices.getDisplay(FormTitleGroup.REFERENCEADDNEW));
				dialog.getButtonPanel().getCmdButton0().addActionListener(new UIActionListener(frame) {
					public void actionExecuted(ActionEvent e) throws Exception {
						dialog.dispose();
					}
				});
				dialog.getButtonPanel().getCmdButton1().addActionListener(new UIActionListener(frame) {
					public void actionExecuted(ActionEvent e) throws Exception {
						ReferenceServices.updateReference(addReferenceModel).getPrimaryKeyId();
						TimedAction action = new TimedAction(0.1) {
							public void executeTask() {
								try {
//									uiRefList.setTableModel(getReferenceList(), 60);
									screen2.initialize(screen1, screen3);
									screen2.invalidate();
									screen2.validate();
								} catch (Exception ex) {
//									Debug.LogException(this, ex);
								}
							};
						};
						screen2.getButtons().getCmdFinish().setEnabled(true);
						screen2.getButtons().getCmdFinish().addActionListener(new UIActionListener(frame) {
							public void actionExecuted(ActionEvent e) throws Exception {
								screen2.dispose();
							}
						});
						dialog.dispose();
					}
				});
				dialog.getCenterPanel().add(buildAddReferenceControlPanel(addReferenceModel), BorderLayout.CENTER);
				dialog.setVisible(true);			
			}
		};
	}
	
	private ActionListener addRemoveReferenceListener(final MultiColumnList uiRefList) {
		return new UIActionListener(frame) {
			public void actionExecuted(ActionEvent e) {
				try {
					final ReferenceModel m = (ReferenceModel) uiRefList.getSelectedRow();
					if (!m.isNew()) {
						m.setNotActive();
						ReferenceServices.updateReference(m);
						uiRefList.setTableModel(getReferenceList(), 60);
					}
				} catch (Exception ex) {
					Debug.LogException(this, ex);
				}
			}
		};
	}
	
	private JPanel buildAddReferenceControlPanel(ReferenceModel rm) {
		try {
			ApplicationControlList list = SecurityServices.getAppControlsByUser(ApplicationFramework.get(ApplicationGroup.REFERENCEFORM));
			Enumeration e1 = list.elements();
			while (e1.hasMoreElements()) {
				ApplicationControlModel acm = (ApplicationControlModel) e1.nextElement();
				//TODO property of acm 
				if (acm.getControlRefDisplay().compareTo("AppTypeRefId")==0) {
					acm.setNotLocked();
				}
			}
			JPanel panel = ControlPanel.getPanel(frame, list, rm);
			return panel;
		} catch (Exception ex) {
			return new JPanel();
		}
	}



}
