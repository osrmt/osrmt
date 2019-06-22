package com.osrmt.appclient.reqmanager;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.*;

import javax.swing.*;
import javax.swing.tree.*;
import com.osrmt.modellibrary.reqmanager.*;
import com.osrmt.appclient.changecontrol.ChangeControlViewController;
import com.osrmt.appclient.common.*;
import com.osrmt.appclient.system.*;
import com.osrmt.appclient.services.*;
import com.osrmt.appclient.traceability.TraceabilityViewController;
import com.osrmt.appclient.wizards.*;
import com.osframework.appclient.ui.tree.*;
import com.osframework.framework.logging.*;
import com.osframework.framework.utility.ClientProperty;
import com.osframework.framework.utility.TimedAction;
import com.osframework.appclient.services.Application;
import com.osframework.appclient.services.ReferenceServices;
import com.osframework.appclient.services.ReportWriterServices;
import com.osframework.appclient.services.SecurityServices;
import com.osframework.appclient.services.SystemServices;
import com.osframework.appclient.system.SystemLogForm;
import com.osframework.appclient.ui.common.*;
import com.osframework.appclient.ui.common.SwingWorker;
import com.osframework.appclient.ui.components.*;
import com.osframework.appclient.ui.controls.*;
import com.osframework.appclient.ui.listeners.DoubleClickListener;
import com.osframework.appclient.ui.listeners.UIActionListener;
import com.osframework.modellibrary.common.*;
import com.osframework.framework.logging.*;
import com.osrmt.modellibrary.reference.group.*;
import com.osframework.modellibrary.reference.security.*;
import com.osframework.modellibrary.reference.common.*;
import com.osframework.modellibrary.reference.group.ApplicationFramework;
import com.osframework.modellibrary.reference.group.ApplicationSettingFramework;
import com.osframework.modellibrary.reference.group.ReferenceGroup;
import com.osframework.modellibrary.reference.group.SystemInfoFramework;
import com.osframework.modellibrary.reportwriter.ReportModel;
import com.osframework.modellibrary.system.RecordFileModel;
import com.osframework.modellibrary.system.RecordParameterControlList;
import com.osframework.modellibrary.reference.group.*;
import com.osrmt.GlobalConstants;
import com.osrmt.appclient.artifact.form.*;
import com.osrmt.appclient.artifact.graph.TraceabilityGraph;
import com.osrmt.appclient.artifact.graph.UIGraph;
import javax.swing.tree.TreePath;

public class RequirementManagerActions {

	private RequirementManagerController controller;
	private MouseAdapter popupNew;
	private UIReferenceSearch referenceSearch;
	private UIProductSearch productSearch;
	private UIInputBox inputNewProduct;
	private JFrame frame;
	private ChangeControlViewController changeControlViewController = null;
	private TraceabilityViewController traceViewController = null;

	public RequirementManagerActions(JFrame frame) {
		this.frame = frame;
	}

	public ApplicationActionList getActions(RequirementManagerController rmc) {
		controller = rmc;

		ApplicationActionList actions = new ApplicationActionList();

		// FILE MENU
		actions.addApplicationAction(getFileNewProduct());
		actions.addApplicationAction(getFileOpenProduct());
		actions.addApplicationAction(getFileCloseProduct());
		actions.addApplicationAction(getFileNewArtifact());
		actions.addApplicationAction(getFileRefresh());
		actions.addApplicationAction(getFileChangePassword());
		actions.addApplicationAction(getFilePrint());
		actions.addApplicationAction(getFileImport());
		actions.addApplicationAction(getFileExport());
		actions.addApplicationAction(getExportBranch());
		actions.addApplicationAction(getFileExit());

		// EDIT MENU
		actions.addApplicationAction(getEditArtifact());
		actions.addApplicationAction(getEditMoveUp());
		actions.addApplicationAction(getEditMoveDown());
		actions.addApplicationAction(getEditDelete());
		actions.addApplicationAction(getEditFind());

		// TOOLS MENU
		actions.addApplicationAction(getToolsReports());
		actions.addApplicationAction(getToolsTrace());
		actions.addApplicationAction(getToolsTraceBranch());
		actions.addApplicationAction(getToolsFilter());
		actions.addApplicationAction(getToolsImpact());

		// ADMIN MENU
		actions.addApplicationAction(getAdminReference());
		actions.addApplicationAction(getAdminUsers());
		actions.addApplicationAction(getAdminPositions());
		actions.addApplicationAction(getAdminUserGroups());
		actions.addApplicationAction(getAdminProjects());

		// SYSTEM MENU
		actions.addApplicationAction(getSystemForms());
		actions.addApplicationAction(getSystemReference());
		actions.addApplicationAction(getSystemNewArtifact());
		actions.addApplicationAction(getSystemNewArtifactField());
		actions.addApplicationAction(getSystemExport());
		actions.addApplicationAction(getSystemImport());
		actions.addApplicationAction(getSystemDebug());
		actions.addApplicationAction(getSystemBaseline());
		actions.addApplicationAction(getSystemOptions());

		// VIEW MENU
		actions.addApplicationAction(getTraceabilityView());
		actions.addApplicationAction(getChangeControlView());

		// HELP MENU
		actions.addApplicationAction(getSystemLog());
		actions.addApplicationAction(getHelpAbout());
		actions.addApplicationAction(showHelp());

		return actions;
	}

	/*
	 * private void prepareTree(final ArtifactFormController afc, final
	 * ArtifactModel m) { final UITreeNode node =
	 * controller.getReqTree().getSelectedNode(); ActionListener al = new
	 * ActionListener() { public void actionPerformed(ActionEvent e) { try {
	 * node.removeAllChildren(); //TODO this must go
	 * RequirementServices.addChildren(node, m); } catch (Exception e1) {
	 * Debug.LogException(this,e1); } resetSystemState(); } };
	 * afc.setTreeNode(controller.getReqTree().getTreeModel(), node, al); }
	 */

	public ApplicationAction getAdminReference() {
		return new ApplicationAction(ActionGroup.REQMGRADMINREF, null, new UIActionListener(controller.ui) {
			public void actionExecuted(ActionEvent me) {
				try {
					ReferenceWizard wizard = new ReferenceWizard(frame);
					wizard.start(false);
				} catch (Exception ex) {
					Debug.LogException(this, ex);
				}
				resetSystemState();
			}
		});
	}

