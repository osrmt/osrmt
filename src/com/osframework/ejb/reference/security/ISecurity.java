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

import com.osframework.datalibrary.common.*;
import com.osframework.modellibrary.common.*;
import com.osframework.modellibrary.reference.security.*;
import com.osframework.modellibrary.reference.group.*;
import com.osframework.appclient.ui.tree.*;
import java.util.*;
import javax.swing.tree.*;
import com.osframework.modellibrary.reference.security.*;
import com.osframework.modellibrary.reference.common.*;
import com.osframework.modellibrary.system.*;

public interface ISecurity
{
    /**  
     *  Returns true if the user name and password combination is valid
     */ 
    public ApplicationUserModel authenticate(ApplicationUserModel user) throws java.rmi.RemoteException, Exception, InvalidUserLoginException, InvalidUserPasswordException;


    /**  
     *  Returns true if the position is authorized to execution the action
	 * for the specified application.
	 * @param positionRefId
	 * @param applicationRefId
	 * @param actionRefId
	 * @return
     */ 
    public void authorize(int positionRefId, int actionRefId) throws java.rmi.RemoteException, DataAccessException, AuthorizeException;


    /**  
     *  Returns a list of application controls for the match app id
     */ 
    public ApplicationControlList getAppControls(ViewFramework view, ApplicationFramework application, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Return the security references which the user has access to for the application
     */ 
    public ApplicationSecurityList getAppSecurity(ApplicationFramework application, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Return the application controls by user given the security configuration
     */ 
    public ApplicationControlList getAppControlsByUser(ApplicationFramework application, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Return the source reference id for specified application control
     */ 
    public int getAppControlSourceRefId(ViewFramework view, ApplicationFramework application, String controlName, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Print middleware stats
     */ 
    public void printStatistics() throws java.rmi.RemoteException, Exception;


    /**  
     *  Return the security references
     */ 
    public ApplicationSecurityList getAppSecurity(ApplicationFramework application, int appTypeRefId, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Get app controls
     */ 
    public ApplicationControlList getAppControls(ViewFramework view, int appTypeRefId, ApplicationFramework application, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Get app controls by user
     */ 
    public ApplicationControlList getAppControlsByUser(ApplicationFramework application, int appTypeRefId, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Get user
     */ 
    public ApplicationUserModel getUser(int userId) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Update user
     */ 
    public UpdateResult UpdateUser(ApplicationUserModel m, ServiceCall c) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Get views
     */ 
    public ReferenceDisplayList getViews(int appTypeRefId, ApplicationFramework application, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Get application types
     */ 
    public ReferenceDisplayList getAppTypes(ApplicationFramework application, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Update multiple app control models
     */ 
    public UpdateResult UpdateApplicationControl(ApplicationControlList acl, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Update application control model
     */ 
    public UpdateResult UpdateApplicationControl(ApplicationControlModel m, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Get all active users
     */ 
    public ApplicationUserList getAllUsers(ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Get a single application control
     */ 
    public ApplicationControlModel getApplicationControl(int applicationControlId, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Get a user by the username
     */ 
    public ApplicationUserModel getUserByLogin(String username, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Return all the distinct application, view and types from the application control table.  Partial model only with those 3 fields populated.
     */ 
    public ApplicationViewList getApplicationViewsByType(ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Return the application security defined for the user
     */ 
    public ApplicationSecurityList getAppSecurity(ApplicationUserModel user, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Return the application security defined for the position
     */ 
    public ApplicationSecurityList getAppSecurity(PositionFramework position, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Return the application security defined globally
     */ 
    public ApplicationSecurityList getAppSecurityGlobal(ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Update the application security
     */ 
    public UpdateResult UpdateApplicationSecurity(ApplicationSecurityModel asm, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Copy the application controls from one view to a new
     */ 
    public UpdateResult duplicateApplicationControlView(String newViewName, ViewFramework view, int appTypeRefId, ApplicationFramework application,  ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Export all system required users
     */ 
    public ApplicationUserList exportSysemUser(ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Import all users
     */ 
    public int importApplicationUser(ApplicationUserList list, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Export all system required application controls
     */ 
    public ApplicationControlList exportApplicationControl(ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Import all controls
     */ 
    public int importApplicationControl(ApplicationControlList list, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Export all system required application security
     */ 
    public ApplicationSecurityList exportApplicationSecurity(ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Import all application security
     */ 
    public int importApplicationSecurity(ApplicationSecurityList list, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Export all application settings
     */ 
    public ApplicationSettingList exportApplicationSetting(ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Import all applicaiton setting
     */ 
    public int importApplicationSetting(ApplicationSettingList list, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Get the application custom control
     */ 
    public ApplicationCustomControlModel getApplicationCustomControl(int id, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  SQL is logged to debug file or not
     */ 
    public void debugSql(boolean debugOn, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Get the application view
     */ 
    public ApplicationViewModel getApplicationView(int applicationViewId, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Application setting list
     */ 
    public ApplicationSettingList getSettingsByUser(ApplicationFramework application, int appTypeRefId, ViewFramework view, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Application setting list
     */ 
    public ApplicationSettingList getSettingsByUser(ApplicationFramework application, int appTypeRefId, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Application setting list
     */ 
    public ApplicationSettingList getSettingsByUser(ApplicationFramework application, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Application setting
     */ 
    public ApplicationSettingList getSettingsByUser(int settingRefId, ApplicationFramework application, int appTypeRefId, ViewFramework view, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Import application view
     */ 
    public int importApplicationView(ApplicationViewList list, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  export application view
     */ 
    public ApplicationViewList exportApplicationView(ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Export custom controls
     */ 
    public ApplicationCustomControlList exportApplicationCustomControl(ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Import custom controls
     */ 
    public int importApplicationCustomControl(ApplicationCustomControlList list, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Update the custom control
     */ 
    public UpdateResult UpdateCustomControl(ApplicationCustomControlModel accm, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Update the application view
     */ 
    public UpdateResult UpdateApplicationView(ApplicationViewList list, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Update the application security
     */ 
    public UpdateResult UpdateApplicationSecurity(ApplicationSecurityList list, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Get the application security
     */ 
    public ApplicationSecurityModel getApplicationSecurity(int applicationSecurityId, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Duplicate application security settings
     */ 
    public boolean duplicateApplicationSecurity(int fromPositionRefId, int toPositionRefId, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Duplicate artifact views and controls
     */ 
    public boolean duplicateArtifactControls(int fromArtifactRefId, int toArtifactRefId, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Clear the system cache
     */ 
    public void clearSystemCache() throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Get the database
     */ 
    public String getEnvironment() throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Get the application user group
     */ 
    public ApplicationUserGroupModel getApplicationUserGroup(int applicationUserGroupId, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Update the application user group
     */ 
    public UpdateResult updateApplicationUserGroup (ApplicationUserGroupModel userGroup, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Get the user group members by group
     */ 
    public ApplicationUserGroupList getUserGroup(int userGroupRefId, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Get the application view
     */ 
    public ApplicationViewModel getApplicationView(ViewFramework view, int appTypeRefId, ApplicationFramework application, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Get all global settings
     */ 
    public ApplicationSettingList getGlobalSettings(ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Update the application settings
     */ 
    public UpdateResult UpdateApplicationSetting(ApplicationSettingList settings, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Get the custom control
     */ 
    public ApplicationCustomControlModel getApplicationCustomControlByRef(int customControlRefId, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Add a new artifact from a copy
     */ 
    public UpdateResult addNewArtifact(String newArtifactName, int copiedArtifactRefId, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Get all of the user group
     */ 
    public ApplicationUserGroupList getAllUserGroups(ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Determine if the applicatiojn controls are read only
     */ 
    public boolean isReadOnlyControlList(ApplicationFramework application, int appTypeRefId, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Get the products the user has access to
     */ 
    public ReferenceDisplayList getProductList(ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Add the new template field to an artifact form
     */ 
    public void addNewArtifactField(ReferenceDisplay artifact, ReferenceDisplayList fields, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Export
     */ 
    public AppControlTemplateList exportAppControlTemplate(ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Import
     */ 
    public int importAppControlTemplate(AppControlTemplateList list, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


}
