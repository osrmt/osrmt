package com.osframework.appclient.ui.common;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.*;
import java.text.*;
import javax.swing.event.*;
import javax.swing.text.*;

import com.jgoodies.forms.builder.*;
import com.jgoodies.forms.layout.*;

import com.osrmt.modellibrary.reports.ReportParameterModel;
import com.osframework.appclient.ui.components.*;
import com.osframework.appclient.ui.controls.*;
import com.osframework.modellibrary.common.*;
import com.osframework.modellibrary.reference.security.*;
import com.osframework.appclient.services.*;
import com.osframework.appclient.ui.listeners.*;
import com.osframework.modellibrary.reference.common.*;
import com.osframework.modellibrary.reference.group.*;
import com.osframework.modellibrary.system.*;
import com.osframework.framework.logging.*;
import com.osframework.framework.utility.*;


public class ControlPanel {
	
	private JPanel panel = new JPanel(new BorderLayout());
	private JTabbedPane tabbedPane = new JTabbedPane();
	private RuleScript script = null;
	private JFrame frame = null;
	private JDialog dialog = null;
	private Vector<FormControl> components = new Vector<FormControl>();
	private ActionListener changedListener = null;
	private String initScript = null;
	private String validationScript = null;
	private IControlModel model = null;
	private boolean controlsEnabled = true;

	public ControlPanel(JFrame frame) {
		this.frame = frame;
		script = new RuleScript(null,null);
		tabbedPane.putClientProperty("jgoodies.noContentBorder", Boolean.TRUE);
	}
	
	public ControlPanel(JFrame frame, JDialog dialog) {
		this(frame);
		this.frame = frame;
		this.dialog = dialog;
	}
	
	/**
	 * Create the JPanel from the application control list.
	 * If any of the controls return multiple values in a Vector
	 * then it is the reposnsibility of the IControlModel to handle that
	 * 
	 * @param acl
	 * @return
	 */
	public static JPanel getPanel(JFrame frame, ApplicationControlList acl, IControlModel model, int controlColumns) {
		ControlPanel cp = new ControlPanel(frame);
		cp.initialize(acl, controlColumns, model);
		cp.setFrame(frame);
		return cp.getPanel();
	}
	
	/**
	 * Create the JPanel from the application control list.
	 * If any of the controls return multiple values in a Vector
	 * then it is the reposnsibility of the IControlModel to handle that
	 * 
	 * @param acl
	 * @return
	 */
	public static JPanel getPanel(JFrame frame, ApplicationControlList acl, IControlModel model) {
		return getPanel(frame,acl,model,2);
	}
	
	/**
	 * Initializes from the application control list.
	 * If any of the controls return multiple values in a Vector
	 * then it is the reposnsibility of the IControlModel to handle that
	 * 
	 * @param acl
	 * @return
	 */
	public void initialize(ApplicationControlList acl, int nbrColumns, IControlModel model) {
		this.model = model;
		// Create the default form builder
		//DefaultFormBuilder builder = LayoutUtility.getDefaultBuilder(nbrColumns, 0);
		UIFormLayout layout = new UIFormLayout(nbrColumns);
		acl.sort();
		// iterate through the controls
		Enumeration e1 = acl.elements();
		while (e1.hasMoreElements()) {
			try {
				ApplicationControlModel rm = (ApplicationControlModel) e1.nextElement();
				// report parameters by description
				if (model instanceof ReportParameterModel && rm.getModelColumnRefId() > 0) {
					((ReportParameterModel) model).addIdToName(rm.getModelColumnRefId(), rm.getControlDescription());
				}
				if (rm.getControlTypeRefDisplay().compareTo("Separator")==0) {
					//builder.nextLine();
					//builder.append("");
					//builder.nextLine();
					//builder.append(rm.getControlText());
					//builder.nextLine();
				} else if (rm.getControlTypeRefDisplay().compareTo("Tab")==0){
					tabbedPane.addTab(rm.getControlText(),layout.getPanel());
					//TODO this prevents the loop from breaking up - not good
					layout = new UIFormLayout(nbrColumns);
				} else {
					ApplicationCustomControlModel custom = SecurityServices.getApplicationCustomControl(rm.getApplicationCustomControlId());
					JComponent control = createControl(rm, custom);
					components.add(new FormControl(control, rm, rm, custom));
					// Control description appears in status bar
					GUI.setGUIMessage(control, rm);
					if (rm.isDisabled()) {
						control.setEnabled(false);
					} else {
						bindScripting(control, rm);
					}
					if (rm.getScrollpaneInd()==1) {
						control = new JScrollPane(control);
					}
					layout.addControl(rm, control, new UILabel(rm.getControlText()));
				}
			} catch (Exception ex) {
				Debug.LogException(this, ex);
			}
		}
		if (tabbedPane.getTabCount()==0) {
			tabbedPane.addTab("",layout.getPanel());
		}
		panel.add(tabbedPane, BorderLayout.CENTER);
		initialize(this.getInitScript(), Application.getUser());
	}
	
