package com.osrmt.appclient.changecontrol;
import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;
import com.osframework.modellibrary.common.*;
import com.osframework.modellibrary.reference.security.*;
import com.osframework.appclient.services.*;
import com.osframework.appclient.ui.common.ApplicationAction;
import com.osframework.appclient.ui.common.ApplicationActionList;
import com.osframework.appclient.ui.components.MultiColumnList;
import com.osframework.appclient.ui.components.UIPanelStatusBar;
import com.osframework.appclient.ui.controls.UIJPanel;
import com.osframework.appclient.ui.controls.UIMenuBar;
import com.osframework.appclient.ui.controls.UIToolBar;
import com.osframework.appclient.ui.listeners.*;
import com.osframework.appclient.ui.tree.*;
import com.osframework.appclient.ui.tree.UITreeModel;
import com.osframework.modellibrary.reference.common.*;
import com.osframework.modellibrary.reference.group.*;
import com.osframework.framework.logging.*;
import com.osframework.framework.utility.*;
import com.osrmt.www.services.*;
import javax.servlet.http.*;
import javax.swing.JPanel;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;

import org.apache.struts.*;
import org.apache.struts.action.*;

import com.osrmt.appclient.common.ApplicationObject;
import com.osrmt.appclient.reqmanager.RequirementManagerTools;
import com.osrmt.appclient.services.IssueServices;
import com.osrmt.appclient.services.RequirementServices;
import com.osrmt.modellibrary.issue.IssueCriteria;
import com.osrmt.modellibrary.issue.IssueList;
import com.osrmt.modellibrary.reference.group.ActionGroup;
import com.osrmt.modellibrary.reference.group.ApplicationGroup;
import com.osrmt.modellibrary.reference.group.FormTitleGroup;
import com.osrmt.modellibrary.reference.group.IssueTypeGroup;
import com.osrmt.modellibrary.reference.group.SystemMessageGroup;
import com.osrmt.modellibrary.reqmanager.*;

public class ChangeControlViewController {

	// user interface
	public ChangeControlView ui = null;
	// action definition
	private ChangeControlActions changeControlActions;
	// action list
	private ApplicationActionList applicationActionList;
	// change control item list
	private MultiColumnList tableList = new MultiColumnList();
	// menu/tool bar
	private UIToolBar toolBar = new UIToolBar();
	private UIMenuBar menuBar = new UIMenuBar();
	//
	private UIScrollTree cctree = new UIScrollTree();

	private UIPanelStatusBar listStatusPanel = new UIPanelStatusBar(0);

	/**
	 * Singleton
	 */
	public ChangeControlViewController() {
		ApplicationObject.setChangeControlViewController(this);
	}

