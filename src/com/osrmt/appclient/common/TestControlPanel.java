package com.osrmt.appclient.common;


import java.awt.*;
import javax.swing.*;


import com.osframework.appclient.ui.common.GUI;
import com.osframework.appclient.ui.common.IControlDef;
import com.osframework.appclient.ui.common.UIFormLayout;
import com.osframework.modellibrary.reference.security.*;
import com.osframework.appclient.services.*;
import com.osframework.modellibrary.reference.group.*;
import com.osrmt.appclient.services.RequirementServices;
import com.osrmt.modellibrary.reqmanager.ArtifactModel;

public class TestControlPanel {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GUI.setLookAndFeel();
	
		ApplicationUserModel user = new ApplicationUserModel();
		user.setUserId(1);
		Application.setUser(user);
		
		JFrame frame = new JFrame();
		ApplicationControlList acl = SecurityServices.getAppControls(ViewFramework.get(0),131,ApplicationFramework.get(134));
		ArtifactModel am = RequirementServices.getArtifact(5582085);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add(getPanel(), BorderLayout.CENTER);
		frame.setSize(800,600);
		frame.setVisible(true);
	}
	
	//ControlPanel.getPanel(frame, acl,am)
	private static JPanel getPanel() {
		IControlDef ifc1 = new IControlDef(){
			public int getHeight() { return 1; };
			public int getWidth() { return 1; };
			public double getGrowthHeight() {return 0; };
			public double getGrowthWidth() { return 0.5; };
			public String getLabel() { return ""; }
		};
		IControlDef ifc2 = new IControlDef(){
			public int getHeight() { return 1; };
			public int getWidth() { return 1; };
			public double getGrowthHeight() {return 0; };
			public double getGrowthWidth() { return 2; };
			public String getLabel() { return ""; }
		};
		IControlDef ifc3 = new IControlDef(){
			public int getHeight() { return 1; };
			public int getWidth() { return 1; };
			public double getGrowthHeight() {return 0; };
			public double getGrowthWidth() { return 1; };
			public String getLabel() { return ""; }
		};
		IControlDef ifc4 = new IControlDef(){
			public int getHeight() { return 3; };
			public int getWidth() { return 5; };
			public double getGrowthHeight() {return 0; };
			public double getGrowthWidth() { return 1; };
			public String getLabel() { return ""; }
		};
		IControlDef ifc5 = new IControlDef(){
			public int getHeight() { return 3; };
			public int getWidth() { return 5; };
			public double getGrowthHeight() {return 1; };
			public double getGrowthWidth() { return 1; };
			public String getLabel() { return ""; }
		};
		IControlDef ifc6 = new IControlDef(){
			public int getHeight() { return 3; };
			public int getWidth() { return 5; };
			public double getGrowthHeight() {return 0; };
			public double getGrowthWidth() { return 1; };
			public String getLabel() { return ""; }
		};
		UIFormLayout layout = new UIFormLayout(2);
		JEditorPane pane = new JEditorPane();
		pane.setPreferredSize(new Dimension(200,200));
		JEditorPane pane2 = new JEditorPane();
		pane2.setPreferredSize(new Dimension(900,200));
		JEditorPane pane3 = new JEditorPane();
		pane3.setPreferredSize(new Dimension(200,200));
		layout.addControl(ifc1, new JTextField(10), new JLabel("testing:"));
		layout.addControl(ifc2, new JTextField(15), new JLabel("line two:"));
		layout.addControl(ifc3, new JTextField(15), new JLabel("second line:"));
		layout.addControl(ifc4, new JScrollPane(pane), new JLabel("wide one:"));
		layout.addControl(ifc5, new JScrollPane(pane2), new JLabel("wide one:"));
		layout.addControl(ifc6, new JScrollPane(pane3), new JLabel("wide one:"));
		layout.buildLayoutSpec();
		System.out.println(layout);
		JPanel panel = layout.getPanel();
		return panel;
	}


}
