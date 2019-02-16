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

package com.osrmt.www.reports;

import com.osframework.appclient.services.ReferenceServices;
import com.osframework.framework.logging.Debug;
import com.osframework.modellibrary.reference.common.ReferenceList;
import com.osframework.modellibrary.reference.common.ReferenceModel;
import java.util.ArrayList;
import org.apache.struts.util.LabelValueBean;

/**
 *
 * @author Leszek Zborowski
 */
public class Parameter {
    
    private String name;
    private boolean list = false;
    private String value;
    private ArrayList dropDownValues;
    
    private int artifactRefId;
    
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public boolean isList() {
        return list;
    }
    
    public void setList(boolean list) {
        this.list = list;
    }
    
    public String getValue() {
        return value;
    }
    
    public void setValue(String value) {
        this.value = value;
    }
    
    public ArrayList getDropDownValues() {
        return dropDownValues;
    }
    
    public void setDropDownValues(ArrayList dropDownValues) {
        this.dropDownValues = dropDownValues;
    }
    
    public void setArtifactId(String sql) {
        String sub1 = new String();
        if (sql.contains("ARTIFACT_REF_ID=")){
            try{
                String sub = sql.substring(sql.indexOf("ARTIFACT_REF_ID=")+16);
                sub1 = sub.substring(0, sub.indexOf(" "));
                this.artifactRefId = Integer.parseInt(sub1.trim());
            } catch(Exception ex) {
                Debug.LogException(this, ex);
            }
        } else this.artifactRefId = -1;
        this.dropDownValues = manageGroups(this.name);
    }
    
    private ArrayList manageGroups(String groupName){
        try {
            ReferenceList list = ReferenceServices.getActiveReferenceList(this.artifactRefId, groupName);
            ArrayList fields = new ArrayList();
            if(list.size()>0) {
                for (int i = 0; i < list.size(); i++) {
                    ReferenceModel mod = list.elementAt(i);
                    fields.add(mod.getDisplaySequence(), mod.getDisplay());
                }
                this.list=true;
            }
            return fields;
        } catch(Exception ex) {
            Debug.LogException(this, ex);
            return null;
        }
    }
}