	public ApplicationAction getSystemReference() {
		return new ApplicationAction(ActionGroup.REQMGRSYSTEMREF, null, new UIActionListener(controller.ui) {
			public void actionExecuted(ActionEvent me) {
				try {
					ReferenceWizard wizard = new ReferenceWizard(frame);
					wizard.start(true);
				} catch (Exception ex) {
					Debug.LogException(this, ex);
				}
				resetSystemState();
			}
		});
	}

	public ApplicationAction getSystemNewArtifact() {
		return new ApplicationAction(ActionGroup.REQMGRSYSTEMNEWARTIFACT, null, new UIActionListener(controller.ui) {
			public void actionExecuted(ActionEvent me) {
				final String newArtifact = JOptionPane.showInputDialog(frame,
						ReferenceServices.getDisplay(SystemMessageFramework.ENTERNEWARTIFACTNAME),
						ReferenceServices.getDisplay(FormTitleFramework.NEWARTIFACT), JOptionPane.PLAIN_MESSAGE);
				if (newArtifact != null) {
					referenceSearch = new UIReferenceSearch(controller.ui);
					referenceSearch.start(ReferenceGroup.Artifact,
							ReferenceServices.getMsg(FormTitleFramework.COPYFIELDSSECURITYFROM), true);
					referenceSearch.setSize(UIProperties.getDIALOG_SIZE_450_330());
					referenceSearch.addOkActionListener(new UIActionListener(controller.ui) {
						public void actionExecuted(ActionEvent e) {
							try {
								// TODO should force selection before ok
								if (referenceSearch.getSelectedValue() != null) {
									SecurityServices.addNewArtifact(newArtifact,
											referenceSearch.getSelectedValue().getRefId());
									referenceSearch.dispose();
									Debug.displayGUIMessage(newArtifact);
								}
								resetSystemState();
							} catch (Exception ex) {
								Debug.LogException(this, ex);
							}
						}
					});
					referenceSearch.setVisible(true);
				}
			}
		});
	}

	public ApplicationAction getSystemNewArtifactField() {
		return new ApplicationAction(ActionGroup.REQMGRSYSTEMNEWARTIFACTFIELD, null,
				new UIActionListener(controller.ui) {
					public void actionExecuted(ActionEvent me) {
						final UIReferenceSearch referenceSearch = new UIReferenceSearch(controller.ui);
						referenceSearch.start(ReferenceGroup.Artifact,
								ReferenceServices.getMsg(FormTitleFramework.SELECTARTIFACTFORMTOEDIT), true);
						referenceSearch.setSize(UIProperties.getDIALOG_SIZE_450_330());
						referenceSearch.addOkActionListener(new UIActionListener(controller.ui) {
							public void actionExecuted(ActionEvent e) {
								try {
									// TODO should force selection before ok
									if (referenceSearch.getSelectedValue() != null) {
										addNewField(referenceSearch.getSelectedValue());
										referenceSearch.dispose();
									}
									resetSystemState();
								} catch (Exception ex) {
									Debug.LogException(this, ex);
								}
							}
						});
						referenceSearch.setVisible(true);
					}
				});
	}

	private void addNewField(final ReferenceDisplay artifact) throws Exception {

		final UIReferenceSearch referenceSearch = new UIReferenceSearch(controller.ui);
		referenceSearch.start(ReferenceGroup.ControlTemplate,
				ReferenceServices.getMsg(FormTitleFramework.SELECTFIELDTEMPLATE), false);
		referenceSearch.setSize(UIProperties.getDIALOG_SIZE_450_330());
		referenceSearch.addOkActionListener(new UIActionListener(controller.ui) {
			public void actionExecuted(ActionEvent e) {
				try {
					// TODO should force selection before ok
					if (referenceSearch.getSelectedValues() != null) {
						SecurityServices.addNewArtifactField(artifact, referenceSearch.getSelectedValues());
						referenceSearch.dispose();
						String msg = referenceSearch.getSelectedValues().size() == 1
								? referenceSearch.getSelectedValues().getFirst().getDisplay()
								: String.valueOf(referenceSearch.getSelectedValues().size());
						msg = msg + " " + SystemMessageFramework.FIELDSADDED;
						Debug.displayGUIMessage(msg);
					}
					resetSystemState();
				} catch (Exception ex) {
					Debug.LogException(this, ex);
				}
			}
		});
		referenceSearch.setVisible(true);
	}

	public ApplicationAction getSystemExport() {
		RequirementManagerUI ui = controller.ui;
		return new ApplicationAction(ActionGroup.REQMGRSYSTEMEXPORT, null, new UIActionListener(controller.ui) {
			public void actionExecuted(ActionEvent me) {
				try {
					SystemServices.exportSystem(ui);
				} catch (Exception ex) {
					Debug.LogException(this, ex);
				}
				resetSystemState();
			}
		});
	}

	public ApplicationAction getSystemImport() {
		return new ApplicationAction(ActionGroup.REQMGRSYSTEMIMPORT, null, new UIActionListener(controller.ui) {
			public void actionExecuted(ActionEvent me) {
				try {
					//
				} catch (Exception ex) {
					Debug.LogException(this, ex);
				}
				resetSystemState();
			}
		});
	}

	public ApplicationAction getSystemDebug() {
		return new ApplicationAction(ActionGroup.REQMGRSYSTEMDEBUG, null, new UIActionListener(controller.ui) {
			public void actionExecuted(ActionEvent me) {
				try {
					SecurityServices.toggleDebugSql();
				} catch (Exception ex) {
					Debug.LogException(this, ex);
				}
				resetSystemState();
			}
		});
	}

	public ApplicationAction getSystemBaseline() {
		return new ApplicationAction(ActionGroup.REQMGRSYSTEMBASELINE, getProductOpenControlState(),
				new UIActionListener(controller.ui) {
					public void actionExecuted(ActionEvent me) {
						try {
							BaselineForm baseline = new BaselineForm(frame);
							baseline.setVisible(true);
						} catch (Exception ex) {
							Debug.LogException(this, ex);
						}
						resetSystemState();
					}
				});
	}

