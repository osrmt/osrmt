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
package com.osframework.ejb.reference.common;
import javax.naming.NoInitialContextException;

public class ReferenceMapUtil
{
   private static ReferenceMapHome cachedRemoteHome = null;
   private static LocalReferenceMapHome cachedRemoteLocalHome = null;

   private static Object lookupHome(java.util.Hashtable environment, String jndiName, Class narrowTo) throws javax.naming.NamingException {
      // Obtain initial context
      javax.naming.InitialContext initialContext = new javax.naming.InitialContext(environment);
      try {
         Object objRef = initialContext.lookup(jndiName);
         // only narrow if necessary
         if (java.rmi.Remote.class.isAssignableFrom(narrowTo))
            return javax.rmi.PortableRemoteObject.narrow(objRef, narrowTo);
         else
            return objRef;
      } finally {
         initialContext.close();
      }
   }

   // Home interface lookup methods

   private static ReferenceMapHome getHome() throws javax.naming.NamingException
   {
      if (cachedRemoteHome == null) {
            cachedRemoteHome = (ReferenceMapHome) lookupHome(null, ReferenceMapHome.COMP_NAME, ReferenceMapHome.class);
      }
      return cachedRemoteHome;
   }

   public static LocalReferenceMapHome getLocalHome() throws javax.naming.NamingException
   {
      if (cachedRemoteLocalHome == null) {
            cachedRemoteLocalHome = (LocalReferenceMapHome) lookupHome(null, LocalReferenceMapHome.COMP_NAME, ReferenceMapHome.class);
      }
      return cachedRemoteLocalHome;
   }


	public static IReferenceMap getReferenceMap() throws Exception {
		try {
			ReferenceMapHome ejbhome = getHome();
			ReferenceMap refejb = ejbhome.find();
			return refejb;
		} catch (NoInitialContextException nice) {
			return ReferenceMapBean.get2TierInstance();
		}
	}


}
