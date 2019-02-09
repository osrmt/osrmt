package com.osframework.ejb.common;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.*;
import java.text.*;
import javax.swing.event.*;
import javax.swing.text.*;

import com.jgoodies.forms.builder.*;
import com.jgoodies.forms.layout.*;

import com.osframework.datalibrary.common.*;
import com.osframework.appclient.ui.components.*;
import com.osframework.appclient.ui.controls.*;
import com.osframework.modellibrary.common.*;
import com.osframework.modellibrary.reference.security.*;
import com.osframework.appclient.services.*;
import com.osframework.appclient.ui.listeners.*;
import com.osframework.modellibrary.reference.common.*;
import com.osframework.modellibrary.reference.group.*;
import com.osframework.datalibrary.common.Db;
import com.osframework.datalibrary.common.DbConnection;
import com.osframework.framework.logging.*;
import com.osframework.framework.utility.*;

public abstract class BeanTransaction extends BaseBean {
	
	private DbConnection connection = null;
	private int intReturn = 0;
	private double doubleReturn = 0;
	private UpdateResult updateResult = new UpdateResult();
	private String stringReturn = null;
	private GregorianCalendar dateReturn = null;
	private Object objectReturn = null;

	/**
	 * Starts the transaction creating a new connection calling execute
	 * 
	 * @param call
	 * @throws Exception
	 */
	public void start(ServiceCall call) throws Exception {
		DbConnection conn = null;
    	try {
    		startService(call);
    		CacheService.clearCache();
    		conn = Db.getConnection();
    		execute(call, conn);
    		stopService(call);
    	} catch (Exception ex) {
    		if (conn != null) {
    			conn.rollback();
    		}
    		Debug.LogException(this, ex);
    		throw ex;
    	} finally {
    		if (conn != null) {
    			conn.close();
    		}
    	}
	}
	
	protected abstract void execute(ServiceCall call, DbConnection connection) throws Exception ;

	public GregorianCalendar getDateReturn() {
		return dateReturn;
	}

	public void setDateReturn(GregorianCalendar dateReturn) {
		this.dateReturn = dateReturn;
	}

	public double getDoubleReturn() {
		return doubleReturn;
	}

	public void setDoubleReturn(double doubleReturn) {
		this.doubleReturn = doubleReturn;
	}

	public int getIntReturn() {
		return intReturn;
	}

	public void setIntReturn(int intReturn) {
		this.intReturn = intReturn;
	}

	public Object getObjectReturn() {
		return objectReturn;
	}

	public void setObjectReturn(Object objectReturn) {
		this.objectReturn = objectReturn;
	}

	public String getStringReturn() {
		return stringReturn;
	}

	public void setStringReturn(String stringReturn) {
		this.stringReturn = stringReturn;
	}

	public void setConnection(DbConnection connection) {
		this.connection = connection;
	}

	public UpdateResult getUpdateResult() {
		return updateResult;
	}

	public void setUpdateResult(UpdateResult updateResult) {
		this.updateResult = updateResult;
	}
	
	
}
