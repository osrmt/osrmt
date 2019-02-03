package com.osframework.appclient.ui.common;


/**
 * The IParent interface provides primitive callback functionality.
 * Child classes (often GUI controls or Controllers) are instantiated
 * with by the parent class which implements IParent
 * 	childObject = new ChildClass(this);
 * The child class retains a reference to the parent and may call the
 * parents setValue method to return a value
 *  this.parent.setValue(someObject);
 * In the class of GUI controls often a dispose() follows the set value
 * for modal forms - return control the parent form.
 * Controllers often use the setValue method to implement business
 * logic which follows the child classes call 
 * eg. See RegistrationController/FindPatientController
 * 
 */
public interface IParent {
	
	/**
	 * Return a value to the parent form.  Use null 
	 * to indicate no action.  
	 * @param o
	 */
	public abstract void setValue(Object o);
	
	/**
	 * Return multiple values to the parent form.
	 * 
	 * @param o
	 */
	public abstract void setValue(Object o1, Object o2);


}
