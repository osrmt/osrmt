package com.osrmt.appclient.reqmanager;

import javax.swing.event.*;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.*;

import java.awt.event.*;
import java.awt.*;
import java.util.*;

import com.osrmt.modellibrary.reqmanager.*;
import com.osrmt.appclient.common.ApplicationObject;
import com.osrmt.appclient.system.*;
import com.osrmt.appclient.services.*;
import com.osframework.framework.logging.*;
import com.osframework.appclient.ui.tree.*;
import com.osframework.framework.utility.TimedAction;
import com.osframework.appclient.services.ReferenceServices;
import com.osframework.appclient.services.SecurityServices;
import com.osframework.appclient.ui.common.*;
import com.osframework.appclient.ui.common.SwingWorker;
import com.osframework.appclient.ui.components.*;
import com.osframework.appclient.ui.controls.UIProperties;
import com.osframework.modellibrary.common.*;
import com.osrmt.modellibrary.reference.group.*;
import com.osframework.modellibrary.reference.common.ReferenceList;
import com.osframework.modellibrary.reference.group.ApplicationFramework;
import com.osframework.modellibrary.reference.group.FormButtonTextFramework;
import com.osframework.modellibrary.reference.group.SystemMessageFramework;
import com.osframework.modellibrary.reference.security.*;


/**
null
*/
public class ArtifactFormController extends UIStandardDialog {
	
	private static final long serialVersionUID = 1L;
	private UITreeNode ancestorNode = null;
	private ArtifactFormController self = this;
	private ArtifactModel parentArtifact = null;
	private ArtifactModel cachedFromList = null;
	private ApplicationControlList controls = null;
	private ArtifactModel artifact = new ArtifactModel();
	private JFrame frame;
	private ControlPanel controlPanel;
	final private ArrayList scrollList = new ArrayList();
	private ButtonNextPrevious buttonNextPrevious;
	private static FormPref formPref = null;
	
	//private int appTypeRefId;
	
	public ArtifactFormController(JFrame frame) {
		super(frame);
		this.frame = frame;
		initPreviousNext();
		init();
	}
	
	public void start(int appTypeRefId) {
		reset();
		artifact.setArtifactRefId(appTypeRefId);
		initializeApplication();
	}

	public void start(int appTypeRefId, ArtifactModel m, ArtifactModel cachedFromList, UITreeNode ancestorNode) {
		reset();
		this.cachedFromList = cachedFromList;
		this.ancestorNode = ancestorNode;
		artifact = m;
		initializeApplication();
	}

	/**
	 * New artifact under the parent
	 * 
	 * @param appTypeRefId
	 * @param parentArtifact
	 * @param parentNode
	 */
	public void startChild(int appTypeRefId, ArtifactModel parentArtifact, UITreeNode ancestorNode) {
			
		reset();
		this.parentArtifact = parentArtifact;
		this.ancestorNode = ancestorNode;
		artifact.setArtifactRefId(appTypeRefId);
		initializeApplication();
	}
	
	private void reset() {
		parentArtifact = null;
		cachedFromList = null;
		controls = null;
		artifact = new ArtifactModel();
		ancestorNode = null;
		controlPanel = null;
	}
	
