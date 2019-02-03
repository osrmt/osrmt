package com.osrmt.apps.swingApp.wizards;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;

import com.osframework.appclient.services.ReferenceServices;
import com.osframework.appclient.services.SecurityServices;
import com.osframework.appclient.ui.common.ControlPanel;
import com.osframework.appclient.ui.components.MultiColumnList;
import com.osframework.appclient.ui.components.UIPanelListButton;
import com.osframework.appclient.ui.components.UIStandardDialog;
import com.osframework.appclient.ui.controls.UIJPanel;
import com.osframework.appclient.ui.controls.UIProperties;
import com.osframework.appclient.ui.controls.UIValueList;
import com.osframework.appclient.ui.listeners.DoubleClickListener;
import com.osframework.appclient.ui.listeners.UIActionListener;
import com.osframework.appclient.ui.listeners.UIListSelectionListener;
import com.osframework.framework.logging.Debug;
import com.osframework.modellibrary.common.ResultColumnException;
import com.osframework.modellibrary.reference.common.ReferenceList;
import com.osframework.modellibrary.reference.common.ReferenceModel;
import com.osframework.modellibrary.reference.group.ApplicationFramework;
import com.osframework.modellibrary.reference.group.FormButtonTextFramework;
import com.osframework.modellibrary.reference.group.FormTitleFramework;
import com.osframework.modellibrary.reference.security.ApplicationControlList;
import com.osrmt.apps.swingApp.system.ApplicationSecurityUserFormController;
import com.osrmt.modellibrary.reference.group.ApplicationGroup;
import com.osrmt.modellibrary.reference.group.ReferenceGroup;


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
