package com.osrmt.appclient.wizards;

import com.osframework.ejb.reportwriter.ReportWriterBean;
import java.awt.*;
import java.awt.event.*;
import java.awt.print.*;
import java.io.FileNotFoundException;
import java.util.*;

import javax.print.*;
import javax.swing.*;
import javax.swing.event.*;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.osframework.appclient.services.*;
import com.osframework.appclient.ui.common.*;
import com.osframework.appclient.ui.common.SwingWorker;
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
import com.osrmt.appclient.system.*;
import com.osrmt.modellibrary.reference.group.*;
import com.osrmt.modellibrary.reqmanager.ArtifactList;
import com.osrmt.modellibrary.reqmanager.ArtifactModel;
import com.osrmt.modellibrary.issue.*;
import com.osrmt.modellibrary.reports.*;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;


public class ReportWizard {
	
	private JFrame frame;
	private MultiColumnList uiReportList = new MultiColumnList();
	private WizardScreen screen1;
	private WizardScreen screen2;
	private RecordParameterControlList parameterControlList;
	// report by artifacts
	private ArtifactList artifactList = new ArtifactList();
	private boolean artifactsSelected = false;
	// report by issues
	private IssueList issueList = new IssueList();
	private boolean issuesSelected = false;
	private ReportParameterModel reportParamModel = new ReportParameterModel();

	public ReportWizard(JFrame owner) throws HeadlessException {
		this.frame = owner;
	}
	
	public ReportWizard(ArtifactList list, JFrame owner) throws HeadlessException {
		this.frame = owner;
		this.artifactList = list;
		artifactsSelected = true;
	}
	
	public ReportWizard(IssueList list, JFrame owner) throws HeadlessException {
		this.frame = owner;
		this.issueList = list;
		issuesSelected = true;
	}
	
	public void start() {
		screen1 = getScreen1();
		screen2 = getScreen2();
		screen1.initialize(null, screen2);
		//if (artifactsSelected || issuesSelected) {
		//	screen1.buttons.getCmdNext().setEnabled(true);
		//	screen1.buttons.getCmdNext().addActionListener(printHtmlAction());
		//	screen1.buttons.getCmdNext().setText(ReferenceServices.getMsg(FormButtonTextFramework.HTML));
		//	screen1.buttons.getCmdFinish().setEnabled(true);
		//	screen1.buttons.getCmdFinish().addActionListener(printPdfAction());
		//	screen1.buttons.getCmdFinish().setText(ReferenceServices.getMsg(FormButtonTextFramework.PDF));
			
		//} else {
			// override next being available - until selected
			screen1.getButtons().getCmdNext().setEnabled(false);
			uiReportList.addListSelectionListener(new UIListSelectionListener(frame){
				public void valueChangedExecuted(ListSelectionEvent e) throws Exception {
					screen1.getButtons().getCmdNext().setEnabled(true);
				}
			});
			uiReportList.addMouseListener(new DoubleClickListener() {
				public void call(ActionEvent me) {
					screen1.getButtons().getCmdNext().getActionListeners()[0].actionPerformed(me);
				}
			});
		//}
		screen1.setVisible(true);
	}
	