	private void updateTree(final boolean newlyAdded, final ArtifactModel updated, final UITreeNode selectedNode, final int newParentId) {
		if (newlyAdded) {
			UITreeNode nodeToUpdate = ApplicationObject.getRequirementManagerController().findNodeInTree(newParentId, selectedNode);
			if (nodeToUpdate != null) {
				ArtifactModel am = new ArtifactModel();
				am.updateWith(updated);
				ArtifactModel newParent = (ArtifactModel) nodeToUpdate.getUserObject();
				if (am.getArtifactRefId() == newParent.getArtifactRefId()) {
					nodeToUpdate.add(new UITreeNode(am));
					ApplicationObject.getRequirementTree().getTreeModel().nodeStructureChanged(nodeToUpdate);
				} else {
					UITreeNode node2ToUpdate = ApplicationObject.getRequirementManagerController().findRootNode(am.getArtifactRefId());
					if (node2ToUpdate != null) {
						int[] indicies  = new int[] { node2ToUpdate.getChildCount() };
						node2ToUpdate.add(new UITreeNode(am));
						ApplicationObject.getRequirementTree().getTreeModel().nodesWereInserted(node2ToUpdate, indicies);
					}					
				}
			}
		} else {
			UITreeNode nodeToUpdate = ApplicationObject.getRequirementManagerController().findNodeInTreeAnywhere(updated.getArtifactId(), selectedNode);
			if (nodeToUpdate != null) {
				ArtifactModel modelFound = (ArtifactModel) nodeToUpdate.getUserObject();
				if (modelFound.getArtifactId() == updated.getArtifactId()) {
					modelFound.updateWith(updated);
					ApplicationObject.getRequirementTree().getTreeModel().nodeChanged(nodeToUpdate);
				}
			}
		}
	}
	

	private JButton getCloseButton() {
		return getButtonPanel().getCmdButton0();
	}
		
	private JButton getApplyButton() {
		return getButtonPanel().getCmdButton1();
	}
		
	private JButton getNextButton() {
		return getButtonPanel().getCmdButton2();
	}
		
	private JButton getBackButton() {
		return getButtonPanel().getCmdButton3();
	}
		
