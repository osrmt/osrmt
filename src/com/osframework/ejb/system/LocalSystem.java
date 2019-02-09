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
package com.osframework.ejb.system;

import com.osframework.datalibrary.common.*;
import com.osframework.modellibrary.common.*;
import com.osframework.modellibrary.reference.security.*;
import com.osframework.modellibrary.reference.group.*;
import com.osframework.appclient.ui.tree.*;
import java.util.*;
import javax.swing.tree.*;
import com.osframework.modellibrary.system.*;

public interface LocalSystem extends javax.ejb.EJBLocalObject, ISystem
{
    /**  
     *  Return all files
     */ 
    public RecordFileList getFiles(int tableRefId, int tableId, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Store a binary file
     */ 
    public void storeBinaryFile(RecordFileModel m, byte[] content, boolean versionControl, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Retreive a binary file
     */ 
    public byte[] getBinaryFile(RecordFileModel m, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Update a record
     */ 
    public void updateFile(RecordFileModel m, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Returns the list of parameters for the record
     */ 
    public RecordParameterList getParameters(RecordParameterModel rpm, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Returns the stored text file as a string
     */ 
    public String getTextFile(RecordFileModel m, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Export all system required record files
     */ 
    public RecordFileList exportRecordFile(ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Import all record files
     */ 
    public int importRecordFile(RecordFileList list, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Export record parameters
     */ 
    public RecordParameterList exportRecordParameter(ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Import record parameters
     */ 
    public int importRecordParameter(RecordParameterList list, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Calculate the size of the baseline
     */ 
    public String calculateBaseline(int productRefId, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Make a copy of the baseline attachments and return the temporary directory name
     */ 
    public String createTempBaselineDirectory(int productRefId, RecordFileList files, ServiceCall cal) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  rename the temp directory to the baseline directory name
     */ 
    public void renameBaselineDirectory(String tempDirName, int newBaselineId, RecordFileList files, DbConnection conn, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Record the file model
     */ 
    public RecordFileModel getRecordFile(int recordFileId, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Get all files for a product
     */ 
    public RecordFileList getProductFiles(int productRefId, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


}
