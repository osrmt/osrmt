package com.osrmt.apps.swingApp.common;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

import com.osframework.appclient.ui.components.PanelOkCancel;
import com.osframework.appclient.ui.controls.UIJDialog;
import com.osframework.appclient.ui.controls.UIJPanel;
import com.osframework.appclient.ui.controls.UIProperties;
import com.osframework.appclient.ui.tree.UIScrollTree;
import com.osframework.appclient.ui.tree.UITreeModel;
import com.osrmt.modellibrary.reqmanager.ArtifactList;
import com.osrmt.modellibrary.reqmanager.ArtifactModel;

public class ReqTreeSearch extends UIJDialog {
	
	private static final long serialVersionUID = 1L;

	private PanelOkCancel okCancel = new PanelOkCancel();
	private ActionListener okAction;
	private UIScrollTree tree = new UIScrollTree();

	public ReqTreeSearch(UITreeModel treeModel) throws HeadlessException {
		super();
		tree.display(treeModel, null);
	}

	public ReqTreeSearch(Dialog owner) throws HeadlessException {
		super(owner);
	}

	public ReqTreeSearch(Frame owner, String title, boolean modal)
			throws HeadlessException {
		super(owner, title, modal);
	}
	
	public void initialize(ActionListener okAction, ArtifactModel child) {
		setLayout(new BorderLayout());
		setSize(UIProperties.getDIALOG_SIZE_450_330());
		setLocation(UIProperties.getPoint200_200());
		UIJPanel southPanel = new UIJPanel(new BorderLayout());
		southPanel.add(okCancel, BorderLayout.CENTER);
		UIJPanel centerPanel = new UIJPanel(new BorderLayout());
		centerPanel.add(tree, BorderLayout.CENTER);
		UIJPanel mainPanel = new UIJPanel(new BorderLayout());
		mainPanel.add(southPanel, BorderLayout.SOUTH);
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		add(mainPanel, BorderLayout.CENTER);
		okCancel.addCancelActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		okCancel.addOkActionListener(okAction);
	}

	public PanelOkCancel getOkCancel() {
		return okCancel;
	}

	public void setOkCancel(PanelOkCancel okCancel) {
		this.okCancel = okCancel;
	}

	public ArtifactModel getSelectedArtifact() {
		return (ArtifactModel) tree.getSelectedObject();
	}

	public ArtifactList getAllSelectedArtifacts() {
		ArtifactList list = new ArtifactList();
		Enumeration e1 = tree.getSelectedUserObjects().elements();
		while (e1.hasMoreElements()) {
			ArtifactModel am = (ArtifactModel) e1.nextElement();
			list.add(am);
		}
		return list;
	}

	
}
