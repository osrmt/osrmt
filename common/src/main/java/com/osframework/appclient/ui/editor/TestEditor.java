package com.osframework.appclient.ui.editor;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;

import com.osframework.appclient.ui.common.GUI;
import com.osframework.framework.utility.FileSystemUtil;

public class TestEditor {
	
	private UIDocumentTableModel model = null;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			TestEditor testEditor = new TestEditor();
			testEditor.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void start() throws Exception{
		JPanel panel = new JPanel(new BorderLayout());
		List<IDocumentLine> lines = getLines();
		buildReport(lines);
		model = new UIDocumentTableModel(getLines());
		UIDocumentTable table = new UIDocumentTable(model);
		panel.add(table, BorderLayout.CENTER);
		display(panel);
	}

	
	public static void display(javax.swing.JPanel panel) throws Exception {
		//javax.swing.JEditorPane pane = new JEditorPane();
		//pane.setContentType("text/html");
		//pane.setPage("file:///c:/temp/test.html");
		//panel = new JPanel(new BorderLayout());
		//panel.add(pane, BorderLayout.CENTER);
		JFrame frame = new JFrame();
		frame.setLocation(100, 100);
		frame.setSize(new java.awt.Dimension(800,600));
		frame.getContentPane().add(panel);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
	}
	
	private List<IDocumentLine> getLines() throws Exception {
		List<IDocumentLine> lines = new ArrayList<IDocumentLine>();
		
		DocumentTextLineModel l1 = new DocumentTextLineModel();
		l1.setValue("Proof of concept");
		DocumentTextLineModel l2 = new DocumentTextLineModel();
		l2.setValue("Goal");
		DocumentTextLineModel l3 = new DocumentTextLineModel();
		l3.setValue("The goal is to prove that storing a text document parsed in the database is an acceptable option.");
		String path = "1.PNG";
		ImageLineModel l4 = new ImageLineModel(GUI.getExternalImage(path, this), path);
		DocumentTextLineModel l5 = new DocumentTextLineModel();
		l5.setValue("Minimum functionality to show success includes");
		TableLineModel l6 = new TableLineModel();
		l6.setValue(getInnerTable());
		DocumentTextLineModel l7 = new DocumentTextLineModel();
		l7.setValue("<html>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1) Bullets</html>");
		DocumentTextLineModel l8 = new DocumentTextLineModel();
		l8.setValue("<html>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2) Hyperlinks to <a href=\"OSRMT#12\">internal artifacts</a> or <a href=\"http://www.osrmt.com\">external resources</a>.</html>");
		DocumentTextLineModel l9 = new DocumentTextLineModel();
		l9.setValue("<html>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3) <span style='color:#FF6600'>Colored text</span></html>");
		DocumentTextLineModel l10 = new DocumentTextLineModel();
		l9.setValue("<html>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4) <b>Bold</b>, <i>italics</i> or <u>underline</u> text.</html>");
		lines.add(l1);
		lines.add(l2);
		lines.add(l3);
		lines.add(new DocumentTextLineModel());
		lines.add(l4);
		lines.add(new DocumentTextLineModel());
		lines.add(l5);
		lines.add(new DocumentTextLineModel());
		lines.add(l6);
		lines.add(new DocumentTextLineModel());
		lines.add(l7);
		lines.add(l8);
		lines.add(l9);
		
//		for (int i=0; i<20; i++) {
//			lines.add(new DocumentTextLineModel());
//		}
		
		return lines;
	}
	
	private JTable getInnerTable() {
		Vector<Vector> lines = new Vector<Vector>();
		Vector columns = new Vector<String>();
		columns.add("");
		columns.add("");
		columns.add("");
		columns.add("");
		columns.add("");
		Vector row1 = new Vector<String>();
		row1.add("Small cell specific");
		row1.add("Text formatted");
		row1.add("To specification.");
		lines.add(row1);
		Vector row2 = new Vector<String>();
		row2.add("");
		row2.add("");
		row2.add("");
		lines.add(row2);
		JTable table = new JTable(lines, columns);
		table.setSize(table.getWidth(), 17 * lines.size());
		return table;
	}
	
	private void buildReport(List<IDocumentLine> lines) throws Exception {
		ReportDocument report = new ReportDocument(lines);
		FileSystemUtil.createFile("C:\\temp","test.jrxml",
				report.getContent());
		StringBuffer sb = new StringBuffer(1024);
		sb.append("<report>\r\n");
		sb.append("<line>\r\n");
		for (int i=0; i<lines.size(); i++) {
			sb.append("<fld");
			sb.append(i);
			sb.append("><![CDATA[");
			sb.append(lines.get(i).getIReportValue());
			sb.append("]]></fld");
			sb.append(i);
			sb.append(">\r\n");
		}
		sb.append("</line>\r\n");
		sb.append("</report>");

		FileSystemUtil.createFile("C:\\temp","document.xml",
				sb.toString());
	}

}