	public ApplicationAction getSystemOptions() {
		return new ApplicationAction(ActionGroup.REQMGRSYSTEMOPTIONS, null, new UIActionListener(controller.ui) {
			public void actionExecuted(ActionEvent me) {
				try {
					SystemOptionForm options = new SystemOptionForm(frame);
					options.setVisible(true);
				} catch (Exception ex) {
					Debug.LogException(this, ex);
				}
				resetSystemState();
			}
		});
	}

	public ApplicationAction getAdminUsers() {
		return new ApplicationAction(ActionGroup.REQMGRADMINUSERS, null, new UIActionListener(controller.ui) {
			public void actionExecuted(ActionEvent me) {
				try {
					UserAdminWizard wizard = new UserAdminWizard(frame);
					wizard.start();
				} catch (Exception ex) {
					Debug.LogException(this, ex);
				}
				resetSystemState();
			}
		});
	}

	public ApplicationAction getAdminPositions() {
		return new ApplicationAction(ActionGroup.REQMGRADMINPOSITIONS, null, new UIActionListener(controller.ui) {
			public void actionExecuted(ActionEvent me) {
				try {
					PositionWizard wizard = new PositionWizard(frame);
					wizard.start();
				} catch (Exception ex) {
					Debug.LogException(this, ex);
				}
				resetSystemState();
			}
		});
	}

	public ApplicationAction getAdminUserGroups() {
		return new ApplicationAction(ActionGroup.REQMGRADMINUSERGROUPS, null, new UIActionListener(controller.ui) {
			public void actionExecuted(ActionEvent me) {
				try {
					UserGroupWizard wizard = new UserGroupWizard(frame);
					wizard.start();
				} catch (Exception ex) {
					Debug.LogException(this, ex);
				}
				resetSystemState();
			}
		});
	}

	public ApplicationAction getAdminProjects() {
		return new ApplicationAction(ActionGroup.REQMGRADMINPROJECTS, null, new UIActionListener(controller.ui) {
			public void actionExecuted(ActionEvent me) {
				try {
					ProjectWizard wizard = new ProjectWizard(frame);
					wizard.start();
				} catch (Exception ex) {
					Debug.LogException(this, ex);
				}
				resetSystemState();
			}
		});
	}

	public ApplicationAction getSystemForms() {
		return new ApplicationAction(ActionGroup.REQMGRSYSTEMFORMS, null, new UIActionListener(controller.ui) {
			public void actionExecuted(ActionEvent me) {
				try {
					SystemFormWizard wizard = new SystemFormWizard(frame);
					wizard.start();
				} catch (Exception ex) {
					Debug.LogException(this, ex);
				}
				resetSystemState();
			}
		});
	}

	public ApplicationAction getToolsReports() {
		return new ApplicationAction(ActionGroup.REQMGRTOOLSREPORTS, null, new UIActionListener(controller.ui) {
			public void actionExecuted(ActionEvent me) {
				try {
					ReportWizard wizard = new ReportWizard(controller.ui);
					wizard.start();
				} catch (Exception ex) {
					Debug.LogException(this, ex);
				}
				resetSystemState();
			}
		});
	}

	public ApplicationAction getToolsTrace() {
		return new ApplicationAction(ActionGroup.REQMGRTOOLSTRACE, getProductOpenControlState(),
				new UIActionListener(controller.ui) {
					public void actionExecuted(ActionEvent me) {
						TraceFormController afc = new TraceFormController(frame);
						afc.start();
						resetSystemState();
					}
				});
	}

	public ApplicationAction getToolsTraceBranch() {
		return new ApplicationAction(ActionGroup.REQMGRTOOLSTRACEBRANCH, getProductOpenControlState(),
				new UIActionListener(controller.ui) {
					public void actionExecuted(ActionEvent me) {
						try {
							if (controller.getReqTree().getSelectedNode() != null) {
								ArtifactModel am = (ArtifactModel) controller.getReqTree().getSelectedNode()
										.getUserObject();
								if (traceViewController == null) {
									traceViewController = new TraceabilityViewController();
								}
								traceViewController.start(am);
							}
						} catch (Exception ex) {
							Debug.LogException(this, ex);
						}
						resetSystemState();
					}
				});
	}

	public ApplicationAction getToolsImpact() {
		return new ApplicationAction(ActionGroup.REQMGRTOOLSIMPACT, getTreeArtifactSelected(),
				new UIActionListener(controller.ui) {
					public void actionExecuted(ActionEvent me) {
						final UIStandardDialog dialog = new UIStandardDialog(frame);
						final ArtifactModel a = (ArtifactModel) controller.getReqTree().getSelectedNode()
								.getUserObject();
						dialog.setTitle(ReferenceServices.getMsg(FormTitleFramework.TRACEABILITYIMPACT));
						TraceabilityGraph trace = new TraceabilityGraph();
						UIGraph graph = trace.createGraph(a.getArtifactId());
						dialog.getCenterPanel().add(new JScrollPane(graph), BorderLayout.CENTER);
						dialog.getButtonPanel().initialize(UIPanelButton.CLOSEONLY);
						dialog.getButtonPanel().getCmdButton0().addActionListener(new UIActionListener(frame) {
							public void actionExecuted(ActionEvent e) throws Exception {
								dialog.dispose();
							}
						});
						dialog.setVisible(true);
						resetSystemState();
					}
				});
	}

	public ApplicationAction getToolsFilter() {
		return new ApplicationAction(ActionGroup.REQMGRTOOLSFILTER, new ControlState() {
			public boolean getEnabled() {
				return isTreeArtifactSelected();
			};
		}, new UIActionListener(controller.ui) {
			public void actionExecuted(ActionEvent me) {
				final ArtifactModel a = (ArtifactModel) controller.getReqTree().getSelectedNode().getUserObject();
				final ArtifactListFilterForm form = new ArtifactListFilterForm(frame, a.getArtifactRefId(),
						controller.getFilterParameters(a.getArtifactRefId()));
				form.setLastOkAction(new UIActionListener(frame) {
					public void actionExecuted(ActionEvent e) throws Exception {
						controller.setFilterParameters(form.getParameterControlList(), a.getArtifactRefId());
						controller.updateArtifactList(a);
						controller.setArtifactList(controller.getCurrentArtifactList(), a.getArtifactRefId());
						resetSystemState();
					}
				});
				form.getPanelButton().getCmdButton2().addActionListener(new UIActionListener(frame) {
					public void actionExecuted(ActionEvent e) throws Exception {
						controller.setFilterParameters(new RecordParameterControlList(), a.getArtifactRefId());
						controller.updateArtifactList(a);
						form.dispose();
					};
				});
				form.setVisible(true);
			}
		});
	}