	/**
	 * @param args
	 */
	public void initializeApplication () {
		ISApplicationMediator.getInstance().clearAll();
		getApplyButton().setEnabled(false);
		getCloseButton().setText(ReferenceServices.getDisplay(FormButtonTextGroup.CLOSE));
		controls = SecurityServices.getAppControlsByUser(artifact.getArtifactRefId(), 
				ApplicationFramework.get(ApplicationGroup.ARTIFACTFORM));
		setTitle(ReferenceServices.getDisplay(artifact.getArtifactRefId()));
		if (!artifact.isNew()) {
			setTitle(ReferenceServices.getDisplay(artifact.getArtifactRefId())
					+ " " + artifact.getArtifactName());
		}
		controlPanel = new ControlPanel(frame);
		if (controls.size() > 0) {
			ApplicationViewModel avm = SecurityServices.getApplicationView(controls.getFirst().getApplicationViewId());
			controlPanel.setInitScript(avm.getInitScript());
			controlPanel.setValidationScript(avm.getValidationScript());
		}
		controlPanel.setChangedListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				getCloseButton().setText(ReferenceServices.getDisplay(FormButtonTextGroup.CANCEL));
				if (controlPanel.isControlsEnabled()) {
					getApplyButton().setEnabled(true);
				}
				getNextButton().setEnabled(false);
				getBackButton().setEnabled(false);
			}
		});
		ISApplicationMediator.getInstance().register(new IReceiveMessage() {

			public boolean receive(ISEvent event, Object value) throws Exception {
				if (event == ISEvent.MADECHANGES) {
					UIContext context = (UIContext) value;
					if (context.equals(UIContext.contextArtifactPanel) || context.equals(UIContext.contextArtifactDocumentPanel)) {
						getCloseButton().setText(ReferenceServices.getDisplay(FormButtonTextGroup.CANCEL));
						if (controlPanel.isControlsEnabled()) {
							getApplyButton().setEnabled(true);
						}
						getNextButton().setEnabled(false);
						getBackButton().setEnabled(false);
						return true;
					}
				}
				return false;
			}
			
		}, this);
		controlPanel.initialize(controls, 2, artifact);
		JPanel panel = controlPanel.getPanel();
		getCenterPanel().removeAll();
		getCenterPanel().add(panel, BorderLayout.CENTER);
		setVisible(true);
		FocusManager fm = FocusManager.getCurrentManager(); 
		fm.focusNextComponent(panel.getComponent(0));
	}
	
	public void init() {
		if (formPref == null) {
			formPref = new FormPref(UIProperties.getPoint75_75(), UIProperties.getWINDOW_SIZE_800_600());
		}
		setLocation(formPref.getLocation());
		setSize(formPref.getSize());
		
		this.addComponentListener(new ComponentListener() {
			public void componentResized(ComponentEvent e) {
				formPref.setSize(e.getComponent().getSize());
			}
			public void componentMoved(ComponentEvent e) {
				formPref.setLocation(e.getComponent().getLocation());
			}
			public void componentShown(ComponentEvent e) {}
			public void componentHidden(ComponentEvent e) {}
		});
		addListeners();
	}
	
	public void addListeners() {
		getNextButton().setVisible(true);
		getBackButton().setVisible(true);
		ActionListener save = new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try {
					if (!GUI.requiredFieldsPopulated(controls, artifact)) {
						return;
					}
					if (!controlPanel.hasAppliedChanges()) {
						return;
					}
					if (!controlPanel.validateScript()) {
						return;
					}
					final boolean newArtifact = artifact.getArtifactId() == 0;
					final ArtifactModel storeArtifactId = new ArtifactModel();
					storeArtifactId.setArtifactId(artifact.getArtifactId());
					int updatedArtifactId = 0;
					final int parentArtifactId = parentArtifact == null ? 0 : parentArtifact.getArtifactId();
					if (parentArtifact != null && parentArtifact.getArtifactId() != 0) {
						updatedArtifactId= RequirementServices.UpdateArtifact(artifact, parentArtifact, RelationGroup.get(RelationGroup.RELATED)).getPrimaryKeyId();
					} else {
						updatedArtifactId = RequirementServices.UpdateArtifact(artifact).getPrimaryKeyId();
					}
					if (newArtifact) {
						artifact.setArtifactId(updatedArtifactId);
						storeArtifactId.setArtifactId(updatedArtifactId);
					}
					SwingWorker sw = new SwingWorker(frame) {

						@Override
						protected void doNonUILogic() throws Exception {
							ISApplicationMediator.getInstance().receive(ISEvent.APPLIEDCHANGES, UIContext.contextArtifactDocumentPanel);
							Thread.sleep(250);
							ArtifactModel art = RequirementServices.getArtifact(storeArtifactId.getArtifactId());
							setUserObject(art);
						}

						@Override
						protected void doUIUpdateLogic() throws Exception {
							ArtifactModel updatedArtifact = (ArtifactModel) getUserObject();
							if (cachedFromList != null) {
								cachedFromList.updateWith(updatedArtifact);
							} 
							updateList(updatedArtifact);
							updateTree(newArtifact, updatedArtifact, (UITreeNode) ApplicationObject.getRequirementTree().getSelectedNode(), 
									parentArtifactId);
							
						}
						
					};
					sw.start();
					if (newArtifact) {
						close();
					}
					artifact.resetModified();
					getApplyButton().setEnabled(false);
					getCloseButton().setEnabled(true);
					getCloseButton().setText(ReferenceServices.getDisplay(FormButtonTextGroup.CLOSE));
					buttonNextPrevious.setState(false);
				} catch (Exception ex) {
					Debug.LogException(this, ex);
				} finally {
					parentArtifact = null;
				}
			}
		};
		ActionListener cancel = new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				if (artifact.hasModified()) {
					if (JOptionPane.showConfirmDialog(frame, ReferenceServices.getDisplay(SystemMessageFramework.DOYOUWANTTOSAVECHANGES),
							ReferenceServices.getDisplay(FormButtonTextFramework.APPLY), JOptionPane.YES_NO_OPTION)
							== JOptionPane.YES_OPTION) {
						getApplyButton().getActionListeners()[0].actionPerformed(ae);
					}
				}
				if (getCloseButton().getText().equals(ReferenceServices.getDisplay(FormButtonTextGroup.CLOSE))
						|| artifact.getArtifactId()==0) {
					close();
				} else {
					loadModelByPointer(artifact.getArtifactId());
					buttonNextPrevious.setState(false);
				}
			}
		};
		getCloseButton().addActionListener(cancel);
		getApplyButton().addActionListener(save);
	}
	
	private void close() {
		dispose();
	}
	
	/**
	 * Add or update list
	 * 
	 * @param updatedArtifactId
	 */
	private void updateList(final ArtifactModel m) {
		try {
			boolean found = false;
			if (ApplicationObject.getArtifactList() != null) {
				Enumeration e1 = ApplicationObject.getArtifactList().elements();
				while (e1.hasMoreElements()) {
					ArtifactModel am = (ArtifactModel) e1.nextElement();
					if (am.getArtifactId() == m.getArtifactId()) {
						am.updateWith(m);
						found = true;
						break;
					}
				}
				// else append
				if (!found) {
					if (ApplicationObject.getArtifactList().size() == 0 ||
							ApplicationObject.getArtifactList().getFirst().getArtifactRefId() == m.getArtifactRefId()) {
						ApplicationObject.getArtifactList().add(m);
						ApplicationObject.getRequirementManagerController().getTableList().setTableModel(ApplicationObject.getArtifactList(),80);							
					}
				}
				ApplicationObject.getRequirementManagerController().getTableList().repaint();
			}
			} catch (Exception ex) {
				Debug.LogException(this, ex);
			}
	}

	public void setScrollList(int index, ArtifactList artifactList) {
		try {
			scrollList.clear();
			Enumeration e1 = artifactList.elements();
			while (e1.hasMoreElements()) {
				ArtifactModel am = (ArtifactModel) e1.nextElement();
				scrollList.add(new Integer(am.getArtifactId()));
			}
			buttonNextPrevious.setPointer(index);
			buttonNextPrevious.setState(false);
		} catch (Exception ex) {
			Debug.LogException(this, ex);
		}
	}
	
	private void initPreviousNext() {
		try {
			super.getButtonPanel().initialize(UIPanelButton.ALLBUTTONS);
			buttonNextPrevious = new ButtonNextPrevious();
			getApplyButton().setText(ReferenceServices.getDisplay(FormButtonTextFramework.APPLY));
			getBackButton().setText(ReferenceServices.getDisplay(FormButtonTextFramework.WIZARDBACK));
			getBackButton().setVisible(true);
			getNextButton().setText(ReferenceServices.getDisplay(FormButtonTextFramework.WIZARDNEXT));
			getNextButton().setVisible(true);
			this.buttonNextPrevious.init(getBackButton(),
					getNextButton(), getApplyButton(), scrollList, getReloadListener());
		} catch (Exception ex) {
			Debug.LogException(this, ex);
		}
	}
	
	private ActionListener getReloadListener() {
		return new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if (buttonNextPrevious != null && scrollList != null) {
					if (scrollList.size() > buttonNextPrevious.getPointer()) {
						if (!controlPanel.hasAppliedChanges()) {
							return;
						}
						Integer artifactId = (Integer) scrollList.get(buttonNextPrevious.getPointer());
						loadModelByPointer(artifactId.intValue());
						buttonNextPrevious.setState(false);
					} 
				}
			}
		};
	}

	private void loadModelByPointer(int artifactId) {
		ArtifactList list = (ArtifactList) ApplicationObject.getRequirementManagerController().getTableList().getResultList();
		Enumeration e1 = list.elements();
		while (e1.hasMoreElements()) {
			ArtifactModel cachedFromList = (ArtifactModel) e1.nextElement();
			ArtifactModel am = RequirementServices.getArtifact(cachedFromList.getArtifactId());
			if (am.getArtifactId() == artifactId) {
				this.start(am.getArtifactRefId(), am, cachedFromList, ancestorNode);
				return;
			}
		}
	}
}