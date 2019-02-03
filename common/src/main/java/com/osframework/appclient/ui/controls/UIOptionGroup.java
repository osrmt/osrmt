package com.osframework.appclient.ui.controls;
import java.awt.BorderLayout;
import java.awt.LayoutManager;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.event.ChangeListener;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class UIOptionGroup extends JPanel implements ICustomBind {
	
	private ButtonGroup group = new ButtonGroup();
	private int selected = 0;
	//TODO Redo this control
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setSize(600,400);
		UIOptionGroup grp = new UIOptionGroup();
		JPanel panel = new JPanel();
		panel.add(grp);
		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		grp.setButtonText(0,"test");
		grp.setButtonText(1,"this is ok");
		frame.setVisible(true);
	}
	
	public UIOptionGroup(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
	}

	public UIOptionGroup(LayoutManager layout) {
		super(layout);
	}

	public UIOptionGroup(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
	}

	public UIOptionGroup() {
		super();
		JRadioButton b1 = new JRadioButton();
		JRadioButton b2 = new JRadioButton();
		init(b1, b2);
	}
		
	public void init(JRadioButton b1, JRadioButton b2) {
		b1.setSelected(true);
		
		group.add(b1);
		group.add(b2);
		
		FormLayout layout = new FormLayout(
				"150dlu",
				"p, 3dlu, p, 3dlu");
				layout.setRowGroups(new int[][]{{3}});

				UIJPanel panel = new UIJPanel(layout);

				CellConstraints cc = new CellConstraints();
				//panel.add(new UILabel( ReferenceServices.getDisplay(FormButtonTextFramework.FILENAME) + ":"),cc.xy(1,1));
				panel.add(b1,cc.xy(1,1));
				panel.add(b2,cc.xy(1,3));
				add(panel);
				this.revalidate();
	}

	public int getSelected() {
		Enumeration e1 = group.getElements();
		int i=0;
		while (e1.hasMoreElements()) {
			AbstractButton button = (AbstractButton) e1.nextElement();
			if (button.isSelected()) {
				this.selected = i;
				return i;
			}
			i++;
		}
		this.selected = -1;
		return -1;
	}

	public void setSelected(int selected) {
		this.selected = selected;
		Enumeration e1 = group.getElements();
		int i=0;
		while (e1.hasMoreElements()) {
			AbstractButton button = (AbstractButton) e1.nextElement();
			if (selected == i) {
				button.setSelected(true);
			} else {
				button.setSelected(false);
			}
			i++;
		}
	}
	
	public void setButtonText(int position, String text) {
		Enumeration e1 = group.getElements();
		int i=0;
		while (e1.hasMoreElements()) {
			AbstractButton button = (AbstractButton) e1.nextElement();
			if (position == i) {
				button.setText(text);
			}
			i++;
		}
	}

	public void setLocked(boolean locked) {
		// TODO Auto-generated method stub
		
	}

	public void addChangeListener(ChangeListener change) {
		// TODO Auto-generated method stub
		
	}
	
}

