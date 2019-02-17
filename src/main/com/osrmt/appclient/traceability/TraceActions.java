package com.osrmt.appclient.traceability;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

import com.osrmt.modellibrary.issue.*;
import com.osrmt.modellibrary.reqmanager.*;
import com.osrmt.appclient.changecontrol.ChangeControlViewController;
import com.osrmt.appclient.common.*;
import com.osrmt.appclient.reqmanager.ArtifactTreeLoader;
import com.osrmt.appclient.reqmanager.ArtifactTreeRenderer;
import com.osrmt.appclient.system.*;
import com.osrmt.appclient.services.*;
import com.osrmt.appclient.traceability.TraceabilityViewController;
import com.osrmt.appclient.wizards.*;
import com.osframework.datalibrary.common.Access;
import com.osframework.datalibrary.common.Db;
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
import com.osframework.appclient.ui.components.*;
import com.osframework.appclient.ui.controls.UIJDialog;
import com.osframework.appclient.ui.controls.UIMenuItem;
import com.osframework.appclient.ui.controls.UIProperties;
import com.osframework.appclient.ui.listeners.DoubleClickListener;
import com.osframework.appclient.ui.listeners.UIActionListener;
import com.osframework.appclient.ui.tree.UITreeModel;
import com.osframework.modellibrary.common.*;
import com.osrmt.modellibrary.reference.group.*;
import com.osrmt.modellibrary.reqmanager.ArtifactModel;
import com.osframework.modellibrary.reference.security.*;
import com.osframework.modellibrary.reference.common.*;
import com.osframework.modellibrary.reference.group.FormButtonTextFramework;
import com.osframework.modellibrary.reference.group.FormTitleFramework;
import com.osframework.modellibrary.reference.group.ReferenceGroup;
import com.osframework.modellibrary.reference.group.SystemInfoFramework;
import com.osframework.modellibrary.reportwriter.ReportModel;
import com.osframework.modellibrary.system.RecordFileModel;
import com.osframework.modellibrary.system.RecordParameterControlList;
import com.osframework.modellibrary.reference.group.*;
import com.osrmt.appclient.artifact.form.*;
import com.osrmt.appclient.artifact.graph.TraceabilityGraph;
import com.osrmt.appclient.artifact.graph.UIGraph;
import javax.swing.tree.TreePath;
import com.osframework.modellibrary.reference.group.SystemMessageFramework;
public class TraceActions {
	
	private TraceabilityViewController controller;
	private JFrame frame;
	
	public TraceActions(JFrame frame) {
		this.frame = frame;
	}

	public ApplicationActionList getActions(TraceabilityViewController ccvc) {
		controller = ccvc;
		
		ApplicationActionList actions = new ApplicationActionList();
		
		// FILE MENU
		//actions.addApplicationAction(getFileNewIssue());
		actions.addApplicationAction(getFileOpenProduct());
		actions.addApplicationAction(getFileRefresh());
		//actions.addApplicationAction(getFilePrint());
		//actions.addApplicationAction(getFileImport());
		//actions.addApplicationAction(getFileExport());
		actions.addApplicationAction(getFileClose());

		// EDIT MENU
		actions.addApplicationAction(getTraceLeftToRight());
		actions.addApplicationAction(getTraceRightToLeft());
		actions.addApplicationAction(getUntraceTrees());
		actions.addApplicationAction(getEditTraceCriteria());
		
		
		return actions;
	}
	
	public ApplicationAction getFileOpenProduct() {
		return   new ApplicationAction(ActionGroup.TRACEFILEOPENPRODUCT,
				null,
				new UIActionListener(controller.ui) {
		public void actionExecuted(ActionEvent ae) throws Exception {
			final UIProductSearch referenceSearch = new UIProductSearch(controller.ui);
			referenceSearch.start(ReferenceGroup.Product, ReferenceServices.getMsg(FormTitleFramework.OPENPRODUCT), true);
			referenceSearch.setSize(UIProperties.getDIALOG_SIZE_450_330());
			referenceSearch.addOkActionListener(new UIActionListener(controller.ui) {
				public void actionExecuted(ActionEvent e) {
					try {
						ApplicationObject ao = new ApplicationObject();
						//TODO should force selection before ok
						if (referenceSearch.getSelectedValue() != null) {
							int productRefId = referenceSearch.getSelectedValue().getRefId();
							buildTargetTree(productRefId);
							referenceSearch.dispose();
						}
						resetSystemState();
					} catch (Exception ex1) {
						Debug.LogException(this, ex1);
					}
				}
			});
			referenceSearch.setVisible(true);
		}
	});
	}
	
