package com.osframework.framework.utility;

import java.awt.Component;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.text.JTextComponent;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ImporterTopLevel;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

import com.osframework.appclient.ui.components.MultiColumnList;
import com.osframework.appclient.ui.components.UIAttachmentList;
import com.osframework.appclient.ui.controls.UIDoubleField;
import com.osframework.appclient.ui.controls.UIEditorPane;
import com.osframework.appclient.ui.controls.UIIntegerField;
import com.osframework.framework.logging.Debug;
import com.osframework.modellibrary.common.IControlModel;
import com.osframework.modellibrary.reference.security.ApplicationControlModel;
import com.osframework.modellibrary.reference.security.ApplicationUserModel;

public class RuleScript {
	
	private String startScript = null;
	private String endScript = null;
	private Context cxStart = null;
	private Context cxEnd = null;
	Scriptable scopeStart = null;
	Scriptable scopeEnd = null;
	
	/**
	 * Initialize object with a start and end script
	 * 
	 * @param startScript
	 * @param endScript
	 */
	public RuleScript(String startScript, String endScript) {
		try {
			this.endScript = endScript;
			this.startScript = startScript;
			cxStart = Context.enter();
			cxEnd = Context.enter();
			scopeStart = new ImporterTopLevel(cxStart);
			scopeEnd = new ImporterTopLevel(cxEnd);
		} catch (Exception ex) {
			Debug.LogException(this, ex);
		}
	}
	
	/**
	 * Execute the start script
	 *
	 */
	public void executeStartScript() throws Exception {
		 try {
			 cxStart.evaluateString(scopeStart, startScript, "<cmd>", 1, null);
		 } catch (Exception ex) {
			 Debug.LogException(this, ex);
			 throw ex;
		 } finally {
			 cxStart.exit();
		 }
	}

	/**
	 * Execute the end script
	 *
	 */
	public Object executeEndScript() throws Exception {
		 try {
			 Object result = cxStart.evaluateString(scopeEnd, endScript, "<cmd>", 1, null);
			 return result;
		 } catch (Exception ex) {
			 Debug.LogException(this, ex);
			 throw ex;
		 }
	}
	
	/**
	 * Add the field reference to the start script with the name fieldRefName
	 * 
	 * @param field
	 * @param fieldRefName
	 */
	public void addStartReferences(Object field, String fieldRefName) {
		try {
			Object ctxField = Context.javaToJS(field, scopeStart);
			ScriptableObject.putProperty(scopeStart, fieldRefName, ctxField);
			
		} catch (Exception ex) {
			Debug.LogException(this, ex);
		}
	}
	
	/**
	 * Add the field reference to the end script with the name fieldRefName
	 * 
	 * @param field
	 * @param fieldRefName
	 */
	public void addEndReferences(Object field, String fieldRefName) {
		try {
			Object ctxField = Context.javaToJS(field, scopeEnd);
			ScriptableObject.putProperty(scopeEnd, fieldRefName, ctxField);
			
		} catch (Exception ex) {
			Debug.LogException(this, ex);
		}
	}
	
	/**
	 * Applies model defined properties including
	 * 	default value
	 * 	visibility
	 * @param field
	 * @return
	 */
	public void bindText (JTextComponent field, ApplicationControlModel acm, IControlModel model, Vector components) {
		try {
			/*
			if (acm.getDefaultValue() != null  && 
					(field.getText() == null || field.getText().compareTo("")==0)) {
				field.setText(acm.getDefaultValue());
			}
			*/
			field.setVisible(acm.getVisibleInd() == 1);
			field.setEditable(acm.getLockedInd()==0);
			field.setFocusable(acm.getLockedInd()==0);
			if (acm.getFocusLostScript()!=null || acm.getFocusGainedScript()!=null) {
				ControlScript cs = new ControlScript();
				cs.setFocusLostScript(acm.getFocusLostScript());
				cs.setFocusGainedScript(acm.getFocusGainedScript());
				cs.addScript(null, field, model, components);
			}
		} catch (Exception e) {
			Debug.LogException(this, e);
		}
	}

	
	
	/**
	 * Applies model defined properties including
	 * 	default value
	 * 	visibility
	 * @param field
	 * @return
	 */
	public void bindList (JComboBox list, ApplicationControlModel acm, IControlModel model, Vector components) {
		try {
			/*
			if (acm.getDefaultValue() != null && list.getSelectedIndex()== -1) {
				GUI.selectValue(list, acm.getDefaultValue());
			}
			*/
			list.setVisible(acm.getVisibleInd() == 1);
			list.setEditable(acm.getLockedInd()==0);
			list.setFocusable(acm.getLockedInd()==0);
			if (acm.getFocusLostScript()!=null || acm.getFocusGainedScript()!=null) {
				ControlScript cs = new ControlScript();
				cs.setFocusLostScript(acm.getFocusLostScript());
				cs.setFocusGainedScript(acm.getFocusGainedScript());
				cs.addScript(null, list, model, components);
			}
		} catch (Exception e) {
			Debug.LogException(this, e);
		}
	}
	
