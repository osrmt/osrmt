/*
    ISMediator implements the Mediator pattern to send and receive all messages
    
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

import java.util.ArrayList;


/**
 * ISMediator implements the Mediator pattern to send and receive all messages
 *
 * <br/>Trace: <a href="/functionality/rm/7494384.html">7494384</a>
 */
public class ISMediator implements IMediateMessages {
	
	private class Receiver {
		private IReceiveMessage receiver = null;
		private Object owner = null;
		
		public Receiver(IReceiveMessage ireceive, Object owner) {
			if (ireceive == null || owner == null)  {
				throw new NullPointerException();
			}
			this.setReceiver(ireceive);
			this.setOwner(owner);
		}
		/**
		 * @return the owner
		 */
		public Object getOwner() {
			return owner;
		}
		/**
		 * @param owner the owner to set
		 */
		public void setOwner(Object owner) {
			this.owner = owner;
		}
		/**
		 * @return the receiver
		 */
		public IReceiveMessage getReceiver() {
			return receiver;
		}
		/**
		 * @param receiver the receiver to set
		 */
		public void setReceiver(IReceiveMessage receiver) {
			this.receiver = receiver;
		}
		
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Receiver> receivers = new ArrayList<Receiver>();
	private boolean forwardMessages = true;
	/**
	 * Default constructor
	 *
	 */
	public ISMediator() {
		
	}
	
	/**
	 * Default constructor
	 *
	 */
	protected ISMediator(boolean forwardMessages) {
		this.forwardMessages = forwardMessages;
	}
	
	/**
	 * @see com.patientis.framework.scripting.IMediateMessages#receiveAll(com.patientis.framework.scripting.ISEvent, java.lang.Object)
	 */
	public boolean receiveAll(ISEvent event, Object value) throws Exception {
		boolean received = false;
		// fixing the size prevents ConcurrentModificationException errors
		for (int i=0; i< receivers.size(); i++) {
			receivers.get(i).getReceiver().receive(event, value);
			received = true;
		}
		if (forwardMessages && ISApplicationMediator.getInstance().receive(event, value)) {
			received = true;
		}
		return received;
	}

	/**
	 * @see com.patientis.framework.scripting.IMediateMessages#receive(com.patientis.framework.scripting.ISEvent, java.lang.Object)
	 */
	public boolean receive(ISEvent event, Object value) throws Exception {
		// fixing the size prevents ConcurrentModificationException errors
		for (int i=0; i< receivers.size(); i++) {
			if (receivers.get(i).getReceiver().receive(event, value)) {
				return true;
			}
		}
		if (forwardMessages) {
			return ISApplicationMediator.getInstance().receive(event, value);
		}
		return false;
	}

	/**
	 * @see com.patientis.framework.scripting.IMediateMessages#register(com.patientis.framework.scripting.IReceiveMessage)
	 */
	public void register(IReceiveMessage receiver, Object owner) {
		for (Receiver r : receivers) {
			if (r.getReceiver().equals(receiver)) {
				throw new java.lang.IllegalMonitorStateException();
			}
		}
		receivers.add(new Receiver(receiver, owner));
	}

	/**
	 * Clear the receivers
	 */
	public void clearAll() {
		receivers.clear();
	}

	/**
	 * @see com.patientis.framework.scripting.IMediateMessages#deregister(java.lang.Object)
	 */
	public boolean deregister(Object owner) {
		ArrayList<Receiver> removeList = new ArrayList<Receiver>();
		for (Receiver r : receivers) {
			if (r.getOwner().equals(owner)) {
				removeList.add(r);
			}
		}
		boolean match = false;
		for (Receiver r : removeList) {
			receivers.remove(r);
			match = true;
		}
		return match;
	}
	
	

}
