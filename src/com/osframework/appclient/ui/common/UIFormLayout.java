package com.osframework.appclient.ui.common;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.*;
import com.jgoodies.forms.builder.*;
import com.jgoodies.forms.layout.*;

import java.util.*;

/**
 * <pre>
 * Generic layout of controls on a panel.
 * Width = (nbr columns * 4)+1
 * Height = (nbr rows * 2)+1
 * First label is at position (2,2)
 * First control is at position (4,2)
 * 
 *    1    2   3      4       5    6   7      8       9
 * ----------------------------------------------------
 * |                                                     1   
 * |    [Label]  [field cntrl]  [Label]  [field cntrl]   2
 * |                                                     3
 * |    [Label]  [field cntrl]  [Label]  [field cntrl]   4
 * |                                                     5
 * ----------------------------------------------------
 * 
 *
 * </pre>
 */
public class UIFormLayout {
	
	private int nbrColumns = 1;
	private int currentColumn = 2;
	private int currentRow= 2;
	private int gridWidth = 0;
	private int gridHeight = 0;
	private int rowMaxHeight = 0;
	private String rowSpec = "";
	private String colSpec = "";
	private int standardRowHeight = 15;
	private Vector controls = new Vector();
	
	public UIFormLayout(int nbrColumns) {
		if (nbrColumns > 1) {
			this.nbrColumns = nbrColumns;
		}
		this.gridWidth = (nbrColumns * 4)+1;
	}
	
	public void buildLayoutSpec() {
		buildRowSpec();
		buildColSpec(nbrColumns);
	}
	
	public JPanel getPanel() {
		buildLayoutSpec();
		FormLayout formLayout = new FormLayout(colSpec, rowSpec);
		PanelBuilder builder = new PanelBuilder(formLayout);
	    builder.setDefaultDialogBorder();
	    CellConstraints cc = new CellConstraints();
	    Enumeration e1 = controls.elements();
	    while (e1.hasMoreElements()) {
	    	FormControl fc = (FormControl) e1.nextElement();
	    	if (fc.getControlDefition().getHeight() > 1 ) {
		    	builder.add(fc.getComponent(), cc.xywh(fc.getXpos(), fc.getYpos(), fc.getWidth(), fc.getHeight(), "fill, fill"));
	    	} else {
		    	builder.add(fc.getComponent(), cc.xywh(fc.getXpos(), fc.getYpos(), fc.getWidth(), fc.getHeight()));
	    	}
	    }
	    return builder.getPanel();
	}
	
	public void addControl(IControlDef ifd, Component component, Component label) {
		
		FormControl labelControl = new FormControl(label, ifd, null, null);
		FormControl componentControl = new FormControl(component, ifd, null, null);

		// wide columns only make sense for multi column forms
		// subtract 4 for the [gap][label][gap]            [gap]
		if (componentControl.getWidth() > gridWidth) {
			componentControl.setWidth(gridWidth-4);
		}
		
		// check control can fit
		if ((currentColumn+2+ifd.getWidth()) > gridWidth) {
			newRow();
		}
		
		// position 2,2
		labelControl.setXpos(currentColumn);
		labelControl.setYpos(currentRow*2);
		labelControl.setWidth(1);
		labelControl.setHeight(1);
		currentColumn+=2;
		controls.add(labelControl);
		
		componentControl.setXpos(currentColumn);
		componentControl.setYpos(currentRow*2);
		componentControl.setWidth(ifd.getWidth());
		componentControl.setHeight(1);
		rowMaxHeight = ifd.getHeight() > rowMaxHeight ? ifd.getHeight() : rowMaxHeight; 
		controls.add(componentControl);
		
		// gap 
		currentColumn++;
		currentColumn += componentControl.getWidth();
				
	}
	
	private void newRow() {
		currentRow++;
		currentColumn = 2;
		
		rowMaxHeight = 1;
	}
	
	public String toString() {
		Enumeration e1 = this.controls.elements();
		String s = "FormLayout layout = new FormLayout(\"" + this.colSpec + "\",\n\"" + this.rowSpec + "\");\n";
		while (e1.hasMoreElements()) {
			FormControl c = (FormControl) e1.nextElement();
			s += c.toString() + "\n";
		}
		return s;
	}

