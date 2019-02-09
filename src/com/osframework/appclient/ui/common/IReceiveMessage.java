/*
    IReceiveMessage is the interface for a receiver of messages
    
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
 * IReceiveMessage is the interface for a receiver of messages
 *
 * <br/>Trace: <a href="/functionality/rm/1760038.html">1760038</a>
 */
public interface IReceiveMessage {

	/**
	 * Implement functionality to handle event
	 * 
	 * @param event
	 * @param value
	 * @return TODO
	 */
	public boolean receive(ISEvent event, Object value) throws Exception;
		
}