	public WizardScreen getScreen1() {
		return new WizardScreen(frame) {
			private static final long serialVersionUID = 1L;

			/**
			 * Build the panel with a list of users
			 */
			public JPanel getCenterPanel() {
				return getReportListPanel();
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
						ReportModel m = (ReportModel) uiReportList.getSelectedRow();
						screen1.setUserObject(m);
						screen2.initialize(screen1, null);
						screen2.buttons.getCmdNext().setEnabled(true);
						for (ActionListener l : screen2.buttons.getCmdNext().getActionListeners()) {
							screen2.buttons.getCmdNext().removeActionListener(l);
						}
						screen2.buttons.getCmdNext().addActionListener(printHtmlAction());
						screen2.buttons.getCmdFinish().setText(ReferenceServices.getMsg(FormButtonTextFramework.PDF));
						screen2.buttons.getCmdNext().setText(ReferenceServices.getMsg(FormButtonTextFramework.HTML));
						
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
						FormTitleGroup.REPORTADDNEW);
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
				return getReportForm(getSelectedReport());
			}
			
			public ActionListener getFinishAction() {
				return printPdfAction();
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
				return ReferenceServices.getDisplay(
						FormTitleGroup.REPORTWIZARDGENERATEREPORT) + ": " + getSelectedReport().getReportRefDisplay();
			}
			
		};
	}
	
	private UIActionListener printPdfAction() {
		return new UIActionListener(frame) {
			public void actionExecuted(ActionEvent e) throws Exception {
				final ReportModel rm = new ReportModel();
				try {
					ReportModel fromList = (ReportModel) uiReportList.getSelectedRow();
					rm.updateWith(fromList);
					String defaultfilename = FileProcess.getFilePath(FileProcess.getTemporaryDirectory(), FileProcess.getRandomFile("osrmt.pdf"));
					final String filename = JOptionPane.showInputDialog(ReferenceServices.getDisplay(FormButtonTextFramework.FILENAME),defaultfilename);
				
                                        if (filename != null ) {
						SwingWorker sw = new SwingWorker(frame) {

							@Override
							protected void doNonUILogic() throws Exception {
								try {
									JasperPrint jasperPrint;
									if (artifactsSelected) {
										reportParamModel.addParameter(artifactList);
									} else if (issuesSelected) {
										reportParamModel.addParameter(issueList);
									}
									jasperPrint = ReportWriterServices.runReport(parameterControlList, rm.getReportId(), reportParamModel.getParams());
									JasperExportManager.exportReportToPdfFile(jasperPrint,filename);
                                                                        FileProcess.executeFile(filename);
								} catch (net.sf.jasperreports.engine.JRException ffe) {
									RecordFileModel rfm = SystemServices.getFiles(TableNameFramework.REPORT, rm.getReportId()).getFirst();
									Debug.LogError(this,rm.getStorageDirectory() + rfm.getFileName() + " " + ffe.toString());									
								} catch (Exception ex1) {
									Debug.LogException(this, ex1);
								}
							}

							@Override
							protected void doUIUpdateLogic() throws Exception {
								Debug.displayGUIMessage(filename);
							}
							
						};
						sw.start();
						screen1.dispose();
						screen2.dispose();
					}
				} catch (Exception ex) {
					Debug.LogException(this, ex);
				}
			}
		};
	}
	
	private UIActionListener printHtmlAction() {
		return new UIActionListener(frame) {
			public void actionExecuted(ActionEvent e) throws Exception {
				final ReportModel rm = new ReportModel();
				try {
					ReportModel fromList = (ReportModel) uiReportList.getSelectedRow();
					rm.updateWith(fromList);
					String defaultfilename = FileProcess.getFilePath(FileProcess.getTemporaryDirectory(), FileProcess.getRandomFile("osrmt.htm"));
					final String filename = JOptionPane.showInputDialog(ReferenceServices.getDisplay(FormButtonTextFramework.FILENAME),defaultfilename);
                                        if (filename != null ) {
						SwingWorker sw = new SwingWorker(frame) {
                                                    
							@Override
							protected void doNonUILogic() throws Exception {
								try {
									JasperPrint jasperPrint;
									if (artifactsSelected) {
										reportParamModel.addParameter(artifactList);
									} else if (issuesSelected) {
										reportParamModel.addParameter(issueList);
									}
									jasperPrint = ReportWriterServices.runReport(parameterControlList, rm.getReportId(), reportParamModel.getParams());
									JasperExportManager.exportReportToHtmlFile(jasperPrint,filename);
                                                                        FileProcess.executeFile(filename);
								} catch (net.sf.jasperreports.engine.JRException ffe) {
									RecordFileModel rfm = SystemServices.getFiles(TableNameFramework.REPORT, rm.getReportId()).getFirst();
									Debug.LogError(this,rm.getStorageDirectory() + rfm.getFileName() + " " + ffe.toString());									
								} catch (Exception ex1) {
									Debug.LogException(this, ex1);
								}
							}

							@Override
							protected void doUIUpdateLogic() throws Exception {
								Debug.displayGUIMessage(filename);
							}
							
						};
						sw.start();
						screen1.dispose();
						screen2.dispose();
					}
				} catch (Exception ex) {
					Debug.LogException(this, ex);
				}
			}
		};
	}
	
	private ReportModel getSelectedReport() {
		if (screen1.getUserObject() != null) {
			return (ReportModel) screen1.getUserObject();
		} else {
			return new ReportModel();
		}
	}
	
	private JPanel getReportForm(ReportModel rm) {
		try {
			ApplicationControlList acl = SecurityServices.getAppControls(ViewFramework.get(rm.getParameterViewRefId()),
					ApplicationFramework.get(ApplicationGroup.REPORTPARAMETERFORM), false);
			reportParamModel.getParams().clear();
			JPanel panel = ControlPanel.getPanel(frame, acl, reportParamModel);
			return panel;
		} catch (Exception ex) {
			return new JPanel();
		}
	}
	
	/*
	private JPanel getReportForm(ReportModel rm) {
		try {
			RecordParameterModel rpm = new RecordParameterModel();
			rpm.setTableId(rm.getReportId());
			rpm.setTableRefId(TableNameGroup.REPORT);
			
			parameterControlList = SystemServices.getParameters(rpm);
			Enumeration e1 = parameterControlList.elements();
			ApplicationControlList list = new ApplicationControlList();
			while (e1.hasMoreElements()) {
				RecordParameterModel m = (RecordParameterModel) e1.nextElement();
				ApplicationControlModel acm = SecurityServices.getApplicationControl(m.getApplicationControlId());
				list.add(acm);
			}
			JPanel panel = ControlPanel.getPanel(frame, list, parameterControlList);
			return panel;
		} catch (Exception ex) {
			return new JPanel();
		}
	}
	*/

	/**
	 * Panel with user list and add button
	 * @return
	 */
	private JPanel getReportListPanel() {
		
		try {
			uiReportList.setTableModel(getReportList(), 60);
			
			final PanelAddRemove panel = new PanelAddRemove(PanelAddRemove.NO_RIGHT_SIDE_BUTTONS);
			panel.getRemoveButton().setVisible(false);
			panel.getPropertiesButton().setVisible(true);
			panel.getPropertiesButton().setEnabled(false);
			uiReportList.addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent e) {
					if (uiReportList.getSelectedRow() != null) {
						panel.getPropertiesButton().setEnabled(true);
					} else {
						panel.getPropertiesButton().setEnabled(false);
					}
				}
			});
			addNewReportListener(uiReportList, panel.getAddButton(),panel.getPropertiesButton());
			panel.setListControl(uiReportList);
			return panel;
		} catch (Exception e) {
			Debug.LogException(this, e);
			return new UIJPanel();
		}
	}
	
	private ReportList getReportList() throws Exception {
		ReportList reports = ReportWriterServices.getReports();
		ApplicationControlList acl1 = SecurityServices.getAppControlsByUser(ApplicationFramework.get(ApplicationGroup.REPORTLISTCOLUMNS));
		reports.setColumnOrder(acl1);
		return reports;
	}
	
	private void addNewReportListener(final MultiColumnList uiReportList, JButton addButton, final JButton editButton) {
		UIActionListener action = new UIActionListener(frame) {
			public void actionExecuted(ActionEvent e) throws Exception {
				final UICenterSouthDialog dialog = new UICenterSouthDialog(frame, false);
				dialog.setTitle(ReferenceServices.getDisplay(FormTitleGroup.REPORTADDNEW));
				PanelOkCancel okCancel = new PanelOkCancel();
				dialog.getSouthPanel().add(okCancel, BorderLayout.CENTER);
				final ReportModel addReportModel = new ReportModel();
				if (e.getSource().equals(editButton) && uiReportList.getSelectedRow() instanceof ReportModel) {
					ReportModel update = (ReportModel) uiReportList.getSelectedRow();
					addReportModel.updateWith(update);
				}
				okCancel.addCancelActionListener(new UIActionListener(frame) {
					public void actionExecuted(ActionEvent e) throws Exception {
						dialog.dispose();
					}
				});
				okCancel.addOkActionListener(new UIActionListener(frame) {
					public void actionExecuted(ActionEvent e) throws Exception {
						//updateReportModel(addReportModel);
						ReportWriterServices.UpdateReport(addReportModel);
						TimedAction runnow = new TimedAction(0.75) {
							public void executeTask() {
								try {
									uiReportList.setTableModel(getReportList(), 60);
									screen1.getButtons().getCmdFinish().setEnabled(true);
								} catch (Exception ex) {
									Debug.LogException(this, ex);
								}
							}
						};
						dialog.dispose();
					}
				});
				dialog.getCenterPanel().add(buildAddReportControlPanel(addReportModel), BorderLayout.CENTER);
				dialog.setLocation(150,150);
				dialog.setSize(500,400);
				dialog.setVisible(true);			
			}
		};
		addButton.addActionListener(action);
		editButton.addActionListener(action);
	}
	
	/*
	private void updateReportModel(ReportModel rm) {
		Enumeration e1 = formControls.elements();
		while (e1.hasMoreElements()) {
			FormControl fc = (FormControl) e1.nextElement();
			if (fc.getModelColumnRefId() == ModelColumnGroup.REPORTFILENAME) {
				if (fc.getComponent() instanceof IGetDocument) {
					IGetDocument doc = (IGetDocument) fc.getComponent();
					rm.setFileName(doc.getText());
				}
			} else if (fc.getModelColumnRefId() == ModelColumnGroup.REPORTSQL) {
				if (fc.getComponent() instanceof IGetDocument) {
					IGetDocument doc = (IGetDocument) fc.getComponent();
					rm.setReportSql(doc.getText());
				}
			} else if (fc.getModelColumnRefId() == ModelColumnGroup.REPORTREPORTREFDISPLAY) {
				if (fc.getComponent() instanceof IGetDocument) {
					IGetDocument doc = (IGetDocument) fc.getComponent();
					rm.setRecordTypeRefDisplay(doc.getText());
				}
			} else {
				Debug.LogError(this,"invalid report form control");
			}
		}
	}
	//TODO redo with binding...
	private Vector formControls = new Vector();	
	*/
	
	private JPanel buildAddReportControlPanel(ReportModel rm) {
		try {
			ApplicationControlList list = SecurityServices.getAppControlsByUser(ApplicationFramework.get(ApplicationGroup.REPORTFORM));
			ControlPanel cp = new ControlPanel(frame);
			cp.initialize(list,2,rm);
			//this.formControls = cp.getFormControls();
			JPanel panel = cp.getPanel();
			return panel;
		} catch (Exception ex) {
			return new JPanel();
		}
	}
        
}
