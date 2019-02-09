package com.osrmt.appclient.wizards;

import java.awt.*;
import java.awt.event.*;
import java.awt.print.*;
import java.util.*;
import javax.print.*;
import javax.swing.*;
import javax.swing.event.*;

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
import com.osframework.modellibrary.reference.group.ApplicationFramework;
import com.osframework.modellibrary.reference.group.FormButtonTextFramework;
import com.osframework.modellibrary.reference.group.FormTitleFramework;
import com.osframework.modellibrary.reference.security.*;
import com.osframework.modellibrary.system.*;
import com.osrmt.appclient.system.*;
import com.osrmt.modellibrary.reference.group.*;


public class PositionWizard {
	
	private JFrame frame;
	private UIValueList uiPositionList = new UIValueList();
	private WizardScreen screen1;
	private WizardScreen screen2;

	public PositionWizard(JFrame owner) throws HeadlessException {
		this.frame = owner;
	}
	
	private void customCopy() {
		ReferenceModel rm = (ReferenceModel) uiPositionList.getSelectedRow();
		screen1.getButtons().getCmdFinish().setEnabled(true);
		screen1.getButtons().getCmdFinish().setText(ReferenceServices.getDisplay(FormButtonTextFramework.COPY));
		screen1.getButtons().getCmdFinish().addActionListener(addCopyPositionListener(rm));
	}
	
	private UIActionListener addCopyPositionListener(final ReferenceModel fromPosition) {
		return new UIActionListener(frame) {
			public void actionExecuted(ActionEvent e) throws Exception {
				final UIStandardDialog dialog = new UIStandardDialog(frame);
				dialog.setTitle(ReferenceServices.getDisplay(FormTitleFramework.DUPLICATEPOSITION));
				dialog.getButtonPanel().getCmdButton1().setEnabled(false);
				final UIValueList targetPositionList =new UIValueList();
				targetPositionList.setTableModel(getPositionList(), 60);
				targetPositionList.addListSelectionListener(new UIListSelectionListener(frame){
					public void valueChangedExecuted(ListSelectionEvent e) throws Exception {
						dialog.getButtonPanel().getCmdButton1().setEnabled(true);
					}
				});
				dialog.getCenterPanel().add(targetPositionList, BorderLayout.CENTER);
				dialog.getButtonPanel().getCmdButton0().addActionListener(new UIActionListener(frame) {
					public void actionExecuted(ActionEvent e) throws Exception {
							dialog.dispose();
					}
				});
				dialog.getButtonPanel().getCmdButton1().addActionListener(new UIActionListener(frame) {
					public void actionExecuted(ActionEvent e) throws Exception {
						ReferenceModel rm2 = (ReferenceModel) targetPositionList.getSelectedRow();
						if (JOptionPane.showConfirmDialog(dialog, ReferenceServices.getDisplay(FormButtonTextFramework.COPYPOSITIONSECURITY)
								+ " " + fromPosition.getDisplay() + " --> " + rm2.getDisplay(),
								ReferenceServices.getDisplay(FormTitleFramework.DUPLICATEPOSITION),JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
							SecurityServices.duplicateApplicationSecurity(fromPosition.getRefId(), rm2.getRefId());
						}
						dialog.dispose();
						screen1.dispose();
					}
				});
				dialog.pack();
				dialog.setVisible(true);
			}
		};
	}

	
	public void start() {
		screen1 = getScreen1();
		screen2 = getScreen2();
		screen1.initialize(null, screen2);
		// override next being available - until selected
		screen1.getButtons().getCmdNext().setEnabled(false);
		uiPositionList.addListSelectionListener(new UIListSelectionListener(frame){
			public void valueChangedExecuted(ListSelectionEvent e) throws Exception {
				screen1.getButtons().getCmdNext().setEnabled(true);
				customCopy();
			}
		});
		uiPositionList.addMouseListener(new DoubleClickListener() {
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
				return getPositionListPanel();
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
						ReferenceModel m = (ReferenceModel) uiPositionList.getSelectedRow();
						screen1.setUserObject(m);
						screen2.initialize(screen1, null);
						screen2.setTitle(ReferenceServices.getMsg(FormTitleFramework.POSITIONMAINTENANCE) + ": " + m.getDisplay());
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
				return ReferenceServices.getMsg(FormButtonTextFramework.SELECTPOSITION);
			}
			
		};
	}
	
	public WizardScreen getScreen2() {
		return new WizardScreen(frame) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			/**
			 * Build the panel with a list of users
			 */
			public JPanel getCenterPanel() {
				return getPositionForm(getSelectedPosition());
			}
			
			public ActionListener getFinishAction() {
				return null;
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
				return ReferenceServices.getMsg(FormTitleFramework.POSITIONMAINTENANCE) + ": " + getSelectedPosition().getDisplay();
			}
			
		};
	}
	
	private ReferenceModel getSelectedPosition() {
		if (screen1.getUserObject() != null) {
			return (ReferenceModel) screen1.getUserObject();
		} else {
			return new ReferenceModel();
		}
	}
	
	private JPanel getPositionForm(ReferenceModel position) {
		try {
			ApplicationSecurityUserFormController controller = new ApplicationSecurityUserFormController(frame);
			controller.start(position);
			return controller.getListPanel();
		} catch (Exception e) {
			Debug.LogException(this, e);
			return new UIJPanel();
		}
	}
	
	/**
	 * Panel with user list and add button
	 * @return
	 */
	private JPanel getPositionListPanel() {
		
		try {
			uiPositionList.setTableModel(getPositionList(), 60);
			
			UIPanelListButton panel = new UIPanelListButton(UIPanelListButton.NO_RIGHT_SIDE_BUTTONS);
			panel.getButtonB2().setVisible(false);
			panel.getButtonB3().setVisible(false);
			//TODO branch to system reference
			panel.getButtonB1().setVisible(false);
			panel.setListControl(uiPositionList);
			return panel;
		} catch (Exception e) {
			Debug.LogException(this, e);
			return new UIJPanel();
		}
	}
	
	private ReferenceList getPositionList() throws ResultColumnException {
		ReferenceList positions = ReferenceServices.getReferenceList(ReferenceGroup.Position);
		ApplicationControlList acl1 = SecurityServices.getAppControlsByUser(ApplicationFramework.get(ApplicationGroup.REFERENCESEARCHRESULTS));
		positions.setColumnOrder(acl1);
		return positions;
	}
	
	private UIActionListener addNewPositionListener(final MultiColumnList uiPositionList) {
		return new UIActionListener(frame) {
			public void actionExecuted(ActionEvent e) throws Exception {
				final UIStandardDialog dialog = new UIStandardDialog(frame);
				final ReferenceModel newReference = new ReferenceModel();
				final JPanel panel = ControlPanel.getPanel(frame, SecurityServices.getAppControlsByUser(ApplicationFramework.get(ApplicationGroup.REFERENCEFORM)),newReference);
				dialog.getCenterPanel().add(panel, BorderLayout.CENTER);
				dialog.getButtonPanel().getCmdButton1().addActionListener(new UIActionListener(frame) {
					public void actionExecuted(ActionEvent e) throws Exception {
						ReferenceServices.updateReference(newReference);
						uiPositionList.setTableModel(getPositionList(), 60);
						screen1.getButtons().getCmdFinish().setEnabled(true);
						screen1.getButtons().getCmdFinish().addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								screen1.dispose();
							}
						});
					}
				});
				dialog.setVisible(true);
			}
		};
	}

}
