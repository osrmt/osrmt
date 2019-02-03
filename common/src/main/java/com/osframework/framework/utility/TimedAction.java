package com.osframework.framework.utility;

import java.util.Timer;
import java.util.TimerTask;

public abstract class TimedAction {
    Timer timer = new Timer();

    public TimedAction(double seconds) {
        timer.schedule(new TimerTask() {
        	public void run() {
        		try {
        		javax.swing.SwingUtilities.invokeAndWait(new Runnable() {

					public void run() {
		                executeTask();
					}
        		});
        		} catch (Exception ex) {
        			com.osframework.framework.logging.Debug.LogException("Timer", ex);
        		}
                timer.cancel();
        	}
        }, (int) (seconds*1000));
	}
    
    public abstract void executeTask();

}