	/**
	 * @param args
	 */
	public void start() {
		try {
			if (ui == null) {
				ui = new ChangeControlView();
				addControls();
				addListeners();
			}
			ui.setTitle(ReferenceServices.getDisplay(FormTitleGroup.CHANGECONTROL) + " " + 
					ApplicationObject.getRequirementManagerController().getTitle());
			changeControlActions.resetSystemState();
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
		changeControlActions = new ChangeControlActions(ui);
		// Create all the form actions
		applicationActionList = changeControlActions.getActions(this);
		// Create the menu bar
		menuBar = new ChangeControlTools().getMenuBar(applicationActionList);
		ui.setJMenuBar(menuBar);
		// Create the toolbar
		toolBar = new ChangeControlTools().getToolBar(applicationActionList);
		// Create the tree
		buildTree();
		// Add controls to the panels
		ui.getNorthPanel().add(toolBar);
		ui.getWestPanel().add(cctree, BorderLayout.CENTER);
		ui.getEastPanel().add(getEastPanel());
	}
	
	private UIJPanel getEastPanel() {
		UIJPanel eastPanel = new UIJPanel();
		UIJPanel eastNorthPanel = new UIJPanel();
		UIJPanel eastSouthPanel = new UIJPanel();
		eastSouthPanel.add(tableList);
		eastNorthPanel.add(listStatusPanel);
		eastPanel.add(eastSouthPanel, BorderLayout.CENTER);
		eastPanel.add(eastNorthPanel, BorderLayout.NORTH);
		return eastPanel;
	}
	


	public MultiColumnList getTableList() {
		return tableList;
	}

	public void setTableList(MultiColumnList tableList) {
		this.tableList = tableList;
	}
	
	/**
	 * The tree built once with a string root and display models
	 * as child nodes storing the application view id
	 * 
	 * @throws Exception
	 */
	public void buildTree() throws Exception {
		UITreeNode root = new UITreeNode();
		root.setUserObject(ReferenceServices.getDisplay(SystemMessageGroup.CUSTOMVIEWS));
		
		ApplicationSecurityList list = SecurityServices.getAppSecurity(ApplicationFramework.get(ApplicationGroup.CHANGECONTROLVIEW));
		Enumeration e1 = list.elements();
		while (e1.hasMoreElements()) {
			ApplicationSecurityModel asm = (ApplicationSecurityModel) e1.nextElement();
			ReferenceDisplay m = new ReferenceDisplay();
			m.setRefId(asm.getApplicationViewId());
			m.setDisplay(asm.getViewRefDisplay());
			UITreeNode child = new UITreeNode();
			child.setUserObject(m);
			root.add(child);
		}
		UITreeModel treeModel = new UITreeModel(root);
		cctree.display(treeModel, getTreeSelectionListener());
		
	}
	
	/**
	 * Get the on selection listener 
	 * @return
	 */
	private TreeSelectionListener getTreeSelectionListener() {
		TreeSelectionListener l = new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {
				try {
					TreePath path = cctree.getTree().getSelectionPath();
					if (cctree.getSelectedNode()!= null) {
						valueForPathChanged(path, (UITreeNode) cctree.getSelectedNode());
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
				refreshItemList(m.getRefId());			
			}
			changeControlActions.resetSystemState();
			
		} catch (Exception ex) {
			Debug.LogException(this, ex);
		}
	}
	
	/**
	 * Execute script to get issues
	 * TODO provide right click properties on tree to modify script or add a new view
	 * 
	 * @param applicationViewId
	 */
	public void refreshItemList(int applicationViewId) {
		ApplicationViewModel avm = SecurityServices.getApplicationView(applicationViewId);
		ControlScript cs = new ControlScript();
		cs.addReference(Application.getUser(), "user");
		IssueList issues = (IssueList) cs.execute(avm.getInitScript(), IssueList.class);
		try {
			ApplicationControlList acl1 = SecurityServices.getAppControlsByUser(ApplicationFramework.get(ApplicationGroup.COLUMNSCHANGECONTROLVIEW));
			issues.setColumnOrder(acl1);
		} catch (ResultColumnException rce) {
			Debug.LogException(this, rce);
		}
		setIssueList(issues);
	}

	public void setIssueList(IssueList issues) {
		this.tableList.setTableModel(issues,60);
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
		
		// Double click on list of artifacts opens artifact form
		if (applicationActionList.hasAction(ActionGroup.CHANGECONTROLEDIT)) {
			this.tableList.addMouseListener(new DoubleClickListener() {
				public void call(ActionEvent me) {
					ApplicationAction action = applicationActionList.getAction(ActionGroup.CHANGECONTROLEDIT);
					ActionEvent actionEvent = new ActionEvent(me.getSource(), 0, null);
					action.getActionListener().actionPerformed(actionEvent);
				}
			});
			this.tableList.addMouseListener(new MouseListener() {
				public void mouseClicked(MouseEvent e) {
					changeControlActions.resetSystemState();					
				}
				public void mouseEntered(MouseEvent e) {}
				public void mouseExited(MouseEvent e) {}
				public void mousePressed(MouseEvent e) {};
				public void mouseReleased(MouseEvent e) {}
			});
		}
	}


}

