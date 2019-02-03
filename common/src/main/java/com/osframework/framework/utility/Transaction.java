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

/**
*
* TODO osrmt reference
* <br/>Trace: <a href="/functionality/rm/??.html">??</a>
*/
public class Transaction {

private int transactionNbr = 0;

private String taggedName = null;

private String methodName = null;

private long executions = 0;

private long timeMS = 0;

private long startTimeMs = 0;

private long stopTimeMs = 0;

/**
 * @return the methodName
 */
public String getMethodName() {
	return methodName;
}

/**
 * @param methodName the methodName to set
 */
public void setMethodName(String methodName) {
	this.methodName = methodName;
}

/**
 * @return the taggedName
 */
public String getTaggedName() {
	return taggedName;
}

/**
 * @param taggedName the taggedName to set
 */
public void setTaggedName(String taggedName) {
	this.taggedName = taggedName;
}

/**
 * @return the transactionNbr
 */
public int getTransactionNbr() {
	return transactionNbr;
}

/**
 * @param transactionNbr the transactionNbr to set
 */
public void setTransactionNbr(int transactionNbr) {
	this.transactionNbr = transactionNbr;
}

/**
 * @return the executions
 */
public long getExecutions() {
	return executions;
}

/**
 * @param executions the executions to set
 */
public void setExecutions(long executions) {
	this.executions = executions;
}

/**
 * @return the timeMS
 */
public long getTimeMS() {
	return timeMS;
}

/**
 * @param timeMS the timeMS to set
 */
public void setTimeMS(long timeMS) {
	this.timeMS = timeMS;
}

/**
 * @return the startTimeMs
 */
public long getStartTimeMs() {
	return startTimeMs;
}

/**
 * @param startTimeMs the startTimeMs to set
 */
public void setStartTimeMs(long startTimeMs) {
	this.startTimeMs = startTimeMs;
}

/**
 * @return the stopTimeMs
 */
public long getStopTimeMs() {
	return stopTimeMs;
}

/**
 * @param stopTimeMs the stopTimeMs to set
 */
public void setStopTimeMs(long stopTimeMs) {
	this.stopTimeMs = stopTimeMs;
	this.executions++;
	this.timeMS+=(this.stopTimeMs-this.startTimeMs);
}

public String toString() {
	String s = this.getMethodName() + "\tx" + this.executions + "\t";
	if (this.getExecutions() > 0) {
		int valuex10 = ((int)(((double)this.timeMS*10)/((double)this.getExecutions())));
		s+= (((double)valuex10)/10) + "ms";
	} else {
		s+="0ms";
	}
	return s;
}

}
