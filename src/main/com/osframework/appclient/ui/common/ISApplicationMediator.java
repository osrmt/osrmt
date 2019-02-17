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

/**
 * A single application level mediator manages application messages
 * 
 * <br/>Trace: <a href="/functionality/rm/10101554.html">10101554</a>
 */
public class ISApplicationMediator extends ISMediator {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Singleton
	 */
	private static ISApplicationMediator applicationMediator = null;
	
	/**
	 * Singleton
	 */
	private ISApplicationMediator() {
		super(false);
	}
	
	/**
	 * Get the single instance
	 * 
	 * @return
	 */
	public static ISApplicationMediator getInstance() {
		if (applicationMediator == null) {
			applicationMediator = new ISApplicationMediator();
		}
		return applicationMediator;
	}
	

}
