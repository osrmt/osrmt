/*
    TODO Enter file description
    
    Copyright (C) 2006  Aron Lancout Smith

    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program; if not, write to the Free Software
    Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA

*/
package com.osframework.appclient.ui.common;

import java.awt.Component;

import javax.swing.SwingUtilities;

import com.osframework.framework.logging.Debug;

/**
 *
 * TODO osrmt reference
 * <br/>Trace: <a href="/functionality/rm/??.html">??</a>
 */

/**
 * This is a variant of the SwingWorker
 * It works in conjunction with the GlassPane class
 * to allow users to execute timeconsuming task on a separate thread
 * The GlassPane addition can prevent users from executing another SwingWorker task
 * while one SwingWorker task is already executing
 *
 * @author Yexin Chen
 */
public abstract class SwingWorker
{
	//private Thread thread;

	/** 
	 * Class to maintain reference to current worker thread
	 * under separate synchronization control.
	 */
	private static class ThreadVar
	{
		private Thread thread;
		ThreadVar(Thread t)
		{
			thread = t;
		}
		synchronized Thread get()
		{
			return thread;
		}
		synchronized void clear()
		{
			thread = null;
		}
	}

	private ThreadVar threadVar;

	private GlassPane glassPane;
	private java.awt.Component aComponent;
	private Object userObject;
	/**
	 * Start a thread that will call the <code>construct</code> method
	 * and then exit.
	 * @param aComponent a reference to the UI component that's directly using
	 * SwingWorker
	 */
	public SwingWorker(Component aComponent)
	{
		setAComponent(aComponent);
		
		final Runnable doFinished = new Runnable()
		{
			public void run()
			{
				finished();
			}
		};
	
		Runnable doConstruct = new Runnable()
		{
			public void run()
			{
				try
				{
					construct();
				}
				finally
				{
					threadVar.clear();
				}
	
				// Execute the doFinished runnable on the Swing dispatcher thread
				SwingUtilities.invokeLater(doFinished);
			}
		};
	
		// Group the new worker thread in the same group as the "spawner" thread
		Thread t = new Thread(Thread.currentThread().getThreadGroup(), doConstruct);
		threadVar = new ThreadVar(t);
	}
	/**
	 * Activate the capabilities of glasspane
	 * 
	 */
	private void activateGlassPane() 
	{
		// Mount the glasspane on the component window
	    GlassPane aPane = GlassPane.mount(getAComponent(), true);
	
	    // keep track of the glasspane as an instance variable
	    setGlassPane(aPane);
	
	    if (getGlassPane() != null) {
	        // Start interception UI interactions
	        getGlassPane().setVisible(true);
	    }
	}
	/** 
	 * Enable the glass pane (to disable unwanted UI manipulation), then spawn the non-UI logic on a separate thread.
	 */
	private void construct()
	{
		if (getAComponent() != null) {
			activateGlassPane();
		}
		try
		{
			doNonUILogic();
		}
		catch (Exception e)
		{
			Debug.LogException(this, e);
		}
	}
	/**
	 * Deactivate the glasspane
	 * 
	 */
	private void deactivateGlassPane() 
	{
	    if (getGlassPane() != null) {
	        // Stop UI interception
	        getGlassPane().setVisible(false);
	    }
	}
	/**
	 * This method will be implemented by the inner class of SwingWorker
	 * It should only consist of the logic that's unrelated to UI
	 *
	 * @throws java.lang.Exception thrown if there are any errors in the non-ui logic
	 */
	protected abstract void doNonUILogic() throws Exception;
	/**
	 * This method will be implemented by the inner class of SwingWorker
	 * It should only consist of the logic that's related to UI updating, after
	 * the doNonUILogic() method is done. 
	 *
	 * @throws java.lang.Exception thrown if there are any problems executing the ui update logic
	 */
	protected abstract void doUIUpdateLogic() throws Exception;

	/**
	 * Called on the event dispatching thread (not on the worker thread)
	 * after the <code>construct</code> method has returned.
	 */
	protected void finished()
	{
		try {
			deactivateGlassPane();
			doUIUpdateLogic();
		} catch (Exception ex) {
			Debug.LogException(this, ex);
		}
	}
/**
 * Getter method
 * 
 * @return java.awt.Component
 */
protected Component getAComponent()
{
	return aComponent;
}
/**
 * Getter method
 * 
 * @return GlassPane
 */
protected GlassPane getGlassPane()
{
	return glassPane;
}
/**
 * A new method that interrupts the worker thread.  Call this method
 * to force the worker to stop what it's doing.
 */
public void interrupt()
{
	Thread t = threadVar.get();
	if (t != null)
	{
		t.interrupt();
	}
	threadVar.clear();
}
/**
 * Setter method
 * 
 * @param newAComponent java.awt.Component
 */
protected void setAComponent(Component newAComponent)
{
	aComponent = newAComponent;
}
/**
 * Setter method
 * 
 * @param newGlassPane GlassPane
 */
protected void setGlassPane(GlassPane newGlassPane)
{
	glassPane = newGlassPane;
}
/**
 * Start the worker thread.
 */
public void start()
{
	Thread t = threadVar.get();
	if (t != null)
	{
		t.start();
	}
}
/**
 * @return the userObject
 */
public Object getUserObject() {
	return userObject;
}
/**
 * @param userObject the userObject to set
 */
public void setUserObject(Object userObject) {
	this.userObject = userObject;
}


}