	public boolean hasAppliedChanges() {
		Enumeration e1 = components.elements();
		while (e1.hasMoreElements()) {
			FormControl fc = (FormControl) e1.nextElement();
			if (fc.getComponent() instanceof IApplyChanges) {
				IApplyChanges ac = (IApplyChanges) fc.getComponent();
				if (ac.hasOutstandingChanges()) {
					if (!ac.applyOutstandingChanges(fc.getControlDefition().getLabel())) {
						return false;
					}
				}
			}
		}
		return true;
	}

	private JComponent createControl(ApplicationControlModel acm, ApplicationCustomControlModel custom) throws Exception {
		try {
			if (acm.getApplicationCustomControlId() == 0) {
				Debug.LogError(this,ReferenceServices.getDisplay(SystemMessageFramework.CUSTOMCONTROLNOTFOUNDFORAPPLICATIONCONTROL) + acm.getApplicationControlId() + " " + acm.getControlText());
				return new JTextField();
			}
			// instantiate the control
			Class controlClass = Class.forName(custom.getClassName());
			JComponent control = (JComponent) controlClass.newInstance();			
			script.executeScript(acm, custom.getPopulateScript(), control, model, dialog, frame, Application.getObject(), Application.getUser());
			
			/** NOTE **
			 * Any additional custom components should reject any runtime values
			 * that are not objects of primitives - Integer, Double, String, GregorianCalendar
			 * or a model that has not implemented IControlValue
			 */
			if (control instanceof IGetDocument) {
				bind(((IGetDocument) control).getDocument(), custom, acm, control);
			} else if (control instanceof IGetCombo) {
				//TODO need a UIComboDocument or similar
				bind(((IGetCombo) control).getModel(), custom, acm);
			} else if (control instanceof UIValueList) {
				bind(((UIValueList) control), custom, acm);
				//TODO Should use an interface getDocument instead of casting
			} else if (control instanceof IGetBoolean) {
				bind(((IGetBoolean) control), custom, acm);
			} else if (control instanceof ICustomBind) { 
				ICustomBind customBind = (ICustomBind) control;
				if (changedListener != null) {
					customBind.addChangeListener(new ChangeListener() {
						public void stateChanged(ChangeEvent e) {
							changedListener.actionPerformed(new ActionEvent(this, 0, null));
						}
					});
				}
			} else {
				Debug.LogError(this,ReferenceServices.getMsg(SystemMessageFramework.SWITCHSTATEMENTFAILED) + " " + controlClass.getName());
			}
			
			return control;
		} catch (Exception ex) {
			if (acm != null) {
				Debug.LogError(this, ReferenceServices.getMsg(SystemMessageFramework.FAILEDTOCREATECONTROL) + " " + acm.toString());
			}
			throw ex;
		}				
	}
	
