package com.osframework.framework.utility;

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
import com.osframework.modellibrary.common.DbCalendar;

/**
*
* TODO osrmt reference
* <br/>Trace: <a href="/functionality/rm/??.html">??</a>
*/
public class TimingUtility {


private static ServiceCache transactions = new ServiceCache();

/**
 * Start a new monitor on this transaction nbr
 * 
 * @param transactionNbr
 */
public static void startTransaction()  {
	startTransaction(getMethod());
}

/**
 * Start a new monitor on this transaction nbr
 * 
 * @param transactionNbr
 */
public static void startTransaction(String key) {
	Transaction transaction = new Transaction();
	if (transactions.exists(key)) {
		transaction = (Transaction) transactions.get(key);
		transactions.remove(key);
	}
	transaction.setMethodName(key);
	transaction.setStartTimeMs(DbCalendar.getNow().getTimeInMillis());
	transactions.put(key, transaction);
}

/**
 * Start a new monitor on this transaction nbr
 * 
 * @param transactionNbr
 */
public static void stopTransaction() {
	stopTransaction(getMethod());
}

/**
 * Start a new monitor on this transaction nbr
 * 
 * @param transactionNbr
 */
public static void stopTransaction(String key) {
	if (transactions.exists(key)) {
		Transaction transaction = (Transaction) transactions.get(key);
		transactions.remove(key);
		transaction.setStopTimeMs(DbCalendar.getNow().getTimeInMillis());
		transactions.put(key, transaction);
	}
}

/**
 * Get the transaction data
 * @return
 * @throws CacheException
 */
public static String getTransactionData(boolean clear)  throws Exception {
	StringBuffer sb = new StringBuffer(1024*12);
	java.util.Enumeration e1 = transactions.getKeys();
	while (e1.hasMoreElements()) {
		String key = (String) e1.nextElement();
		Transaction transaction = (Transaction) transactions.get(key);
		sb.append(transaction + "\n");
	}
	if (clear) {
		transactions.clear();
	}
	return sb.toString();
}

/**
 * Create a cache key for the class, method and parameters
 * 
 * @param params
 * @return
 * @throws CacheException
 */
protected static String getMethod() {		
	Throwable t = new Throwable();
	StringBuffer sb = new StringBuffer(64);
	if (t.getStackTrace().length > 2) {
		StackTraceElement e = t.getStackTrace()[2];
		sb.append(e.getClassName());
		sb.append("/");
		sb.append(e.getMethodName());
		return sb.toString();
	} else {
		return null;
	}
}

}
