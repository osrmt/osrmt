/*
    //usage

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
package com.osframework.ejb.reference.security;

import java.util.*;
import com.osframework.modellibrary.common.*;
import com.osframework.modellibrary.reference.security.*;
import com.osframework.appclient.ui.tree.*;
import javax.swing.tree.*;
import com.osframework.modellibrary.reference.group.*;

public interface SecurityHome extends javax.ejb.EJBHome
{
	public static final String COMP_NAME="SecurityName";
	public static final String JNDI_NAME="ejb/Security";

	public Security create() throws javax.ejb.CreateException,java.rmi.RemoteException;

	public Security find() throws java.rmi.RemoteException, javax.ejb.FinderException;

}
