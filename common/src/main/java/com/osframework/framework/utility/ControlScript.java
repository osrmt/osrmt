package com.osframework.framework.utility;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Enumeration;
import java.util.Vector;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ImporterTopLevel;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

import com.osframework.framework.logging.Debug;
import com.osframework.modellibrary.common.IControlModel;

public class ControlScript {
	
	private class ControlScriptRef {
		public Object field;
		public String name;
	}

	private String focusLostScript = null;
	private String focusGainedScript = null;
	private Vector references = new Vector();
	
	/**
	 * Add the focusLostScript or focusGainedScript to the fields
	 * focus listener
	 * 
	 * @param field
	 */
	public void addScript(final com.osframework.appclient.ui.common.ControlPanel controlPanel, java.awt.Component field, final IControlModel model, final Vector components) {
		field.addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent e) {
				addReference(e.getSource(),"field");
				addReference(model,"model");
				addReference(components,"components");
				addReference(controlPanel,"controlpanel");
				execute(getFocusLostScript());
			}
			public void focusGained(FocusEvent e) {
				addReference(e.getSource(),"field");
				addReference(model,"model");
				addReference(components,"components");
				addReference(controlPanel,"controlpanel");
				execute(getFocusGainedScript());
			}
		});
	}
	
	public void addReference(Object field, String name) {
		ControlScriptRef ref = new ControlScriptRef();
		ref.field = field;
		ref.name = name;
		references.add(ref);
	}
	
	public Object execute(String script, Class javaclass) {
		return Context.jsToJava(execute(script), javaclass);
	}
	
	public Object execute(String script, Class javaclass, boolean throwException) throws Exception {
		return Context.jsToJava(execute(script, throwException), javaclass);
	}
	
	/**
	 * Execute the javascript using field as a single reference
	 * The script only interacts with the single control
	 * 
	 * @param script
	 * @param field
	 */
	public Object execute(String script) {
		try {
			return execute(script, false);
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * Execute the javascript using field as a single reference
	 * The script only interacts with the single control
	 * 
	 * @param script
	 * @param field
	 */
	public Object execute(String script, boolean throwException) throws Exception {
		if (script == null) {
			return null;
		}
		Object result = null;
		 Context cx = Context.enter();
		 try {
			 Scriptable scope = new ImporterTopLevel(cx);
			 //Scriptable scope = cx.initStandardObjects();
			 //if (field != null) {
			//	 Object ctxField = Context.javaToJS(field, scope);
			//	 ScriptableObject.putProperty(scope, "field", ctxField);
			// }
			 Enumeration e1 = references.elements();
			 while (e1.hasMoreElements()) {
				 ControlScriptRef ref = (ControlScriptRef) e1.nextElement();
				 Object ctx = Context.javaToJS(ref.field, scope);
				 ScriptableObject.putProperty(scope, ref.name, ctx);
			 }
			 result = cx.evaluateString(scope, script, "<cmd>", 1, null);
			 return result;
		 } catch (Exception ex) {
				Debug.LogException(this, ex);
				Debug.LogError(this, script);
				if (throwException) {
					throw ex;
				} else {
					return null;
				}
		 } finally {
			 Context.exit();
		 }		
	}

	
	public String getFocusGainedScript() {
		return focusGainedScript;
	}
	public void setFocusGainedScript(String focusGainedScript) {
		this.focusGainedScript = focusGainedScript;
	}
	public String getFocusLostScript() {
		return focusLostScript;
	}
	public void setFocusLostScript(String focusLostScript) {
		this.focusLostScript = focusLostScript;
	}
	
}
