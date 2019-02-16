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
import com.osframework.appclient.ui.components.*;
import com.osframework.appclient.ui.controls.UIProperties;
import com.osframework.modellibrary.common.*;
import com.osrmt.modellibrary.reference.group.*;
import com.osframework.modellibrary.reference.common.ReferenceList;
import com.osframework.modellibrary.reference.group.ApplicationFramework;
import com.osframework.modellibrary.reference.group.FormButtonTextFramework;
import com.osframework.modellibrary.reference.security.*;


/**
null
*/
public class ArtifactReadOnlyFormController extends UIStandardDialog {
	
	private static final long serialVersionUID = 1L;
	private ArtifactReadOnlyFormController self = this;
	private ApplicationControlList controls = null;
	private ArtifactModel artifact = new ArtifactModel();
	private JFrame frame;
	private ControlPanel controlPanel;
	private static FormPref formPref = null;
	
	//private int appTypeRefId;
	
	public ArtifactReadOnlyFormController(JFrame frame) {
		super(frame);
		this.frame = frame;
		init();
	}
	
	public ArtifactReadOnlyFormController(JDialog dialog) {
		super(dialog, true);
		init();
	}

	/**
	 * New artifact under the parent
	 * 
	 * @param appTypeRefId
	 * @param parentArtifact
	 * @param parentNode
	 */
	public void start(ArtifactModel artifact) {
		this.artifact.updateWith(artifact);
		initializeApplication();
	}

		
	/**
	 * @param args
	 */
	public void initializeApplication () {
		ISApplicationMediator.getInstance().clearAll();
		getButtonPanel().getCmdButton1().setVisible(false);
		getButtonPanel().getCmdButton0().setText(ReferenceServices.getDisplay(FormButtonTextGroup.CLOSE));
		getButtonPanel().getCmdButton0().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});
		initControls();
		setVisible(true);
	}
	
	private void initControls() {
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
		controlPanel.initialize(controls, 2, artifact);
		controlPanel.setAllControls(true, true);
		JPanel panel = controlPanel.getPanel();
		getCenterPanel().removeAll();
		getCenterPanel().add(panel, BorderLayout.CENTER);

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
	}
	
	private void lockAll(ApplicationControlList controls) {
		
	}
	
}