package com.osrmt.appclient.reqmanager;

import javax.swing.event.*;
import javax.swing.tree.*;

import org.apache.commons.logging.Log;

import java.awt.*;
import java.awt.dnd.*;
import javax.swing.*;

import java.awt.event.*;
import java.awt.*;
import java.io.IOException;
import java.util.*;

import com.osframework.appclient.ui.tree.UITreeNode;
import com.jgoodies.looks.LookUtils;
import com.jgoodies.looks.Options;
import com.jgoodies.looks.plastic.Plastic3DLookAndFeel;
import com.osrmt.modellibrary.reqmanager.*;
import com.osrmt.appclient.artifact.form.ArtifactListFilterForm;
import com.osrmt.appclient.common.ApplicationObject;
import com.osrmt.appclient.system.*;
import com.osrmt.appclient.services.*;
import com.osframework.appclient.services.Application;
import com.osframework.appclient.services.ReferenceServices;
import com.osframework.appclient.services.SecurityServices;
import com.osframework.appclient.ui.common.*;
import com.osframework.appclient.ui.common.SwingWorker;
import com.osframework.appclient.ui.components.*;
import com.osframework.appclient.ui.controls.UIJPanel;
import com.osframework.appclient.ui.controls.UIMenuBar;
import com.osframework.appclient.ui.controls.UIProperties;
import com.osframework.appclient.ui.controls.UIToolBar;
import com.osframework.appclient.ui.listeners.DoubleClickListener;
import com.osframework.appclient.ui.listeners.WindowClosingListener;
import com.osframework.appclient.ui.tree.UIDragDropListener;
import com.osframework.appclient.ui.tree.UIScrollTree;
import com.osframework.appclient.ui.tree.UITreeModel;
import com.osframework.modellibrary.common.*;
import com.osframework.modellibrary.reference.common.*;
import com.osframework.modellibrary.reference.group.*;
import com.osframework.modellibrary.reference.security.*;
import com.osframework.modellibrary.system.RecordParameterControlList;
import com.osrmt.modellibrary.reference.group.*;
import com.osframework.datalibrary.common.Db;
import com.osframework.framework.logging.*;
import com.osframework.framework.utility.*;

/**
 * null
 */
public class RequirementManagerController extends RequirementManagerBaseController implements IParent {
 
	private UIScrollTree reqTree = new UIScrollTree(this);
	private MultiColumnList tableList = new MultiColumnList();
	private UIToolBar toolBar = new UIToolBar();
	private UIMenuBar menuBar = new UIMenuBar();
	private RequirementManagerActions actions = new RequirementManagerActions(ui);
	private ApplicationActionList applicationActionList;
	private ArtifactList currentArtifactList = new ArtifactList();
	private UIPanelStatusBar listStatusPanel = new UIPanelStatusBar(0);
	private Hashtable filterMap = new Hashtable();
	private JEditorPane descriptionbox = new JEditorPane();
	/**
	 * Loads the artifact tree
	 */
	private ArtifactTreeLoader treeLoader = null;
	
	public RequirementManagerController() {
		ApplicationObject.setRequirementManagerController(this);
		
	}
	