	/**
	 * The tree built once with a string root and display models
	 * as child nodes storing the application view id
	 * 
	 * @throws Exception
	 */
	public void buildTargetTree(final int productRefId) throws Exception {
		//controller.getTargettree().getTree().rem
		/*
		SwingWorker sw = new SwingWorker(controller.getTargettree().getRootPane()) {

			@Override
			protected void doNonUILogic() throws Exception {
				controller.getTargettree().setRenderer(ArtifactTreeRenderer.getInstance());
				UITreeModel newModel = RequirementServices.getTopRequirementTreeModel(productRefId);
				setUserObject(newModel);
			}

			@Override
			protected void doUIUpdateLogic() throws Exception {
				controller.getTargettree().display((UITreeModel) getUserObject(), new TreeSelectionListener() {

					public void valueChanged(TreeSelectionEvent e) {
						// TODO Auto-generated method stub
						
					};
				
				});
				ArtifactTreeLoader treeLoader = new ArtifactTreeLoader((UITreeModel) getUserObject(), controller.getTargettree().getTree());
				
			}
			
		};
		sw.start();
		*/
		controller.getTargettree().setRenderer(ArtifactTreeRenderer.getInstance());
		UITreeModel newModel = RequirementServices.getTopRequirementTreeModel(productRefId);
		controller.getTargettree().getTree().removeAll();
		controller.getTargettree().display(newModel, new TreeSelectionListener() {

			public void valueChanged(TreeSelectionEvent e) {
				// TODO Auto-generated method stub
				
			};
		
		});
		ArtifactTreeLoader treeLoader = new ArtifactTreeLoader(productRefId, newModel, controller.getTargettree().getTree());
		controller.getTargettree().getTree().revalidate();


	}
	
	
	public ApplicationAction getEditTraceCriteria() {
		return new ApplicationAction(ActionGroup.TRACEEDITCRITERIA,
				null,
		new UIActionListener(controller.ui) {
			public void actionExecuted(ActionEvent me) {
				final TraceTreeCriteria previousCriteria = new TraceTreeCriteria();
				previousCriteria.updateWith(controller.getTraceCriteria());
				final TraceCriteriaForm form = new TraceCriteriaForm(frame, controller.getTraceCriteria());
				form.setLastOkAction(new UIActionListener(frame) {
					public void actionExecuted(ActionEvent e) throws Exception {
						controller.buildTree();
						resetSystemState();
					}
				});
				form.getPanelButton().getCmdButton0().addActionListener(new UIActionListener(frame) {
					public void actionExecuted(ActionEvent e) throws Exception {
						controller.getTraceCriteria().updateWith(previousCriteria);
						form.setVisible(false);
					};
				});
				form.setVisible(true);
			}
		});
	}
	
	public ApplicationAction getFileRefresh() {
		return new ApplicationAction(ActionGroup.TRACEFILEREFRESH,
				getProductOpenControlState(),
				 new UIActionListener(controller.ui) {
					public void actionExecuted(ActionEvent ae) throws Exception {
						controller.buildTree();
					}
				});
	}
	
	
	public ApplicationAction getFileClose() {
		return new ApplicationAction(ActionGroup.TRACEFILECLOSE,
				null,
				 new UIActionListener(controller.ui) {
					public void actionExecuted(ActionEvent ae) {
						try {
							controller.ui.setVisible(false);
						} catch (Exception ex) {
							Debug.LogException(this, ex);
						}
					}
				});
	}
	
	private void trace(Vector fromList, Vector toList, boolean leftToRight) throws Exception {
		//String msg = ReferenceServices.getDisplay(SystemMessageFramework.TRACE)
		//+ " " + fromList.size() + (leftToRight ? " -> " : " <- ") + toList.size();
		String artifacts = String.valueOf(fromList.size());
		if (fromList.size() == 1) {
			artifacts = ((ArtifactModel) fromList.get(0)).getArtifactName();
		}
		String traceto = String.valueOf(toList.size());
		if (toList.size() == 1) {
			traceto = ((ArtifactModel) toList.get(0)).getArtifactName();
		}
  		String msg = ReferenceServices.getDisplay(SystemMessageFramework.CREATEDEPENDENCYFROM) + " " + (leftToRight ? artifacts : traceto) + " " + 
			ReferenceServices.getDisplay(SystemMessageFramework.ON) + " " + (leftToRight ? traceto : artifacts);
		if (JOptionPane.showConfirmDialog(frame, msg,ReferenceServices.getDisplay(SystemMessageFramework.TRACE), JOptionPane.OK_CANCEL_OPTION)
				== JOptionPane.OK_OPTION) {
			Enumeration e1 = fromList.elements();
			while (e1.hasMoreElements()) {
				ArtifactModel am = (ArtifactModel) e1.nextElement();
				Enumeration e2 = toList.elements();
				while (e2.hasMoreElements()) { 
					ArtifactModel to = (ArtifactModel) e2.nextElement();
					RequirementTreeModel rtm = new RequirementTreeModel();
					rtm.setParentId(leftToRight ? am.getArtifactId() : to.getArtifactId());
					rtm.setParentArtifactRefId(leftToRight ? am.getArtifactRefId() : to.getArtifactRefId());
					rtm.setChildId(leftToRight ? to.getArtifactId() : am.getArtifactId());
					rtm.setChildArtifactRefId(leftToRight ? to.getArtifactRefId() : am.getArtifactRefId());
					rtm.setRelationRefId(com.osrmt.modellibrary.reference.group.RelationGroup.DEPENDENT);
					RequirementServices.UpdateRelationship(rtm);
				}
			}
		}

	}
	
