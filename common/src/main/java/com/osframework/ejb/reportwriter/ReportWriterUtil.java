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
package com.osframework.ejb.reportwriter;
import javax.naming.NoInitialContextException;

public class ReportWriterUtil
{
   private static ReportWriterHome cachedRemoteHome = null;
   private static LocalReportWriterHome cachedRemoteLocalHome = null;

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

   private static ReportWriterHome getHome() throws javax.naming.NamingException
   {
      if (cachedRemoteHome == null) {
            cachedRemoteHome = (ReportWriterHome) lookupHome(null, ReportWriterHome.COMP_NAME, ReportWriterHome.class);
      }
      return cachedRemoteHome;
   }

   public static LocalReportWriterHome getLocalHome() throws javax.naming.NamingException
   {
      if (cachedRemoteLocalHome == null) {
            cachedRemoteLocalHome = (LocalReportWriterHome) lookupHome(null, LocalReportWriterHome.COMP_NAME, ReportWriterHome.class);
      }
      return cachedRemoteLocalHome;
   }


	public static IReportWriter getReportWriter() throws Exception {
		try {
			ReportWriterHome ejbhome = getHome();
			ReportWriter refejb = ejbhome.find();
			return refejb;
		} catch (NoInitialContextException nice) {
			return ReportWriterBean.get2TierInstance();
		}
	}


}