	private void buildColSpec(int nbrColumns) {
		if (nbrColumns == 1) {
			colSpec = "4dlu, right:max(50dlu;p), 4dlu, 75dlu" + getColGrowth(4) + ", 4dlu";
		} else {
			String s = "";
			for (int i=0; i< nbrColumns; i++) {
				if (i < nbrColumns -1) {
					s += ("right:max(50dlu;p), 4dlu, 75dlu" + getColGrowth((i+1)*4) + ", 9dlu, ");
				} else {
					s += ("right:max(50dlu;p), 4dlu, 75dlu" + getColGrowth((i+1)*4) + ", 4dlu ");
				}
			}
			colSpec = "4dlu, " + s + "";
		}
	}
	
	private String getColGrowth(int column) {
		Enumeration e1 = this.controls.elements();
		String s = "";
		while (e1.hasMoreElements()) {
			FormControl c = (FormControl) e1.nextElement();
			if (c.getXpos() == column && c.getControlDefition().getGrowthWidth() > 0) {
				return ":grow(" + c.getControlDefition().getGrowthWidth() + ")";
			}
		}
		return s;
	}
	
	private String getRowGrowth(int row) {
		Enumeration e1 = this.controls.elements();
		String s = "";
		double maxGrowthHeight = 0;
		while (e1.hasMoreElements()) {
			FormControl c = (FormControl) e1.nextElement();
			if ((c.getYpos()) == row && c.getControlDefition().getGrowthHeight() > 0) {
				maxGrowthHeight = c.getControlDefition().getGrowthHeight() > maxGrowthHeight 
						? c.getControlDefition().getGrowthHeight() : maxGrowthHeight;
			}
		}
		if (maxGrowthHeight > 0) {
			s = ":grow(" + maxGrowthHeight + ")";
		} 
		return s;
	}
	
	private int getRowGrowth(int row, int base) {
		Enumeration e1 = this.controls.elements();
		int maxHeight = 0;
		while (e1.hasMoreElements()) {
			FormControl c = (FormControl) e1.nextElement();
			if ((c.getYpos()) == row && c.getControlDefition().getHeight() > 1) {
				maxHeight = c.getControlDefition().getHeight() > maxHeight 
						? c.getControlDefition().getHeight() : maxHeight;
			}
		}
		if (maxHeight > 1) {
			return (base-3) * maxHeight;
		} else {
			return base;
		}
	}
	
	
	private void buildRowSpec() {
		String s = "";
		for (int i=0; i< currentRow; i++) {
			if (i < currentRow-1) {
				s += "top:" + getRowGrowth((i+1)*2, standardRowHeight) + "dlu" + getRowGrowth((i+1)*2) + ", 2dlu, ";
			} else {
				s += "top:" + getRowGrowth((i+1)*2, standardRowHeight) + "dlu" + getRowGrowth((i+1)*2) + ", ";
			}
		}
		this.rowSpec = "4dlu, " + s + " 4dlu";
	}
	
	
	public static void main(String[] args) {
		IControlDef ifc1 = new IControlDef(){
			public int getHeight() { return 1; };
			public int getWidth() { return 1; };
			public double getGrowthHeight() {return 0; };
			public double getGrowthWidth() { return 0; };
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
			public double getGrowthHeight() {return 2; };
			public double getGrowthWidth() { return 1; };
			public String getLabel() { return ""; }
		};
		UIFormLayout layout = new UIFormLayout(2);
		JEditorPane pane = new JEditorPane();
		pane.setPreferredSize(new Dimension(200,200));
		layout.addControl(ifc1, new JTextField(10), new JLabel("testing:"));
		layout.addControl(ifc2, new JTextField(15), new JLabel("line two:"));
		layout.addControl(ifc3, new JTextField(15), new JLabel("second line:"));
		layout.addControl(ifc4, pane, new JLabel("wide one:"));
		layout.buildLayoutSpec();
	}
	
}