	public ApplicationAction getTraceLeftToRight() {
		return new ApplicationAction(ActionGroup.TRACELEFTTORIGHT,
				areTreesSelected(),
				 new UIActionListener(controller.ui) {
					public void actionExecuted(ActionEvent ae) throws Exception {
						Vector fromList = controller.getTracetree().getSelectedUserObjects();
						Vector toList = controller.getTargettree().getSelectedUserObjects();
						if (fromList.size() == 1 || toList.size() == 1) {
							trace(fromList, toList, true);
						}
					}
				});
	}
	
	public ApplicationAction getTraceRightToLeft() {
		return new ApplicationAction(ActionGroup.TRACERIGHTTOLEFT,
				areTreesSelected(),
				 new UIActionListener(controller.ui) {
					public void actionExecuted(ActionEvent ae) throws Exception {
						Vector fromList = controller.getTracetree().getSelectedUserObjects();
						Vector toList = controller.getTargettree().getSelectedUserObjects();
						if (fromList.size() == 1 || toList.size() == 1) {
							trace(fromList, toList, false);
						}
					}
				});
	}
	
	public ApplicationAction getUntraceTrees() {
		return new ApplicationAction(ActionGroup.TRACEEDITUNTRACE,
				areTreesSelected(),
				 new UIActionListener(controller.ui) {
					public void actionExecuted(ActionEvent ae) throws Exception {
						Vector fromList = controller.getTracetree().getSelectedUserObjects();
						Vector toList = controller.getTargettree().getSelectedUserObjects();
						if (fromList.size() == 1 || toList.size() == 1) {
							String msg = ReferenceServices.getDisplay(SystemMessageFramework.UNTRACE)
							+ " " + fromList.size() + " -> " + toList.size();
							if (JOptionPane.showConfirmDialog(frame, msg,ReferenceServices.getDisplay(SystemMessageFramework.TRACE), JOptionPane.OK_CANCEL_OPTION)
									== JOptionPane.OK_OPTION) {
								Enumeration e1 = fromList.elements();
								while (e1.hasMoreElements()) {
									ArtifactModel am = (ArtifactModel) e1.nextElement();
									Enumeration e2 = toList.elements();
									while (e2.hasMoreElements()) { 
										ArtifactModel to = (ArtifactModel) e2.nextElement();
										RequirementTreeModel rtm = new RequirementTreeModel();
										rtm.setParentId(am.getArtifactId());
										rtm.setParentArtifactRefId(am.getArtifactRefId());
										rtm.setChildId(to.getArtifactId());
										rtm.setChildArtifactRefId(to.getArtifactRefId());
										rtm.setRelationRefId(com.osrmt.modellibrary.reference.group.RelationGroup.DEPENDENT);
										RequirementServices.deleteRelationship(rtm);
									}
								}
							}
						}
					}
				});
	}
	


	/**
	 * Remove any error messages
	 * Reset the toolbar
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
		return 	new ControlState() {
			public boolean getEnabled() {
				return ApplicationObject.getApplicationProductRefId()>0;
			}
		};
	}


	
	private ControlState getAlwaysTrue() {
		return 	new ControlState() {
			public boolean getEnabled() {
				return true;
			}
		};
	}
	
	private ControlState areTreesSelected() {
		return new ControlState() {
			public boolean getEnabled() {
				if (controller.getTracetree().isNodeSelected()
						&& controller.getTargettree().isNodeSelected()) {
					Object o = controller.getTracetree().getSelectedObject();
					Object o2 = controller.getTargettree().getSelectedObject();
					if (o != null && o instanceof ArtifactModel
							&& o2 != null && o2 instanceof ArtifactModel) {
						if (controller.getTracetree().getSelectedUserObjects().size()==1
						|| controller.getTargettree().getSelectedUserObjects().size()==1) {
							return true;
						}
					}
				}
				return false;
			}
		};
	}

}
