package com.osframework.appclient.ui.controls;

import com.osframework.appclient.services.ReferenceServices;
import com.osframework.appclient.ui.components.MultiColumnList;
import com.osframework.framework.logging.Debug;
import com.osframework.modellibrary.common.ResultList;
import com.osframework.modellibrary.reference.group.SystemMessageFramework;
import com.osframework.modellibrary.system.RecordParameterValueModel;

public class UIValueList extends MultiColumnList {

	public void setTableModel(ResultList list, int colMinWidth) {
		list.sort();
		if (list != null && list.getRowCount() > 0) {
			Object o = list.getValueAt(0);
			if (!RecordParameterValueModel.validValue(o)) {
				Debug.LogError(this,ReferenceServices.getMsg(SystemMessageFramework.VALUENOTFOUNDINLIST) + ": " + o.getClass().getName());
				return;
			}
		}
		super.setTableModel(list, colMinWidth);
	}
	
}