	public void bind(final Document document, final ApplicationCustomControlModel custom, final ApplicationControlModel acm, final JComponent control) {
		// set existing value
		try {
			GUI.setValue(document, model.getModelColDataAt(acm.getModelColumnRefId()));
		} catch (Exception e) {
			Debug.LogException("ControlPanel", e);
		}
		// update model when control changes 
		if (model.getModelColDatabaseDataType(acm.getModelColumnRefId()) == DatabaseDataTypeFramework.STRING) {
			document.addDocumentListener(new TextDocListener() {
				public void call(String documentText) {
					model.setModelColDataAt(documentText, acm.getModelColumnRefId());
					if (changedListener != null) {
						changedListener.actionPerformed(new ActionEvent(this, 0, null));
					}
				} 
			});
		} else if (model.getModelColDatabaseDataType(acm.getModelColumnRefId()) == DatabaseDataTypeFramework.INTEGER) {
			document.addDocumentListener(new NumberDocListener() {
				public void call(int nbr) {
					model.setModelColDataAt(new Integer(nbr), acm.getModelColumnRefId());
					if (changedListener != null) {
						changedListener.actionPerformed(new ActionEvent(this, 0, null));
					}
				} 
			});
		} else if (model.getModelColDatabaseDataType(acm.getModelColumnRefId()) == DatabaseDataTypeFramework.DOUBLE) {
			document.addDocumentListener(new DoubleDocListener() {
				public void call(double nbr) {
					model.setModelColDataAt(new Double(nbr), acm.getModelColumnRefId());
					if (changedListener != null) {
						changedListener.actionPerformed(new ActionEvent(this, 0, null));
					}
				} 
			});
		} else if (model.getModelColDatabaseDataType(acm.getModelColumnRefId()) == DatabaseDataTypeFramework.DATE) {
			document.addDocumentListener(new DateDocListener() {
				public void call(GregorianCalendar calendar) {
					model.setModelColDataAt(calendar, acm.getModelColumnRefId());
					if (changedListener != null) {
						changedListener.actionPerformed(new ActionEvent(this, 0, null));
					}
				} 
			});
			control.addFocusListener(new FocusListener() {
				public void focusGained(FocusEvent e) {};
				public void focusLost(FocusEvent e) {
					try {
						document.remove(0,document.getLength());
						document.insertString(0, model.getModelColDataAt(acm.getModelColumnRefId()).toString(), null);
					} catch (Exception ex) {}
				};
			});
		} else {
			Debug.LogError("ControlPanel", ReferenceServices.getMsg(SystemMessageFramework.SWITCHSTATEMENTFAILED,"ModelColumnRefId " + acm.getModelColumnRefId()));
		}
		try {
			// pre populate with any default values if this is a new model
			if (model.isNew() && acm.getDefaultValue() != null) {
				document.insertString(0,acm.getDefaultValue(), null);
			} 
		} catch (BadLocationException e) {
			Debug.LogException("ControlPanel", e);
		}

	}
	
	public void bind(final ComboBoxModel comboBoxModel, final ApplicationCustomControlModel custom, final ApplicationControlModel acm) {
		GUI.selectValue(comboBoxModel, model.getModelColDataAt(acm.getModelColumnRefId()));
		// update model when control changes 
		comboBoxModel.addListDataListener(new UIListDataSelectionListener(frame) {
			public void valueChangedExecuted(ListDataEvent e) {
				if (comboBoxModel.getSelectedItem() instanceof IControlModel) {
					model.setModelColDataAt(((IControlModel) comboBoxModel.getSelectedItem()).getPrimaryValue(), acm.getModelColumnRefId());
				} else {
					model.setModelColDataAt(comboBoxModel.getSelectedItem(), acm.getModelColumnRefId());
				}
				if (changedListener != null) {
					changedListener.actionPerformed(new ActionEvent(this, 0, null));
				}
			} 
		});
		// pre populate with any default values if this is a new model
		if (model.isNew()) {
			GUI.selectValue(comboBoxModel, acm.getDefaultValue());
		}
	}
	
