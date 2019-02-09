package com.osframework.ejb.common;

import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.util.Vector;

import com.osframework.datalibrary.common.*;
import com.osframework.ejb.reference.security.*;
import com.osframework.framework.logging.*;
import com.osframework.ejb.reference.security.*;
import com.osframework.modellibrary.common.*;
import com.osframework.modellibrary.reference.security.*;

public class BaseBean {

   	protected  com.osframework.ejb.reference.security.ISecurity security;
   	public static String dbWildcard = "%";
   	
	public BaseBean() {
		try {
				security = SecurityUtil.getSecurity();
		} catch (Exception ex) {
			Debug.LogException(this, ex);
		}
	}
	
	protected void authorize(int positionRefId, int actionRefId) throws RemoteException, DataAccessException, AuthorizeException {
		security.authorize(positionRefId, actionRefId);
	}
	
	protected void startService(ServiceCall call) throws RemoteException, DataAccessException, AuthorizeException {
		if (Debug.IsInfoLogged()) {
			Statistics.startCall();
		}
		if (call != null && call.getUser() != null) {
			authorize(call.getUser().getPositionRefId(), call.getActionRefId());
		} else {
			authorize(0, call.getActionRefId());
		}
	}
	
	protected void stopService(ServiceCall call) throws RemoteException, DataAccessException, AuthorizeException {
		if (Debug.IsInfoLogged()) {
			Statistics.stopCall();
		}
	}
	
	public void printStats(boolean resetStats) {
		try {
			Debug.LogDebug(this, Statistics.statsToString(resetStats));
		} catch (Exception ex) {
			Debug.LogException(this, ex);
		}
	}
	

}
