package com.osframework.appclient.ui.common;

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.AWTEventListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;

import javax.swing.JComponent;
import javax.swing.RootPaneContainer;
import javax.swing.SwingUtilities;

/**
 * This is the glass pane class that intercepts screen interactions during system busy states.
 * TODO tidy up error logging
 * 
 * @author Yexin Chen
 */
public class GlassPane extends JComponent implements AWTEventListener
{
	private static final long serialVersionUID = 1L;
	private Window theWindow;
	private Component activeComponent;
/**
 * GlassPane constructor comment.
 * @param Container a 
 */
protected GlassPane(Component activeComponent)
{
	// add adapters that do nothing for keyboard and mouse actions
	addMouseListener(new MouseAdapter()
	{
	});
	
	addKeyListener(new KeyAdapter()
	{
	});
	
	setActiveComponent(activeComponent);
}
/**
 * Receives all key events in the AWT and processes the ones that originated from the
 * current window with the glass pane.
 *
 * @param event the AWTEvent that was fired
 */
public void eventDispatched(AWTEvent event)
{
	Object source = event.getSource();
	
	// discard the event if its source is not from the correct type
	boolean sourceIsComponent = (event.getSource() instanceof Component);

	if ((event instanceof KeyEvent) && sourceIsComponent)
	{
		// If the event originated from the window w/glass pane, consume the event
		if ((SwingUtilities.windowForComponent((Component) source) == theWindow))
		{
			((KeyEvent) event).consume();
		}
	}
}
/**
 * Finds the glass pane that is related to the specified component.
 * 
 * @param startComponent the component used to start the search for the glass pane
 * @param create a flag whether to create a glass pane if one does not exist
 * @return GlassPane
 */
public synchronized static GlassPane mount(Component startComponent, boolean create)
{
	RootPaneContainer aContainer = null;
	Component aComponent = startComponent;

	// Climb the component hierarchy until a RootPaneContainer is found or until the very top
	while ((aComponent.getParent() != null) && !(aComponent instanceof RootPaneContainer))
	{
		aComponent = (Component) aComponent.getParent();
	}

	// Guard against error conditions if climb search wasn't successful
	if (aComponent instanceof RootPaneContainer)
	{
		aContainer = (RootPaneContainer) aComponent;
	}

	if (aContainer != null)
	{
		// Retrieve an existing GlassPane if old one already exist or create a new one, otherwise return null
		if ((aContainer.getGlassPane() != null) && (aContainer.getGlassPane() instanceof GlassPane))
		{
			return (GlassPane) aContainer.getGlassPane();
		}
		else if (create)
		{
			GlassPane aGlassPane = new GlassPane(startComponent);
			aContainer.setGlassPane(aGlassPane);

			return aGlassPane;
		}
		else
		{
			return null;
		}
	}
	else
	{
		return null;
	}
}
/**
 * Set the component that ordered-up the glass pane.
 *
 * @param aComponent the UI component that asked for the glass pane
 */
private void setActiveComponent(Component aComponent)
{
	activeComponent = aComponent;
}
/**
 * Sets the glass pane as visible or invisible. The mouse cursor will be set accordingly.
 */
public void setVisible(boolean value)
{
	if (value)
	{
		// keep track of the visible window associated w/the component
		// useful during event filtering
		if (theWindow == null)
		{
			theWindow = SwingUtilities.windowForComponent(activeComponent);
			if (theWindow == null)
			{
				if (activeComponent instanceof Window)
				{
					theWindow = (Window) activeComponent;
				}
			}
		}

	   // java.awt.EventQueue waitQueue = new WaitCursorEventQueue(500);
	   // Toolkit.getDefaultToolkit().getSystemEventQueue().push(waitQueue);

		// Sets the mouse cursor to hourglass mode
		this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		
		activeComponent = theWindow.getFocusOwner();

		// Start receiving all events and consume them if necessary
		Toolkit.getDefaultToolkit().addAWTEventListener(this, AWTEvent.KEY_EVENT_MASK);
		
		this.requestFocus();

		// Activate the glass pane capabilities
		super.setVisible(value);
	}
	else
	{
		// Stop receiving all events
		Toolkit.getDefaultToolkit().removeAWTEventListener(this);

		// Deactivate the glass pane capabilities
		super.setVisible(value);

		// Sets the mouse cursor back to the regular pointer
		if (getTopLevelAncestor() != null)
		{
			this.setCursor(null);
		}
	}
}
}