	public ApplicationAction getEditArtifact() {
		return new ApplicationAction(ActionGroup.REQMGREDITARTIFACT, getArtifactListSelected(),
				new DoubleClickListener() {
					public void call(ActionEvent me) {
						try {
							Object selected = null;
							if (me.getSource() instanceof JTree) {
								if (controller.getReqTree().getSelectedNode() != null) {
									selected = controller.getReqTree().getSelectedNode().getUserObject();
								}
							} else if (controller.getTableList().isRowSelected()) {
								selected = controller.getTableList().getSelectedRow();
							}
							if (selected != null && selected instanceof ArtifactModel) {
								final ArtifactModel selectedArtifact = (ArtifactModel) selected;
								SwingWorker sw = new SwingWorker(frame) {

									@Override
									protected void doNonUILogic() throws Exception {
										ArtifactModel am = RequirementServices
												.getArtifact(selectedArtifact.getArtifactId());
										if (SecurityServices.isReadOnlyControlList(
												ApplicationFramework.get(ApplicationGroup.ARTIFACTFORM),
												am.getArtifactRefId())) {
											ArtifactReadOnlyFormController afc = new ArtifactReadOnlyFormController(
													frame);
											afc.start(am);
										} else {
											ArtifactFormController afc = new ArtifactFormController(frame);
											afc.setScrollList(controller.getTableList().getSelectedIndex(),
													(ArtifactList) controller.getTableList().getResultList());
											afc.start(am.getArtifactRefId(), am, selectedArtifact,
													(UITreeNode) controller.getReqTree().getSelectedNode());
										}
									}

									@Override
									protected void doUIUpdateLogic() throws Exception {
										// TODO Auto-generated method stub

									}

								};
								sw.start();
							}
						} catch (Exception ex) {
							Debug.LogException(this, ex);
						}
						resetSystemState();
					}
				});
	}

	private ControlState canArtifactMoveUp() {
		return new ControlState() {
			public boolean getEnabled() {
				if (isTreeArtifactModelSelected() && controller.getReqTree().getSelectedNodes().length == 1) {
					UITreeNode node = (UITreeNode) controller.getReqTree().getSelectedNode();
					UITreeNode parent = (UITreeNode) node.getParent();
					return parent.getIndex(node) > 0;
				}
				return false;
			}
		};
	}

	private ControlState canArtifactMoveDown() {
		return new ControlState() {
			public boolean getEnabled() {
				if (isTreeArtifactModelSelected() && controller.getReqTree().getSelectedNodes().length == 1) {
					UITreeNode node = (UITreeNode) controller.getReqTree().getSelectedNode();
					UITreeNode parent = (UITreeNode) node.getParent();
					return parent.getIndex(node) < parent.getChildCount() - 1;
				}
				return false;
			}
		};
	}

	private void moveNode(UITreeNode node, UITreeNode parent, boolean up, int prevIndex, int newIndex) {
		parent.remove(prevIndex);
		parent.insert(node, newIndex);
		updateArtifacts(parent);
		controller.getReqTree().getTreeModel().nodeStructureChanged(parent);
		controller.getReqTree().getTree()
				.setSelectionPath(new TreePath(((UITreeNode) parent.getChildAt(newIndex)).getPath()));

	}

	private void updateArtifacts(UITreeNode parent) {
		ArrayList<Integer> list = new ArrayList<Integer>(parent.getChildCount());
		for (int i = 0; i < parent.getChildCount(); i++) {
			UITreeNode child = (UITreeNode) parent.getChildAt(i);
			ArtifactModel am = (ArtifactModel) child.getUserObject();
			list.add(new Integer(am.getArtifactId()));
		}
		RequirementServices.updateArtifactSequence(list);
	}

	public ApplicationAction getEditMoveUp() {
		return new ApplicationAction(ActionGroup.REQMGRMOVEUP, canArtifactMoveUp(), new DoubleClickListener() {
			public void call(ActionEvent me) {
				try {
					if (canArtifactMoveUp().getEnabled()) {
						UITreeNode node = (UITreeNode) controller.getReqTree().getSelectedNode();
						UITreeNode parent = (UITreeNode) node.getParent();
						moveNode(node, parent, true, parent.getIndex(node), parent.getIndex(node) - 1);
					}
				} catch (Exception ex) {
					Debug.LogException(this, ex);
				}
				resetSystemState();
			}
		});
	}

	public ApplicationAction getEditMoveDown() {
		return new ApplicationAction(ActionGroup.REQMGRMOVEDOWN, canArtifactMoveDown(), new DoubleClickListener() {
			public void call(ActionEvent me) {
				try {
					if (canArtifactMoveDown().getEnabled()) {
						UITreeNode node = (UITreeNode) controller.getReqTree().getSelectedNode();
						UITreeNode parent = (UITreeNode) node.getParent();
						moveNode(node, parent, false, parent.getIndex(node), parent.getIndex(node) + 1);
					}
				} catch (Exception ex) {
					Debug.LogException(this, ex);
				}
				resetSystemState();
			}
		});
	}