	public void bind(final IGetBoolean booleanModel, final ApplicationCustomControlModel custom, final ApplicationControlModel acm) {
		if (model.getModelColDatabaseDataType(acm.getModelColumnRefId()) == DatabaseDataTypeFramework.INTEGER) {
			Integer intValue = (Integer) model.getModelColDataAt(acm.getModelColumnRefId());
			if (intValue != null) {
				booleanModel.setBooleanValue(intValue.intValue() == 1);
			}			
		}
		booleanModel.getModel().addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (model.getModelColDatabaseDataType(acm.getModelColumnRefId()) == DatabaseDataTypeFramework.INTEGER) {
					boolean booleanValue = booleanModel.getModel().isSelected();
					model.setModelColDataAt(new Integer(booleanValue ? 1 :0), acm.getModelColumnRefId());
					if (changedListener != null) {
						changedListener.actionPerformed(new ActionEvent(this, 0, null));
					}
				}
			}
		});
		// pre populate with any default values if this is a new model
		if (model.isNew()) {
			if (acm.getDefaultValue() != null) {
				if (acm.getDefaultValue().equalsIgnoreCase("true") || acm.getDefaultValue().equalsIgnoreCase("1")) {
					booleanModel.setBooleanValue(true);
				}
			}
		}
	}
	
	public void bind(final UIValueList list, final ApplicationCustomControlModel custom, final ApplicationControlModel acm) {
		// pre populate with any default values if this is a new model
		//TODO need something better than pkey since model may have values
		if (model.isNew()) {
			GUI.selectValue(list, acm.getDefaultValue());
		} 
		try {
			Object o = model.getModelColDataAt(acm.getModelColumnRefId()); 
			if (o != null && o instanceof RecordParameterValueList) {
				RecordParameterValueList values = (RecordParameterValueList) o;
				Enumeration e1 = values.elements();
				while (e1.hasMoreElements()) {
					RecordParameterValueModel m = (RecordParameterValueModel) e1.nextElement();
					if (m.getValue()!= null && m.isActive()) {
						GUI.selectValue(list, m.getValue());
					}
				}
			}
		} catch (Exception ex) {
			Debug.LogException(this, ex);
		}
		if (acm.getModelColumnRefId() > 0) {
			list.addListSelectionListener(new UIListSelectionListener(frame) {
				public void valueChangedExecuted(ListSelectionEvent e) throws Exception {
					model.setModelColDataAt(list.getSelectedRows(), acm.getModelColumnRefId());
					if (changedListener != null) {
						changedListener.actionPerformed(new ActionEvent(this, 0, null));
					}
				}
			});
		}
	}

	private void bindScripting(JComponent control, ApplicationControlModel acm) {
		try {
			initControlScript(acm.getInitScript(), control);
			script.bind(control, acm);
			control.setVisible(acm.getVisibleInd() == 1);
			enableControl(control, !acm.isDisabled(), acm.isLocked());
			control.setFocusable(!acm.isDisabled());
			if (acm.getFocusLostScript()!=null || acm.getFocusGainedScript()!=null) {
				ControlScript cs = new ControlScript();
				cs.setFocusLostScript(acm.getFocusLostScript());
				cs.setFocusGainedScript(acm.getFocusGainedScript());
				cs.addScript(this, control, model, components);
			}
		} catch (Exception ex) {
			Debug.LogException(this, ex);
		}
	}
	
	private void initControlScript(String script, JComponent component) {
		ControlScript cs = new ControlScript();
		cs.addReference(this, "controlpanel");
		cs.addReference(component, "field");
		cs.addReference(model, "model");
		
		cs.addReference(dialog,"dialog");
		cs.addReference(frame,"frame");
		cs.addReference(Application.getUser(),"user");
		cs.execute(script);
	}
	
	public boolean validateScript() {
		if (this.getValidationScript() != null) {
			ControlScript cs = new ControlScript();
			cs.addReference(this, "controlpanel");
			cs.addReference(components, "components");
			cs.addReference(model, "model");
			
			cs.addReference(dialog,"dialog");
			cs.addReference(frame,"frame");
			cs.addReference(Application.getUser(),"user");
			Boolean validated = (Boolean) cs.execute(this.getValidationScript(), Boolean.class);
			return validated.booleanValue();
		} else {
			return true;
		}
	}
	
	public boolean hasControlComponent(String label) {
		return getControlComponent(label) != null;
	}
	
	public JComponent getControlComponent(String label) {
		Enumeration e1 = components.elements();
		while (e1.hasMoreElements()) {
			FormControl control = (FormControl) e1.nextElement();
			if (control.getComponent() instanceof JComponent) {
				if (control.getApplicationControlModel().getLabel() != null &&
						control.getApplicationControlModel().getLabel().toLowerCase().startsWith(label.toLowerCase())) {
					return (JComponent) control.getComponent();
				}
			}
		}
		return null;
	}
	
	public void setAllControls(boolean enable, boolean locked) {
		Enumeration e1 = components.elements();
		while (e1.hasMoreElements()) {
			FormControl control = (FormControl) e1.nextElement();
			if (control.getComponent() instanceof JComponent) {
				enableControl((JComponent) control.getComponent(), enable, locked);
			}
		}
		setControlsEnabled(enable);
	}
	
	public void disableAllControls() {
		setAllControls(false, true);
	}
	
	public void enableAllControls() {
		setAllControls(true, false);
	}
	
	private void enableControl(JComponent control, boolean enable, boolean locked) {
		try {
			if (control instanceof IGetDocument) {
				((IGetDocument) control).setEnabled(enable);
				((IGetDocument) control).setLocked(locked);
			} else if (control instanceof IGetCombo) {
				((IGetCombo) control).setEnabled(enable);
				((IGetCombo) control).setLocked(locked);
			} else if (control instanceof ICustomBind) {
				if(!this.model.isNew()) {
					((ICustomBind) control).setEnabled(enable);
					((ICustomBind) control).setLocked(locked);
				}
			} else if (control instanceof IGetBoolean) {
				((IGetBoolean) control).setEnabled(enable);
				((IGetBoolean) control).setLocked(locked);
			} else if (control instanceof UIValueList) {
				((UIValueList) control).getJtable().setEnabled(enable);
			} else { 
				Debug.LogWarning(this, ReferenceServices.getMsg(SystemMessageFramework.SWITCHSTATEMENTFAILED,control.getClass().getName()));
			}
		} catch (Exception ex) {
			Debug.LogException(this, ex);
		}
	}
	
	private void initialize(String initScript, ApplicationUserModel user) {
		if (initScript != null) {
			ControlScript cs = new ControlScript();
			cs.addReference(this, "controlpanel");
			cs.addReference(components, "components");
			cs.addReference(model, "model");
			cs.addReference(dialog,"dialog");
			cs.addReference(frame,"frame");
			cs.addReference(user,"user");
			cs.execute(initScript);
			
		}
	}
	
	
	public JPanel getPanel() {
		return panel;
	}

	public void setPanel(JPanel panel) {
		this.panel = panel;
	}

	public Vector getFormControls() {
		return components;
	}

	public ActionListener getChangedListener() {
		return changedListener;
	}

	public void setChangedListener(ActionListener changedListener) {
		this.changedListener = changedListener;
	}

	public String getInitScript() {
		return initScript;
	}

	public void setInitScript(String initScript) {
		this.initScript = initScript;
	}

	public String getValidationScript() {
		return validationScript;
	}

	public void setValidationScript(String validationScript) {
		this.validationScript = validationScript;
	}

	public JDialog getDialog() {
		return dialog;
	}

	public void setDialog(JDialog dialog) {
		this.dialog = dialog;
	}

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	public boolean isControlsEnabled() {
		return controlsEnabled;
	}

	public void setControlsEnabled(boolean controlsEnabled) {
		this.controlsEnabled = controlsEnabled;
	}
	
	
}
