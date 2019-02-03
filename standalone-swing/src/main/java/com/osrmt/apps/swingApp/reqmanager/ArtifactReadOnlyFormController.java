package com.osrmt.apps.swingApp.reqmanager;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.osframework.appclient.services.ReferenceServices;
import com.osframework.appclient.services.SecurityServices;
import com.osframework.appclient.ui.common.ControlPanel;
import com.osframework.appclient.ui.common.ISApplicationMediator;
import com.osframework.appclient.ui.components.FormPref;
import com.osframework.appclient.ui.components.UIStandardDialog;
import com.osframework.appclient.ui.controls.UIProperties;
import com.osframework.modellibrary.reference.group.ApplicationFramework;
import com.osframework.modellibrary.reference.security.ApplicationControlList;
import com.osframework.modellibrary.reference.security.ApplicationViewModel;
import com.osrmt.modellibrary.reference.group.ApplicationGroup;
import com.osrmt.modellibrary.reference.group.FormButtonTextGroup;
import com.osrmt.modellibrary.reqmanager.ArtifactModel;


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