	/**
	 * Application start
	 * @param args
	 */
	public static void main (String[] args) {
		try {
			  SwingUtilities.invokeLater( new Runnable(){

				public void run() {
					UISplash.getInstance().start();
					UISplash.getInstance().setVisible(true);
				}
				  
			  }); 
			
			GUI.setLookAndFeel();
			RequirementManagerController rmc = new RequirementManagerController();
			rmc.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Start the application
	 *
	 */
	public void start() {
		LoginController loginController = new LoginController();
		loginController.start(this);
	}

	public void setValue(Object o1, Object o2) {
		
	}
	
	/**
	 * Execute after login.
	 */
	public void setValue(Object o) {
		if (o == null) {
			System.exit(0);
		} else {
			Application.setUser((ApplicationUserModel) o);
			initializeApplication();
		}
	}
	
	
	public void initializeApplication () {
		try {
			// intialize the base controls
			  
			//ApplicationControlList controls = SecurityServices.getAppControls(ApplicationGroup.REQUIREMENTMANAGER);
			super.initialize(new ApplicationControlList(), 0);
			// add custom controls and listeners
			
			addControls();
			addListeners();
			initForm();
			actions.resetSystemState();
			// warm up artifact form
			SwingWorker sw = new SwingWorker(this.ui) {

				@Override
				protected void doNonUILogic() throws Exception {
					ApplicationControlList controls = SecurityServices.getAppControlsByUser(ArtifactGroup.FEATURE, 
							ApplicationFramework.get(ApplicationGroup.ARTIFACTFORM));
					ApplicationControlList controls2 = SecurityServices.getAppControlsByUser(ArtifactGroup.REQUIREMENT, 
							ApplicationFramework.get(ApplicationGroup.ARTIFACTFORM));
				}

				@Override
				protected void doUIUpdateLogic() throws Exception {
					
				}
				
			};
			sw.start();

		} catch (Exception ex) {
			Debug.LogException(this, ex);			
			ui.setVisible(true);
		}
	}
	
	private void initForm() {
		// show the form
		//TODO Size and location should be in properties
		ui.setLocation(UIProperties.getPoint50_50());
		ui.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
		ui.setSize(UIProperties.getWINDOW_SIZE_900_675());
		ui.setTitle("OSRMT - " + Application.getUser().getUserLogin() 
				+ "@" + SecurityServices.getEnvironment());
		ui.setIconImage(GUI.getImage("osrmt.gif",this));
		ui.setVisible(true);
	}
	
	/**
	 * Add custom controls for this application form
	 * @throws Exception
	 */
	private void addControls() throws Exception {
		
		// Create all the form actions
		applicationActionList = actions.getActions(this);
		// Create the menu bar
		menuBar = new RequirementManagerTools().getMenuBar(applicationActionList);
		ui.setJMenuBar(menuBar);
		// Create the toolbar
		toolBar = new RequirementManagerTools().getToolBar(applicationActionList);
		// Create the tree
		buildTree();
		// Add controls to the panels
		ui.getNorthPanel().add(toolBar);
		ui.getWestPanel().add(reqTree, BorderLayout.CENTER);
		ui.getEastPanel().add(getEastPanel());
		descriptionbox.setOpaque(false);
		descriptionbox.setFocusable(false);
	}
	
	private JSplitPane getEastPanel() {
		JSplitPane eastPane = new JSplitPane();
		eastPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		eastPane.setDividerLocation(500);
		UIJPanel eastNorthPanel = new UIJPanel(new BorderLayout());
		UIJPanel eastSouthPanel = new UIJPanel(new BorderLayout());
		//eastSouthPanel.add(tableList);
		eastNorthPanel.add(tableList, BorderLayout.CENTER);
		
		eastSouthPanel.add(new JScrollPane(descriptionbox), BorderLayout.CENTER);
		eastSouthPanel.setPreferredSize(new java.awt.Dimension(100,100));
		eastPane.setLeftComponent(eastNorthPanel);
		eastPane.setRightComponent(eastSouthPanel);
		return eastPane;
	}

	
	/**
	 * Add listeners to the custom controls
	 * 
	 * @throws Exception
	 */
	private void addListeners() throws Exception {
		
		// Double click on list of artifacts opens artifact form
		if (applicationActionList.hasAction(ActionGroup.REQMGREDITARTIFACT)) {
			this.tableList.addMouseListener(new DoubleClickListener() {
				public void call(ActionEvent me) {
					ApplicationAction action = applicationActionList.getAction(ActionGroup.REQMGREDITARTIFACT);
					ActionEvent actionEvent = new ActionEvent(me.getSource(), 0, null);
					action.getActionListener().actionPerformed(actionEvent);
				}
			});
			this.tableList.addMouseListener(new MouseListener() {
				public void mouseClicked(MouseEvent e) {
					actions.resetSystemState();					
				}
				public void mouseEntered(MouseEvent e) {}
				public void mouseExited(MouseEvent e) {}
				public void mousePressed(MouseEvent e) {};
				public void mouseReleased(MouseEvent e) {}
			});
		}
		this.tableList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				descriptionbox.setText("");
				if (tableList.getSelectedRow() != null && tableList.getSelectedRow() instanceof ArtifactModel) {
					ArtifactModel am = (ArtifactModel) tableList.getSelectedRow();
					ArtifactModel m = RequirementServices.getArtifact(am.getArtifactId());
					descriptionbox.setText(m.getDescription());
					descriptionbox.setCaretPosition(0);
				}
				tableList.grabFocus();
			}
		});
		GUI.registerDebug(ui, ui.getSouthPanel());
		ui.addWindowListener(new WindowClosingListener() {
			public void windowClosed(WindowEvent e) {
				GUI.unregisterDebug(ui);
			}
		});
		
	}
	
	/**
	 * The tree is rebuilt several times by this method
	 * 
	 * @throws Exception
	 */
	public void buildTree() throws Exception {

		try {
			ApplicationObject.getRequirementManagerController().ui.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			reqTree.getTree().removeAll();
			reqTree.setRenderer(ArtifactTreeRenderer.getInstance());
			int productRefId = 0;
			ApplicationObject ao = (ApplicationObject) Application.getObject();
			if (ao != null) {
				productRefId = ao.getProductRefId();
			}
			ui.setTitle(ReferenceServices.getDisplay(productRefId) + " - " + Application.getUser().getUserLogin() 
					+ "@" + SecurityServices.getEnvironment());
			UITreeModel newModel = RequirementServices.getTopRequirementTreeModel(productRefId);
			reqTree.display(newModel, getTreeSelectionListener());
			treeLoader = new ArtifactTreeLoader(productRefId, newModel, reqTree.getTree());
			
			addTreeListeners();
		} catch (Exception ex) {
			ex.printStackTrace();
		
		} finally {
			ApplicationObject.getRequirementManagerController().ui.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
		
	}
	
	private void addTreeListeners() {
		UIDragDropListener ddl = new UIDragDropListener(reqTree) {
			public boolean dropTarget(UITreeNode newparent, UITreeNode[] nodesDragged) {
				boolean returnVal = false;
				try {
				      if (nodesDragged.length > 0 && nodesDragged[0].getUserObject() instanceof ArtifactModel && newparent.getUserObject() instanceof ArtifactModel) {
				    	  	ArtifactModel first = (ArtifactModel) nodesDragged[0].getUserObject();
							ArtifactModel nparent = (ArtifactModel) newparent.getUserObject();
			    	  		String msg = null;
				    	  	if (first.getArtifactRefId() == ((ArtifactModel) newparent.getUserObject()).getArtifactRefId()) {
				    	  		String artifacts = nodesDragged.length + " " + ReferenceServices.getDisplay(SystemMessageFramework.ARTIFACTS);
				    	  		if (nodesDragged.length == 1) {
				    	  			artifacts = first.getArtifactName();
				    	  		}
				    	  		msg = ReferenceServices.getDisplay(SystemMessageFramework.MOVE) + " " +  artifacts  + " " 
				    	  				+ ReferenceServices.getDisplay(SystemMessageFramework.UNDER)  + " " +  nparent.getArtifactName();
				    	  	} else {
				    	  		String artifacts = nodesDragged.length + " " + ReferenceServices.getDisplay(SystemMessageFramework.ARTIFACTS);
				    	  		if (nodesDragged.length == 1) {
				    	  			artifacts = first.getArtifactName();
				    	  		}
				    	  		
				    	  		msg = ReferenceServices.getDisplay(SystemMessageFramework.CREATEDEPENDENCYFROM) + " " + artifacts + " " + 
				    	  			ReferenceServices.getDisplay(SystemMessageFramework.ON) + " " + nparent.getArtifactName();
				    	  		
				    	  	}
							if (JOptionPane.YES_OPTION != JOptionPane.showConfirmDialog(ui, msg)) {
								return false;
							} else {
								for (int i=0; i< nodesDragged.length; i++) {
									try {
										UITreeNode nodeDragged = nodesDragged[i];
										UITreeNode oldParent = (UITreeNode) nodeDragged.getParent();
										int index = oldParent.getIndex(nodeDragged);
										ArtifactModel oparent = (ArtifactModel) ((UITreeNode) oldParent).getUserObject();
							
										if (oparent != null && nparent != null && oparent != nparent) {
											if (oparent.getArtifactRefId() == nparent.getArtifactRefId()) {
												ArtifactModel child = (ArtifactModel) nodeDragged.getUserObject();
												if (child.getArtifactId() != nparent.getArtifactId()) {
													if (RequirementServices.MoveArtifact(oparent.getArtifactId(), oparent.getArtifactRefId(),nparent.getArtifactId(), nparent.getArtifactRefId(), child.getArtifactId(), child.getArtifactRefId())) {
														//nodeDragged.removeFromParent();
														newparent.add(nodeDragged);
														reqTree.getTreeModel().nodesWereInserted(newparent, new int[] {newparent.getChildCount()-1});
														reqTree.getTreeModel().nodesWereRemoved(oldParent, new int[] {index}, new Object[] {nodeDragged});
														returnVal = true;
													} else {
														returnVal = false;
													}
												} else {
													//drag onto itself
													returnVal = false;
												}
											// different type - create relationship
											} else {
												ArtifactModel child = (ArtifactModel) nodeDragged.getUserObject();
												RequirementTreeModel rtm = new RequirementTreeModel();
												rtm.setParentId(child.getArtifactId());
												rtm.setParentArtifactRefId(child.getArtifactRefId());
												rtm.setChildId(nparent.getArtifactId());
												rtm.setChildArtifactRefId(nparent.getArtifactRefId());
												rtm.setRelationRefId(RelationGroup.DEPENDENT);
												RequirementServices.UpdateRelationship(rtm);
												Debug.displayGUIMessage(ReferenceServices.getMsg(SystemMessageFramework.RELATIONSHIPTRACED));
												returnVal = false;
											}
										} else {
											returnVal = false;
										}
									} catch (Exception ex) {
										Debug.LogException(this, ex);
										returnVal = false;
									}
								}
							}
				      } else {
				    	  returnVal = false;
				      }
				} catch (Exception ex) {
					Debug.LogException(this, ex);
					returnVal = false;
				}
				return returnVal;
			}
		};
		try {
			reqTree.getTree().setDragEnabled(true);
			DropTarget dt = new DropTarget(reqTree.getTree(), ddl);
			reqTree.getTree().setDropTarget(dt);
		} catch (Exception ex) {
			Debug.LogException(this, ex);
		}
		
		this.reqTree.getTree().addMouseListener(new DoubleClickListener() {
			public void call(ActionEvent me) {
				if (reqTree.getSelectedNode() != null && reqTree.getSelectedNode().isLeaf()
						&& reqTree.getSelectedNode().getLevel() != 1) {
					Object o = reqTree.getSelectedObject();
					if (o instanceof ArtifactModel) {
						ApplicationAction action = applicationActionList.getAction(ActionGroup.REQMGREDITARTIFACT);
						ActionEvent actionEvent = new ActionEvent(me.getSource(), 0, null);
						action.getActionListener().actionPerformed(actionEvent);
					}
				}
			}
		});
		reqTree.getTree().addTreeWillExpandListener(new TreeWillExpandListener() {
			public void treeWillCollapse(TreeExpansionEvent event) throws ExpandVetoException {
				if (event.getPath().getLastPathComponent() instanceof UITreeNode) {
					UITreeNode node = (UITreeNode) event.getPath().getLastPathComponent();
					if (!(node.getUserObject() instanceof ArtifactModel)) {
						// root node stop the collapse
						throw new ExpandVetoException(event);
					}
				}
			}
			public void treeWillExpand(TreeExpansionEvent event) throws ExpandVetoException {}
		});
		this.reqTree.getTree().addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (SwingUtilities.isRightMouseButton(e)) {
					try {
						UITreeNode node = ((UITreeNode) reqTree.getTree().getPathForLocation(e.getX(), e.getY()).getLastPathComponent());
						rightClick(node,  reqTree.getTree().getPathForLocation(e.getX(), e.getY()), (ArtifactModel) node.getUserObject(), e.getX(), e.getY());
					} catch (Exception ex) {
						// ignore click errors or null errors
					}
				}
			}
		});
	}
	
	private TreeSelectionListener getTreeSelectionListener() {
		TreeSelectionListener l = new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {
				try {
					TreePath path = reqTree.getTree().getSelectionPath();
					if (reqTree.getSelectedNode()!= null) {
						valueForPathChanged(path, (UITreeNode) reqTree.getSelectedNode());
					}
				} catch (Exception ex) {
					Debug.LogException(this, ex);
				}
			}
		};
		
		return l;
	}
	
	public void setArtifactList(ArtifactList artifactList, int artifactRefId) {
		try {

			RecordParameterControlList filterParameters = getFilterParameters(artifactRefId);
			if (filterParameters != null && (filterParameters.size() != 0 || filterParameters.hasValues())) {
				artifactList = ArtifactListFilterForm.filter(artifactList, filterParameters);
				listStatusPanel.setMessage(ReferenceServices.getDisplay(artifactRefId) + " " 
						+ ReferenceServices.getMsg(SystemMessageFramework.ARTIFACTLISTISFILTERED)
						+ " (" + artifactList.size() + ")");
			} else {
				listStatusPanel.setMessage("");
			}
			currentArtifactList = artifactList;
			
			ApplicationControlList acl1 = SecurityServices.getAppControlsByUser(artifactRefId, ApplicationFramework.get(ApplicationGroup.ARTIFACTSEARCHRESULTS));
			artifactList.setColumnOrder(acl1);
			ApplicationObject.trimDescription(artifactList);
			this.tableList.setTableModel(artifactList, 60);
			if (tableList.getJtable().getRowCount() > 0) {
				tableList.getJtable().setRowSelectionInterval(0,0);
			}

		} catch (Exception ex) {
			Debug.LogException(this,ex);
		}
	}
	/**
	 * Dynamically add child nodes to the tree
	 */
	private void valueForPathChanged(TreePath path, UITreeNode node) throws ResultColumnException {
		try {
			if (node.getUserObject() instanceof ArtifactModel) {
				ArtifactModel m = (ArtifactModel) node.getUserObject();
				updateArtifactList(m);			
			}
			actions.resetSystemState();
			
		} catch (Exception ex) {
			Debug.LogException(this, ex);
		}
	}
	
	
	private boolean getImmediateChildren() throws Exception {
		ApplicationSettingList asl = SecurityServices.getSetting(ApplicationFramework.get(0),
				ApplicationSettingFramework.ARTIFACTLISTIMMEDIATECHILDRENFORSELECTED);
		if (asl.size() > 0) {
			return asl.getFirst().getValueInt() == 1;
		} else {
			return false;
		}
	}
	
	public void updateArtifactList(final ArtifactModel m) {
		SwingWorker sw = new SwingWorker(tableList.getRootPane()) {

			@Override
			protected void doNonUILogic() throws Exception {
				//TODO creating new artifact list misses the recordextensioncolumnlist
				ArtifactList artifactList = new ArtifactList();				
				if (getImmediateChildren()) {
					int productRefId = ApplicationObject.getApplicationProductRefId();
					if (m.getArtifactId() == 0) {
						artifactList = RequirementServices.getTopLevelChildren(productRefId, m.getArtifactRefId());
					} else {
						artifactList.add(RequirementServices.getArtifact(m.getArtifactId()));
						artifactList.addAll(RequirementServices.getImmediateSameChildren(productRefId, m.getArtifactRefId(), m.getArtifactId()));
					}
				} else {
					if (m.getArtifactId() == 0) {
						artifactList = RequirementServices.getAllItems(m.getArtifactRefId());
					} else {
						artifactList = RequirementServices.getAllSameChildren(m.getArtifactRefId(), m.getArtifactId());
					}
				}
				setUserObject(artifactList);
			}

			@Override
			protected void doUIUpdateLogic() throws Exception {
				setArtifactList(((ArtifactList)getUserObject()), m.getArtifactRefId());
			}
		};
		sw.start();
	}
	
	public UIScrollTree getReqTree() {
		return reqTree;
	}

	public void setReqTree(UIScrollTree reqTree) {
		this.reqTree = reqTree;
	}

	public RequirementManagerActions getActions() {
		return actions;
	}
	
	public MultiColumnList getTableList() {
		return tableList;
	}

	public void setTableList(MultiColumnList tableList) {
		this.tableList = tableList;
	}

	public void setActions(RequirementManagerActions actions) {
		this.actions = actions;
	}

	public UIToolBar getToolBar() {
		return toolBar;
	}

	public void setToolBar(UIToolBar toolBar) {
		this.toolBar = toolBar;
	}

	public UIMenuBar getMenuBar() {
		return menuBar;
	}

	public void setMenuBar(UIMenuBar menuBar) {
		this.menuBar = menuBar;
	}

	public RecordParameterControlList getFilterParameters(int artifactRefId) {
		if (filterMap.containsKey(getKey(artifactRefId))) {
			return (RecordParameterControlList) filterMap.get(getKey(artifactRefId));
		} else {
			return new RecordParameterControlList();
		}
	}

	public void setFilterParameters(RecordParameterControlList filterParameters, int artifactRefId) {
		if (filterMap.containsKey(getKey(artifactRefId))) {
			filterMap.remove(getKey(artifactRefId));
		}
		filterMap.put(getKey(artifactRefId), filterParameters);
	}
	
	private String getKey(int i) {
		return "" + i;
	}

	public ArtifactList getCurrentArtifactList() {
		return currentArtifactList;
	}
	
	public UITreeNode findNodeInTreeAnywhere(int artifactId, UITreeNode start) {
		UITreeNode node = findNodeInTree(artifactId, start);
		if (node == null) {
			return findNodeInTree(artifactId, (UITreeNode) this.getReqTree().getTreeModel().getRoot());
		} else {
			return node;
		}
	}

	/**
	 * Finds an artifact in the tree at or under the start node
	 * 
	 * @param artifactId
	 * @param start
	 * @return
	 */
	public UITreeNode findNodeInTree(int artifactId, UITreeNode start) {
		if (start != null) {
			if (idMatch(artifactId, start)) {
				return start;
			} else {
				for (int i=0; i< start.getChildCount(); i++) {
					UITreeNode subnode = findNodeInTree(artifactId, (UITreeNode) start.getChildAt(i));
					if (subnode != null) {
						return subnode;
					}
				}
			}
		}
		return null;
	}
	
	public UITreeNode findRootNode(int artifactRefId) {
		UITreeNode rootNode = (UITreeNode) this.reqTree.getTree().getModel().getRoot();
		for (int i=0; i< rootNode.getChildCount(); i++) {
			UITreeNode subnode = (UITreeNode) rootNode.getChildAt(i);
			if (subnode.getUserObject() instanceof ArtifactModel) {
				ArtifactModel am = (ArtifactModel) subnode.getUserObject();
				if (am.getArtifactRefId() == artifactRefId) {
					return subnode;
				}
			}
		}
		return null;
	}
	
	private boolean idMatch(int id, UITreeNode node) {
		if (node != null && node.getUserObject() != null && node.getUserObject() instanceof ArtifactModel) {
			return ((ArtifactModel) node.getUserObject()).getArtifactId() == id;
		} else {
			return false;
		}
	}
	
	public String getTitle() {
		return ui.getTitle();
	}

	public ApplicationActionList getApplicationActionList() {
		return applicationActionList;
	}

	public void setApplicationActionList(ApplicationActionList applicationActionList) {
		this.applicationActionList = applicationActionList;
	}
	
	/**
	 * Right click on a node
	 * 
	 * @param node
	 * @param model
	 */
	private void rightClick(UITreeNode node, TreePath path, ArtifactModel model, int x, int y) {
		this.reqTree.getTree().setSelectionPath(path);
		actions.showArtifactPopupMenu(x, y, model, node, applicationActionList);
	}

	public ArtifactTreeLoader getTreeLoader() {
		return treeLoader;
	}
	
	
}