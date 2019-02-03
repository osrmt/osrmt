package com.osrmt.apps.swingApp.traceability;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;

import com.osframework.appclient.services.ReferenceServices;
import com.osframework.appclient.ui.common.ApplicationActionList;
import com.osframework.appclient.ui.controls.UIJPanel;
import com.osframework.appclient.ui.controls.UIMenuBar;
import com.osframework.appclient.ui.controls.UIToolBar;
import com.osframework.appclient.ui.listeners.DoubleClickListener;
import com.osframework.appclient.ui.tree.UIScrollTree;
import com.osframework.appclient.ui.tree.UITreeNode;
import com.osframework.framework.logging.Debug;
import com.osframework.modellibrary.common.ResultColumnException;
import com.osframework.modellibrary.reference.common.ReferenceDisplay;
import com.osrmt.apps.swingApp.common.ApplicationObject;
import com.osrmt.apps.swingApp.reqmanager.ArtifactFormController;
import com.osrmt.apps.swingApp.reqmanager.ArtifactTreeRenderer;
import com.osrmt.apps.swingApp.services.RequirementServices;
import com.osrmt.modellibrary.reference.group.ArtifactGroup;
import com.osrmt.modellibrary.reference.group.FormTitleGroup;
import com.osrmt.modellibrary.reference.group.TraceTreeGroup;
import com.osrmt.modellibrary.reqmanager.ArtifactList;
import com.osrmt.modellibrary.reqmanager.ArtifactModel;
import com.osrmt.modellibrary.reqmanager.TraceTreeCriteria;
public class TraceabilityViewController {

	// user interface
	public TraceView ui = null;
	private UIToolBar toolBar = new UIToolBar();
	private UIMenuBar menuBar = new UIMenuBar();
	private TraceActions traceActions;
	private JEditorPane descriptionbox = new JEditorPane(); 
	// action list
	private ApplicationActionList applicationActionList;

	private UIScrollTree tracetree = new UIScrollTree();
	private UIScrollTree targettree = new UIScrollTree();
	private ArtifactModel branch = null;

	private TraceTreeCriteria traceCriteria = new TraceTreeCriteria();

	/**
	 * Singleton
	 */
	public TraceabilityViewController() {
		ApplicationObject.setTraceViewController(this);
	}

	/**
	 * @param args
	 */
	public void start(ArtifactModel branch) {
		try {
			this.branch = branch;
			if (ui == null) {
				tracetree.setRenderer(ArtifactTreeRenderer.getInstance());
				targettree.setRenderer(ArtifactTreeRenderer.getInstance());
				ui = new TraceView();
				addControls();
				buildTargetTree();
				addDoubleClickTree();
				addListeners();
			}
			// Create the tree
			buildTree();
			ui.setTitle(ReferenceServices.getDisplay(FormTitleGroup.TRACEABILITYMATRIX) + " " + 
					ApplicationObject.getRequirementManagerController().getTitle());
			traceActions.resetSystemState();
		} catch (Exception ex) {
			Debug.LogException(this, ex);
		} finally {
			ui.setVisible(true);
		}

	}

	/**
	 * Add custom controls for this view
	 * 
	 * @throws Exception
	 */
	private void addControls() throws Exception {
		// actions
		traceActions = new TraceActions(ui);
		// Create all the form actions
		applicationActionList = traceActions.getActions(this);
		// Create the menu bar
		menuBar = new TraceTools().getMenuBar(applicationActionList);
		ui.setJMenuBar(menuBar);
		// Create the toolbar
		toolBar = new TraceTools().getToolBar(applicationActionList);
		// Add controls to the panels
		ui.getNorthPanel().add(toolBar);
		ui.getWestPanel().add(tracetree, BorderLayout.CENTER);
		ui.getEastPanel().add(getEastPanel());
		// default
		traceCriteria.setAllowCircularDependencies(false);
		traceCriteria.setTraceFromArtifactRefId(ArtifactGroup.FEATURE);
		traceCriteria.setMaxTreeDepth(5);
		traceCriteria.setStartTraceWith(TraceTreeGroup.get(TraceTreeGroup.STARTALL));
		descriptionbox.setOpaque(false);
		descriptionbox.setFocusable(false);
	}
	
	private JSplitPane getEastPanel() {
		JSplitPane eastPane = new JSplitPane();
		eastPane.setDividerLocation(300);
		eastPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		UIJPanel eastNorthPanel = new UIJPanel(new BorderLayout());
		UIJPanel eastSouthPanel = new UIJPanel(new BorderLayout());
		//eastSouthPanel.add(tableList);
		eastNorthPanel.add(targettree, BorderLayout.CENTER);
		
		eastSouthPanel.add(new JScrollPane(descriptionbox), BorderLayout.CENTER);
		eastSouthPanel.setPreferredSize(new java.awt.Dimension(100,100));
		eastPane.setLeftComponent(eastNorthPanel);
		eastPane.setRightComponent(eastSouthPanel);
		return eastPane;
	}