	public ApplicationAction getEditDelete() {
		return new ApplicationAction(ActionGroup.REQMGREDITDELETE, getTreeArtifactSelected(),
				new UIActionListener(controller.ui) {
					public void actionExecuted(ActionEvent ae) {
						try {
							if (controller.getReqTree().isNodeSelected()) {
								UITreeNode[] selectedNodes = new UITreeNode[controller.getReqTree()
										.getSelectedNodes().length];
								for (int i = 0; i < selectedNodes.length; i++) {
									selectedNodes[i] = (UITreeNode) controller.getReqTree().getSelectedNodes()[i];
								}
								List<Integer> deleteIds = new ArrayList<Integer>();
								final List<UITreeNode> deletedNodes = new ArrayList<UITreeNode>();
								for (int i = 0; i < selectedNodes.length; i++) {

									UITreeNode selectedNode = selectedNodes[i];
									final ArtifactModel a = (ArtifactModel) selectedNode.getUserObject();
									if (a.getArtifactId() != 0) {
										deleteIds.add(a.getArtifactId());
										deletedNodes.add(selectedNode);
									}
//							ArtifactList children = RequirementServices.getAllSameChildren(a.getArtifactRefId(), a.getArtifactId());
									// deleteList.addAll(children);
									// deletedParentNodes.add(parentNode);
								}

								int recursiveDeleteCount = RequirementServices.countArtifactsToDelete(deleteIds);
								if (JOptionPane.YES_OPTION != JOptionPane.showConfirmDialog(frame,
										ReferenceServices.getDisplay(FormButtonTextGroup.DELETE) + " "
												+ recursiveDeleteCount + " "
												+ ReferenceServices.getDisplay(SystemMessageGroup.ARTIFACTSFOUND))) {
									return;
								}
								/*
								 * Enumeration e1 = deleteList.elements(); while (e1.hasMoreElements()) {
								 * ArtifactModel am = (ArtifactModel) e1.nextElement(); am.setActiveInd(0); }
								 */
								RequirementServices.DeleteArtifacts(deleteIds);
								SwingWorker sw = new SwingWorker(frame) {

									@Override
									protected void doNonUILogic() throws Exception {
										Thread.sleep(500);

									}

									@Override
									protected void doUIUpdateLogic() throws Exception {
										for (UITreeNode deletedNode : deletedNodes) {

											controller.getReqTree().getTreeModel().removeNodeFromParent(deletedNode);
										}
									}

								};
								sw.start();
								controller.setArtifactList(new ArtifactList(), 0);
							}
						} catch (Exception e) {
							Debug.LogException(this, e);
						}
						resetSystemState();
					}
				});
	}

	public ApplicationAction getEditFind() {
		return new ApplicationAction(ActionGroup.REQMGREDITFIND, getTreeArtifactSelected(),
				new UIActionListener(controller.ui) {
					public void actionExecuted(ActionEvent ae) {
						try {
							if (controller.getReqTree().isNodeSelected()) {
								final ArtifactModel a = (ArtifactModel) controller.getReqTree().getSelectedNode()
										.getUserObject();
								String searchString = JOptionPane.showInputDialog(frame,
										ReferenceServices.getMsg(FormButtonTextFramework.SEARCHANYPARTOFARTIFACTS)
												+ ": " + ReferenceServices.getDisplay(a.getArtifactRefId()),
										ReferenceServices.getMsg(FormTitleFramework.SEARCH) + " "
												+ ReferenceServices.getDisplay(a.getArtifactRefId()),
										JOptionPane.PLAIN_MESSAGE);
								ArtifactList list = RequirementServices.getAllSameChildren(a.getArtifactRefId(), 0);
								controller.setArtifactList(artifactSearch(list, searchString), a.getArtifactRefId());
							}
						} catch (Exception e) {
							Debug.LogException(this, e);
						}
						resetSystemState();
					}
				});
	}

	public ArtifactList artifactSearch(ArtifactList list, String s) {
		ArtifactList searchList = new ArtifactList();
		Enumeration e1 = list.elements();
		while (e1.hasMoreElements()) {
			ArtifactModel m = (ArtifactModel) e1.nextElement();
			if (m.allToString().toLowerCase().replace("\r\n", "").contains(s.toLowerCase())
					|| m.allToString().replace("\r\n", "").toLowerCase().matches(s)) {
				searchList.add(m);
			}
		}
		return searchList;
	}

	public ApplicationAction getFileNewProduct() {
		return new ApplicationAction(ActionGroup.REQMGRNEWPRODUCT, null, new UIActionListener(controller.ui) {
			public void actionExecuted(ActionEvent ae) throws Exception {
				createProductInputPopup();
			}
		});
	}

