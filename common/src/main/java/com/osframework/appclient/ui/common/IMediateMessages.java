/*
    IMediateMessages is an interface to the form mediator for messages
    
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

/**
 * IMediateMessages is an interface to the form mediator for messages
 *
 * <br/>Trace: <a href="/functionality/rm/4605290.html">4605290</a>
 */
public interface IMediateMessages {

	/**
	 * Register the receiver to receive all messages
	 * 
	 * @param receiver
	 */
	public void register(IReceiveMessage receiver, Object owner);
	
	/**
	 * Receive an event and send to the first registered
	 * receiver which processes the message.
	 * 
	 * @param event
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public boolean receive(ISEvent event, Object value) throws Exception;
	
	/**
	 * Receive an event and send to all the registered
	 * receivers.
	 * 
	 * @param event
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public boolean receiveAll(ISEvent event, Object value) throws Exception;
	/**
	 * Remove the receivers for the specified owner
	 * 
	 * @param owner
	 * @return
	 */
	public boolean deregister(Object owner);
}