	/**
	 * Applies model defined properties including
	 * 	default value
	 * 	visibility
	 * @param field
	 * @return
	 * TODO Dont refer to controls directly
	 * use an interface
	public void bind (ReferenceSearch list, ApplicationControlModel acm) {
		try {
			if (acm.getDefaultValue() != null) {
				list.setText(acm.getDefaultValue());
			}
			list.setVisible(acm.getVisibleInd() == 1);
			list.setEditable(acm.getLockedInd()==0);
			list.setFocusable(acm.getLockedInd()==0);
			if (acm.getFocusLostScript()!=null || acm.getFocusGainedScript()!=null) {
				ControlScript cs = new ControlScript();
				cs.setFocusLostScript(acm.getFocusLostScript());
				cs.setFocusGainedScript(acm.getFocusGainedScript());
				cs.addScript(list);
			}
		} catch (Exception e) {
			Debug.LogException(this, e);
		}
	}
	 */
	
	/**
	 * Executes script with single field reference
	 * Returns result or null
	 * 
	 * @param script
	 * @param field
	 * @return
	 */
	public Object executeScript(String script, Component field) {
		if (script != null && field != null) {
			ControlScript cs = new ControlScript();
			cs.addReference(field,"field");
			return cs.execute(script);
		} else {
			return null;
		}
	}
	
	/**
	 * Executes script with single field reference
	 * Returns result or null
	 * 
	 * @param script
	 * @param field
	 * @return
	 */
	public Object executeScript(String script, Component field, JDialog dialog) {
		if (script != null && field != null) {
			//TODO document these references
			ControlScript cs = new ControlScript();
			cs.addReference(field,"field");
			cs.addReference(dialog,"dialog");
			return cs.execute(script);
		} else {
			return null;
		}
	}
	
	/**
	 * Executes script with single field reference
	 * Returns result or null
	 * 
	 * @param script
	 * @param field
	 * @return
	 */
	public Object executeScript(ApplicationControlModel acm, String script, Component field) {
		if (script != null && field != null) {
			ControlScript cs = new ControlScript();
			cs.addReference(acm,"applicationControlModel");
			cs.addReference(field,"field");
			return cs.execute(script);
		} else {
			return null;
		}
	}
	
	/**
	 * Executes script with single field reference
	 * Returns result or null
	 * 
	 * @param script
	 * @param field
	 * @return
	 */
	public Object executeScript(ApplicationControlModel acm, String script, Component field, IControlModel model) {
		if (script != null && field != null) {
			ControlScript cs = new ControlScript();
			cs.addReference(acm,"applicationControlModel");
			cs.addReference(field,"field");
			cs.addReference(model,"model");
			return cs.execute(script);
		} else {
			return null;
		}
	}
	
	/**
	 * Executes script with single field reference
	 * Returns result or null
	 * 
	 * @param script
	 * @param field
	 * @return
	 */
	public Object executeScript(ApplicationControlModel acm, String script, Component field, IControlModel model, JDialog dialog, JFrame frame, Object applicationObject, ApplicationUserModel user) {
		if (script != null && field != null) {
			//TODO get rid of rule script in favour of static controlscript methods
			ControlScript cs = new ControlScript();
			cs.addReference(dialog,"dialog");
			cs.addReference(acm,"applicationControlModel");
			cs.addReference(field,"field");
			cs.addReference(model,"model");
			cs.addReference(frame,"frame");
			cs.addReference(user,"user");
			cs.addReference(applicationObject,"applicationObject");
			return cs.execute(script);
		} else {
			return null;
		}
	}
	
	public void bind (Component field, ApplicationControlModel rm) {
		//TODO uncomment below
		try {
			if (field.getClass().getName().compareTo(UIIntegerField.class.getName())==0) {
				bindText((UIIntegerField) field, rm, null, null);
			} else if (field.getClass().getName().compareTo(UIDoubleField.class.getName())==0) {
				bindText((UIDoubleField) field, rm, null, null);
			} else if (field.getClass().getName().compareTo(UIEditorPane.class.getName())==0) {
				bindText((UIEditorPane) field, rm, null, null);
			} else if (field.getClass().getName().compareTo(UIAttachmentList.class.getName())==0) {
				//bind((UIDoubleField) field, rm);
			} else if (field.getClass().getName().compareTo(MultiColumnList.class.getName())==0) {
				//bind((UIDoubleField) field, rm);
			} else if (rm.getInitScript() != null || rm.getFocusGainedScript() != null || rm.getFocusLostScript() != null){
				Debug.LogDebug(this,field.getClass().getName() + " not bound");
			}
		} catch (Exception e) {
			Debug.LogException(this, e);
		}
	}

}