	private void createProductInputPopup() {
		// TODO Auto-generated method stub
		inputNewProduct = new UIInputBox(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (inputNewProduct.getInputText() != null && !inputNewProduct.getInputText().isEmpty()) {
					try {
						ReferenceModel m = new ReferenceModel();
						m.setReferenceGroup(ReferenceGroup.Product);
						m.setDisplay(inputNewProduct.getInputText());
						m.setDisplayCode();
						ReferenceServices.updateReference(m);
						ApplicationObject ao = new ApplicationObject();
						// TODO duplicate code with open
						ReferenceDisplay rd = ReferenceServices.getDisplay(ReferenceGroup.Product, m.getDisplayCode());
						ao.setProductRefId(rd.getRefId(), ReferenceServices.getDisplay(rd.getRefId()));
						Application.setObject(ao);
						controller.ui.setTitle(ao.getProductRefDisplay() + " " + Application.getUser().getUserLogin()
								+ "@" + SecurityServices.getEnvironment());
						inputNewProduct.dispose();
						controller.buildTree();
					} catch (Exception ex) {
						Debug.LogException(this, ex);
					}
					resetSystemState();
				} else {
					JOptionPane.showMessageDialog(frame, "Please Enter valid Product ");
					inputNewProduct.dispose();
					createProductInputPopup();
				}
			}
		}, ReferenceServices.getMsg(FormTitleFramework.NEWPRODUCT), controller.ui,
				ReferenceServices.getMsg(FormButtonTextFramework.PRODUCT));
		
		inputNewProduct.setVisible(true);
	}

	ApplicationAction getFileOpenProduct() {
		return new ApplicationAction(ActionGroup.REQMGRFILEOPEN, null, new UIActionListener(controller.ui) {
			public void actionExecuted(ActionEvent ae) throws Exception {
				referenceSearch = new UIProductSearch(controller.ui);
				referenceSearch.start(ReferenceGroup.Product, ReferenceServices.getMsg(FormTitleFramework.OPENPRODUCT),
						true);
				referenceSearch.setSize(UIProperties.getDIALOG_SIZE_450_330());
				referenceSearch.addOkActionListener(new UIActionListener(controller.ui) {
					public void actionExecuted(ActionEvent e) {
						ApplicationObject ao = new ApplicationObject();
						// TODO should force selection before ok
						if (referenceSearch.getSelectedValue() != null) {
							ao.setProductRefId(referenceSearch.getSelectedValue().getRefId(),
									ReferenceServices.getDisplay(referenceSearch.getSelectedValue().getRefId()));
							Application.setObject(ao);
							controller.ui.setTitle(ao.getProductRefDisplay() + " - "
									+ Application.getUser().getUserLogin() + "@" + SecurityServices.getEnvironment());
							referenceSearch.dispose();
							controller.setArtifactList(new ArtifactList(), 0);
						}
						resetSystemState();
					}
				});
				referenceSearch.setVisible(true);
				SwingWorker sw = new SwingWorker(controller.getReqTree().getRootPane()) {

					@Override
					protected void doNonUILogic() throws Exception {
						controller.buildTree();
					}

					@Override
					protected void doUIUpdateLogic() throws Exception {
						// TODO Auto-generated method stub

					}

				};
				sw.start();
			}
		});
	}

	public ApplicationAction getFileCloseProduct() {
		return new ApplicationAction(ActionGroup.REQMGRFILECLOSE, getProductOpenControlState(),
				new UIActionListener(controller.ui) {
					public void actionExecuted(ActionEvent ae) throws Exception {
						if (Application.getObject() != null) {
							ApplicationObject ao = (ApplicationObject) Application.getObject();
							if (ao.getProductRefId() == 0) {
								Debug.LogError(this, ReferenceServices.getMsg(SystemMessageFramework.OPENAPRODUCT));
							} else {
								ao.setProductRefId(0, "");
								controller.buildTree();
								controller.setArtifactList(new ArtifactList(), 0);
							}
						} else {
							Debug.LogError(this, ReferenceServices.getMsg(SystemMessageFramework.NULLPOINTEREXCEPTION));
						}
						resetSystemState();
					}
				});
	}

	public ApplicationAction getFileNewArtifact() {
		return new ApplicationAction(ActionGroup.REQMGRFILENEW, getProductOpenControlState(),
				new UIActionListener(controller.ui) {
					public void actionExecuted(ActionEvent ae) {
						if (ae.getSource() != null && ae.getSource() instanceof UIMenuItem) {
							UIMenuItem menuItem = (UIMenuItem) ae.getSource();
							ApplicationSecurityModel asm = (ApplicationSecurityModel) menuItem.getUserObject();
							ApplicationViewModel avm = SecurityServices.getApplicationView(asm.getApplicationViewId());
							if (controller.getReqTree().isNodeSelected()) {
								Object o = controller.getReqTree().getSelectedObject();
								if (o != null && o instanceof ArtifactModel) {
									ArtifactModel parentArtifact = (ArtifactModel) controller.getReqTree()
											.getSelectedObject();
									ArtifactFormController afc = new ArtifactFormController(frame);
									// prepareTree(afc, parentArtifact);
									afc.startChild(avm.getAppTypeRefId(), parentArtifact,
											(UITreeNode) controller.getReqTree().getSelectedNode());
								}
							} else {
								ArtifactFormController afc = new ArtifactFormController(frame);
								afc.start(avm.getAppTypeRefId());
							}
							resetSystemState();
						} else {
							if (isTreeArtifactSelected()) {
								Object o = controller.getReqTree().getSelectedObject();
								ArtifactModel parentArtifact = (ArtifactModel) o;
								ArtifactFormController afc = new ArtifactFormController(frame);
								// prepareTree(afc, parentArtifact);
								afc.startChild(parentArtifact.getArtifactRefId(), parentArtifact,
										(UITreeNode) controller.getReqTree().getSelectedNode());
								resetSystemState();
							} else {
								Debug.displayGUIMessage(
										ReferenceServices.getMsg(SystemMessageFramework.SELECTANARTIFACT));
							}
						}
						controller.ui.repaint();
					}
				});
	}

	public ApplicationAction getFileRefresh() {
		return new ApplicationAction(ActionGroup.REQMGRFILEREFRESH, getProductOpenControlState(),
				new UIActionListener(controller.ui) {
					public void actionExecuted(ActionEvent ae) throws Exception {
						controller.buildTree();
					}
				});
	}

	public ApplicationAction getFileImport() {
		return new ApplicationAction(ActionGroup.REQMGRFILEIMPORT, getProductOpenControlState(),
				new UIActionListener(controller.ui) {
					public void actionExecuted(ActionEvent ae) throws Exception {
						ArtifactModel parentArtifact = new ArtifactModel();
						if (controller.getReqTree().getSelectedNode() != null
								&& controller.getReqTree().getSelectedNode().getUserObject() instanceof ArtifactModel) {
							final ArtifactModel a = (ArtifactModel) controller.getReqTree().getSelectedNode()
									.getUserObject();
							parentArtifact = a;
						}
						ArtifactListImportForm form = new ArtifactListImportForm(frame, parentArtifact);
						form.setVisible(true);
					}
				});
	}

	public ApplicationAction getFileExport() {
		return new ApplicationAction(ActionGroup.REQMGRFILEEXPORT, getProductOpenControlState(),
				new UIActionListener(controller.ui) {
					public void actionExecuted(ActionEvent ae) throws Exception {
						ArtifactListExportForm form = new ArtifactListExportForm(frame,
								controller.getCurrentArtifactList());
						form.setVisible(true);
					}
				});
	}

	public ApplicationAction getExportBranch() {
		return new ApplicationAction(ActionGroup.REQMGREXPORTBRANCH, getTreeArtifactSelected(),
				new UIActionListener(controller.ui) {
					public void actionExecuted(ActionEvent ae) throws Exception {
						if (controller.getReqTree().getSelectedNode() != null) {
							ArtifactModel am = (ArtifactModel) controller.getReqTree().getSelectedNode()
									.getUserObject();
							ArtifactList list = new ArtifactList();
							if (am.getArtifactId() > 0) {
								list = RequirementServices.getAllSameChildren(am.getArtifactRefId(),
										am.getArtifactId());
							} else if (am.getArtifactRefId() > 0) {
								list = RequirementServices.getAllItems(am.getArtifactRefId());
							}
							ArtifactListExportForm form = new ArtifactListExportForm(frame, list);
							form.setVisible(true);
						}
					}
				});
	}

	public ApplicationAction getFilePrint() {
		return new ApplicationAction(ActionGroup.REQMGRFILEPRINT, getArtifactListSelected(),
				new UIActionListener(controller.ui) {
					public void actionExecuted(ActionEvent me) {
						try {
							ArtifactList list = new ArtifactList();
							Vector v = controller.getTableList().getSelectedRows();
							Enumeration e1 = v.elements();
							while (e1.hasMoreElements()) {
								ArtifactModel am = (ArtifactModel) e1.nextElement();
								list.add(am);
							}
							if (list.size() > 0) {
								ReportWizard wizard = new ReportWizard(list, controller.ui);
								wizard.start();
							}
						} catch (Exception ex) {
							Debug.LogException(this, ex);
						}
						resetSystemState();
					}
				});
	}

	public ApplicationAction getFileChangePassword() {
		return new ApplicationAction(ActionGroup.REQMGRCHANGEPASSWORD, null, new UIActionListener(controller.ui) {
			public void actionExecuted(ActionEvent ae) throws Exception {
				ChangePasswordFormController cpfc = new ChangePasswordFormController(frame);
				cpfc.start(Application.getUser());
			}
		});
	}

	public ApplicationAction getFileExit() {
		return new ApplicationAction(ActionGroup.REQMGRFILEEXIT, null, new UIActionListener(controller.ui) {
			public void actionExecuted(ActionEvent ae) {
				try {
					Vector v = new Vector();
					v.add(Application.getClientProperty());
					v.add(Application.getClientProperty());
					ClientProperty.saveClients(v);
				} catch (Exception ex) {
					Debug.LogException(this, ex);
				}
				try {
					SecurityServices.printStats();
				} finally {
					System.exit(0);
				}
			}
		});
	}

	public ApplicationAction getSystemLog() {
		return new ApplicationAction(ActionGroup.REQMGRSYSTEMLOG, null, new UIActionListener(controller.ui) {
			public void actionExecuted(ActionEvent ae) throws Exception {
				SystemLogForm sysform = new SystemLogForm(controller.ui, false);
				sysform.setVisible(true);
			}
		});
	}

	public ApplicationAction getHelpAbout() {
		checkVersion();
		return new ApplicationAction(ActionGroup.REQMGRHELPABOUT, null, new UIActionListener(controller.ui) {
			public void actionExecuted(ActionEvent ae) throws Exception {
				String msg = ReferenceServices.getMsg(SystemMessageFramework.VERSION) + ": "
						+ GlobalConstants.APP_VERSION + "\n" + ReferenceServices.getMsg(SystemMessageFramework.DATABASE)
						+ " " + ReferenceServices.getMsg(FormButtonTextFramework.LASTUPDATED) + ": "
						+ GlobalConstants.LAST_UPDATED_DATE + "\n"
						+ ReferenceServices.getMsg(SystemMessageFramework.CLIENT) + " "
						+ ReferenceServices.getMsg(FormButtonTextFramework.LASTUPDATED) + ": "
						+ GlobalConstants.LAST_UPDATED_DATE + "\n"
						+ ReferenceServices.getMsg(SystemMessageFramework.SERVER) + " "
						+ ReferenceServices.getMsg(FormButtonTextFramework.LASTUPDATED) + ": "
						+ GlobalConstants.LAST_UPDATED_DATE + "\n\n";
				JOptionPane.showMessageDialog(frame, msg, ReferenceServices.getMsg(FormTitleFramework.ABOUTOSRMT),
						JOptionPane.INFORMATION_MESSAGE);
				SecurityServices.printStats();
			}
		});
	}

	public ApplicationAction getTraceabilityView() {
		return new ApplicationAction(ActionGroup.VIEWTRACEABILITY, getProductOpenControlState(),
				new UIActionListener(controller.ui) {
					public void actionExecuted(ActionEvent me) {
						try {
							if (traceViewController == null) {
								traceViewController = new TraceabilityViewController();
							}
							traceViewController.start(null);
						} catch (Exception ex) {
							Debug.LogException(this, ex);
						}
						resetSystemState();
					}
				});
	}

	public ApplicationAction getChangeControlView() {
		return new ApplicationAction(ActionGroup.VIEWCHANGECONTROL, null, new UIActionListener(controller.ui) {
			public void actionExecuted(ActionEvent me) {
				try {
					if (changeControlViewController == null) {
						changeControlViewController = new ChangeControlViewController();
					}
					changeControlViewController.start();
				} catch (Exception ex) {
					Debug.LogException(this, ex);
				}
				resetSystemState();
			}
		});
	}

	private void checkVersion() {
		try {
			String dbdate = ReferenceServices.getDisplay(SystemInfoFramework.VERSIONDATE);
			String clientdate = "26-Mar-2007";
			String serverdate = ReferenceServices.getLastUpdated();
			if (!clientdate.equals(serverdate)) {
				Debug.LogError(this,
						ReferenceServices.getMsg(SystemMessageFramework.CLIENT) + " last updated " + clientdate + " != "
								+ ReferenceServices.getMsg(SystemMessageFramework.SERVER) + " last updated "
								+ serverdate);
			}
			if (!clientdate.equals(dbdate)) {
				Debug.LogError(this,
						ReferenceServices.getMsg(SystemMessageFramework.CLIENT) + " last updated " + clientdate + " != "
								+ ReferenceServices.getMsg(SystemMessageFramework.DATABASE) + " last updated "
								+ dbdate);
			}
			if (!serverdate.equals(dbdate)) {
				Debug.LogError(this,
						ReferenceServices.getMsg(SystemMessageFramework.SERVER) + " last updated " + serverdate + " != "
								+ ReferenceServices.getMsg(SystemMessageFramework.DATABASE) + " last updated "
								+ dbdate);
			}
		} catch (Exception ex) {
			Debug.LogException(this, ex);
		}
	}

	/**
	 * Remove any error messages Reset the toolbar
	 */
	public void resetSystemState() {
		try {
			Debug.displayGUIMessage("");
			controller.getToolBar().setEnabled();
			controller.getMenuBar().setEnabled();
		} catch (Exception ex) {
		}
	}

	private ControlState getProductOpenControlState() {
		return new ControlState() {
			public boolean getEnabled() {
				return ApplicationObject.getApplicationProductRefId() > 0;
			}
		};
	}

	private ControlState getArtifactListSelected() {
		return new ControlState() {
			public boolean getEnabled() {
				return controller.getTableList().isRowSelected() && ApplicationObject.getApplicationProductRefId() > 0;
			}
		};
	}

	private ControlState getTreeArtifactSelected() {
		return new ControlState() {
			public boolean getEnabled() {
				return isTreeArtifactSelected();
			}
		};
	}

	private boolean isTreeArtifactSelected() {
		if (controller.getReqTree().isNodeSelected()) {
			Object o = controller.getReqTree().getSelectedObject();
			if (o != null && o instanceof ArtifactModel) {
				return true;
			}
		}
		return false;
	}

	private boolean isTreeArtifactModelSelected() {
		if (controller.getReqTree().isNodeSelected()) {
			Object o = controller.getReqTree().getSelectedObject();
			if (o != null && o instanceof ArtifactModel) {
				return ((ArtifactModel) o).getArtifactId() != 0;
			}
		}
		return false;
	}

	public void showArtifactPopupMenu(int x, int y, ArtifactModel model, UITreeNode node,
			ApplicationActionList actions) {

		MenuManager menuManager = RequirementManagerTools.getMenuManager();
		UIPopupMenu pop = new UIPopupMenu("");
		if (node.isLeaf() && node.getLevel() != 1)
			addEditArtifact(pop, actions, menuManager, node);
		addRefresh(model.getProductRefId(), pop, actions, menuManager, node);
		addTraceArtifacts(pop, actions, menuManager, node);
		addExport(pop, actions, menuManager, node);
		addFileNew(pop, actions, menuManager);
		if (node.isLeaf() && node.getLevel() != 1) {
			pop.addSeparator();
			addDelete(pop, actions, menuManager, node);
		}
		pop.show(controller.getReqTree().getTree(), x, y);
		resetSystemState();
	}

	private void addRefresh(final int productRefId, UIPopupMenu pop, ApplicationActionList actions,
			MenuManager menuManager, final UITreeNode node) {
		ApplicationAction action = actions.getAction(ActionGroup.REQMGRFILEREFRESH);
		action.setActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					node.removeAllChildren();
					((UITreeModel) controller.getReqTree().getTreeModel()).nodeStructureChanged(node);
					controller.getTreeLoader().loadChildren(productRefId, node, false);
					((UITreeModel) controller.getReqTree().getTreeModel()).nodeStructureChanged(node);
				} catch (Exception ex) {
					Debug.LogException(this, ex);
				}
			}

		});
		menuManager.addMenuItem("Menu File - Refresh", ReferenceServices.getMsg(FormButtonTextFramework.POPUPREFRESH),
				action, false, null, pop);

	}

	private void addDelete(UIPopupMenu pop, ApplicationActionList actions, MenuManager menuManager,
			final UITreeNode node) {
		ApplicationAction action = actions.getAction(ActionGroup.REQMGREDITDELETE);
		menuManager.addMenuItem("Menu Edit - Delete", ReferenceServices.getMsg(FormButtonTextFramework.DELETE), action,
				false, null, pop);

	}

	private void addEditArtifact(UIPopupMenu pop, ApplicationActionList actions, MenuManager menuManager,
			final UITreeNode node) {
		ApplicationAction action = actions.getAction(ActionGroup.REQMGREDITARTIFACT);
		menuManager.addMenuItem("Menu Edit - Artifact", ReferenceServices.getMsg(FormButtonTextFramework.EDIT), action,
				false, null, pop);

	}

	private void addTraceArtifacts(UIPopupMenu pop, ApplicationActionList actions, MenuManager menuManager,
			final UITreeNode node) {
		ApplicationAction action = actions.getAction(ActionGroup.REQMGRTOOLSTRACEBRANCH);
		menuManager.addMenuItem("Menu Tools - Trace", ReferenceServices.getMsg(FormButtonTextFramework.TRACEBRANCH),
				action, false, null, pop);

	}

	private void addFileNew(JPopupMenu pop, ApplicationActionList actions, MenuManager menuManager) {
		UIMenu menu = new UIMenu();
		menu.setText(ReferenceServices.getMsg(FormButtonTextFramework.POPUPMENUNEW));

		ApplicationSecurityList list = SecurityServices
				.getAppSecurity(ApplicationFramework.get(ApplicationGroup.ARTIFACTFORM));
		Enumeration e1 = list.elements();
		while (e1.hasMoreElements()) {
			ApplicationSecurityModel asm = (ApplicationSecurityModel) e1.nextElement();
			// ApplicationViewModel avm =
			// SecurityServices.getApplicationView(asm.getApplicationViewId());
			ApplicationAction action = actions.getAction(ActionGroup.REQMGRFILENEW);
			action.setUserObject(asm);
			menuManager.addMenuItem("Menu File - New", asm.getAppTypeRefDisplay(), action, false, null, menu);
		}
		pop.add(menu);
	}

	private void addExport(UIPopupMenu pop, ApplicationActionList actions, MenuManager menuManager,
			final UITreeNode node) {
		ApplicationAction action = actions.getAction(ActionGroup.REQMGREXPORTBRANCH);
		menuManager.addMenuItem("Menu File - Export", ReferenceServices.getMsg(SystemMessageFramework.EXPORTARTIFACTS),
				action, false, null, pop);

	}

	private ApplicationAction showHelp() {
		return new ApplicationAction(ActionGroup.REQMGRHELPOSRMT, null, new UIActionListener(controller.ui) {
			public void actionExecuted(ActionEvent ae) throws Exception {
				JFrame helpFrame = new net.sourceforge.helpgui.gui.MainFrame("/docs/help/", "java");
				helpFrame.setTitle("Help-OSRMT");
				helpFrame.setVisible(true);
			}
		});

	}

}