	/**
	 * The tree built once with a string root and display models
	 * as child nodes storing the application view id
	 * 
	 * @throws Exception
	 */
	public void buildTree() throws Exception {
		tracetree.display(RequirementServices.getTraceTreeModel(traceCriteria, branch), 
				getTreeSelectionListener());
		this.tracetree.getTree().addMouseListener(new DoubleClickListener() {
			public void call(ActionEvent me) {
				if (tracetree.getSelectedNode() != null && tracetree.getSelectedNode().isLeaf()) {
					Object o = tracetree.getSelectedObject();
					if (o instanceof ArtifactModel) {
						ArtifactModel m = (ArtifactModel) o;
						ArtifactFormController afc = new ArtifactFormController(ui);
						ArtifactModel am = RequirementServices.getArtifact(m.getArtifactId());
						afc.setScrollList(0,new ArtifactList());
						afc.start(am.getArtifactRefId(), am, m, null);
					}
				}
			}
		});

	}
	
	/**
	 * The tree built once with a string root and display models
	 * as child nodes storing the application view id
	 * 
	 * @throws Exception
	 */
	public void buildTargetTree() throws Exception {
		targettree.display(ApplicationObject.getRequirementManagerController().getReqTree().getTreeModel(), 
				getTargetTreeSelectionListener());

	}
	
	/**
	 * Get the on selection listener 
	 * @return
	 */
	private TreeSelectionListener getTreeSelectionListener() {
		TreeSelectionListener l = new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {
				try {
					TreePath path = tracetree.getTree().getSelectionPath();
					if (tracetree.getSelectedNode()!= null) {
						valueForPathChanged(path, (UITreeNode) tracetree.getSelectedNode());
					}
				} catch (Exception ex) {
					Debug.LogException(this, ex);
				}
			}
		};
		
		return l;
	}
	
	/**
	 * Refresh view 
	 */
	private void valueForPathChanged(TreePath path, UITreeNode node) throws ResultColumnException {
		try {
			if (node.getUserObject() instanceof ReferenceDisplay) {
				ReferenceDisplay m = (ReferenceDisplay) node.getUserObject();
				//refreshItemList(m.getRefId());			
			}
			traceActions.resetSystemState();
			
		} catch (Exception ex) {
			Debug.LogException(this, ex);
		}
	}
	
	private void addDoubleClickTree() {
		this.targettree.getTree().addMouseListener(new DoubleClickListener() {
			public void call(ActionEvent me) {
				if (targettree.getSelectedNode() != null && targettree.getSelectedNode().isLeaf()) {
					Object o = targettree.getSelectedObject();
					if (o instanceof ArtifactModel) {
						ArtifactModel m = (ArtifactModel) o;
						ArtifactFormController afc = new ArtifactFormController(ui);
						ArtifactModel am = RequirementServices.getArtifact(m.getArtifactId());
						afc.setScrollList(0,new ArtifactList());
						afc.start(am.getArtifactRefId(), am, m, null);
					}
				}
			}
		});

	}
	
	public UIMenuBar getMenuBar() {
		return menuBar;
	}

	public void setMenuBar(UIMenuBar menuBar) {
		this.menuBar = menuBar;
	}

	public UIToolBar getToolBar() {
		return toolBar;
	}

	public void setToolBar(UIToolBar toolBar) {
		this.toolBar = toolBar;
	}

	
	private void addListeners() {
		
	}

	public TraceTreeCriteria getTraceCriteria() {
		return traceCriteria;
	}

	public void setTraceCriteria(TraceTreeCriteria traceCriteria) {
		this.traceCriteria = traceCriteria;
	}

	/**
	 * Get the on selection listener 
	 * @return
	 */
	private TreeSelectionListener getTargetTreeSelectionListener() {
		TreeSelectionListener l = new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {
				try {
					TreePath path = targettree.getTree().getSelectionPath();
					if (targettree.getSelectedNode()!= null) {
						targetValueForPathChanged(path, (UITreeNode) targettree.getSelectedNode());
					} else {
						descriptionbox.setText("");
					}
				} catch (Exception ex) {
					Debug.LogException(this, ex);
				}
			}
		};
		
		return l;
	}
	
	/**
	 * Refresh view 
	 */
	private void targetValueForPathChanged(TreePath path, UITreeNode node) throws ResultColumnException {
		try {
			if (node.getUserObject() instanceof ArtifactModel) {
				ArtifactModel am = RequirementServices.getArtifact(((ArtifactModel)node.getUserObject()).getArtifactId());
				descriptionbox.setText(am.getDescription());
				descriptionbox.setCaretPosition(0);
			} else {
				descriptionbox.setText("");
			}
			traceActions.resetSystemState();
		} catch (Exception ex) {
			Debug.LogException(this, ex);
		}
	}

	public UIScrollTree getTargettree() {
		return targettree;
	}

	public void setTargettree(UIScrollTree targettree) {
		this.targettree = targettree;
	}

	public UIScrollTree getTracetree() {
		return tracetree;
	}

	public void setTracetree(UIScrollTree tracetree) {
		this.tracetree = tracetree;
	}
	
	
}

