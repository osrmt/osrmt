/**    Copyright (C) 2006  PSC (Poland Solution Center)
 *
 *    This program is free software; you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation; either version 2 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program; if not, write to the Free Software
 *    Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA 
 */

package com.osrmt.www.services;

import com.osframework.datalibrary.common.DataAccessException;
import com.osframework.ejb.reportwriter.IReportWriter;
import com.osframework.ejb.reportwriter.ReportWriterUtil;
import com.osframework.framework.logging.Debug;
import com.osframework.modellibrary.common.ServiceCall;
import com.osframework.modellibrary.reference.group.ReferenceGroup;
import com.osframework.modellibrary.reportwriter.ReportList;
import java.rmi.RemoteException;

/**
 *
 * @author zborowskil
 */
public class LocalReportWriterServices extends BaseService {
    
    /** Creates a new instance of LocalReportWriterServices */
    public LocalReportWriterServices() {
        super();
    }
    
    private static IReportWriter getReportWriterRef() throws Exception {
        return ReportWriterUtil.getReportWriter();
    }
    
    public static ReportList getReports(ServiceCall call) throws RemoteException, DataAccessException, Exception {
        try {
            ReportList reports = getReportWriterRef().getReports(call);
            return reports;
        } catch (Exception e) {
            Debug.LogException("SystemServices", e);
            return new ReportList();
        }
    }
    
}
