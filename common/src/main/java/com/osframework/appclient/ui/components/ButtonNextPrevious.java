package com.osframework.appclient.ui.components;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;

import com.osframework.framework.logging.Debug;

public class ButtonNextPrevious {
	
	private ButtonNextPrevious self = this;
	private JButton buttonPrevious;
	private JButton buttonNext;
	private JButton buttonApply;
	private ArrayList objectList = new ArrayList();
	private ActionListener reloadListener;
	private int pointer = 0;

	public void init(JButton previous, JButton next, JButton apply, ArrayList list, ActionListener reload) {
		this.buttonPrevious = previous;
		this.buttonApply = apply;
		this.buttonNext = next;
		this.objectList = list;
		this.reloadListener = reload;
		this.buttonPrevious.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				self.previous();
			}
		});
		this.buttonNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				self.next(); 
			}
		});
		setState(false);
	}
	
	public void next() {
		if (buttonApply.isEnabled()) {
			Debug.displayGUIMessage("Apply or cancel changes");
		} else {
			if (pointer < objectList.size() -1) {
				pointer++;
			}
			setState(true);
		}
	}

	public void previous() {
		if (pointer > 0) {
			pointer--;
		}
		setState(true);
	}

	public void setState(boolean reload) {
		if (buttonPrevious != null && buttonNext != null && objectList != null) {
			buttonPrevious.setEnabled(pointer>0);
			buttonNext.setEnabled(pointer < objectList.size()-1);
		}
		if (reload && this.reloadListener != null) {
			reloadListener.actionPerformed(new ActionEvent(this, 0, null));
		}
	}

	public JButton getButtonNext() {
		return buttonNext;
	}

	public void setButtonNext(JButton buttonNext) {
		this.buttonNext = buttonNext;
	}

	public JButton getButtonPrevious() {
		return buttonPrevious;
	}

	public void setButtonPrevious(JButton buttonPrevious) {
		this.buttonPrevious = buttonPrevious;
	}

	public ArrayList getObjectList() {
		return objectList;
	}

	public void setObjectList(ArrayList objectList) {
		this.objectList = objectList;
	}

	public int getPointer() {
		return pointer;
	}

	public void setPointer(int pointer) {
		this.pointer = pointer;
	}

	public ActionListener getReloadListener() {
		return reloadListener;
	}

	public void setReloadListener(ActionListener reloadListener) {
		this.reloadListener = reloadListener;
	}

	
}

