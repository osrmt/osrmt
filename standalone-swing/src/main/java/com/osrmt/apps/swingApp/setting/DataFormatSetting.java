package com.osrmt.apps.swingApp.setting;

import com.osframework.appclient.services.SecurityServices;
import com.osframework.framework.logging.Debug;
import com.osframework.framework.utility.CalendarUtility;
import com.osframework.modellibrary.reference.group.ApplicationFramework;
import com.osframework.modellibrary.reference.group.ApplicationSettingFramework;
import com.osframework.modellibrary.reference.security.ApplicationSettingList;
import com.osrmt.modellibrary.reqmanager.ArtifactModel;

public class DataFormatSetting extends BaseSetting {

	public static void initialize() {
		initializeDateFormat();
		initializeArtifactDisplay();
	}
	
	private static void initializeDateFormat() {
		try {
			ApplicationSettingList asl = SecurityServices.getSetting(ApplicationFramework.get(0),
					ApplicationSettingFramework.LONGDATETIMEFORMAT);
			if (asl.size() > 0) {
				CalendarUtility.setLongDateTimeFormat(asl.getFirst().getValueString());
			}
			asl = SecurityServices.getSetting(ApplicationFramework.get(0),
					ApplicationSettingFramework.SHORTDATEFORMAT);
			if (asl.size() > 0) {
				CalendarUtility.setShortDateFormat(asl.getFirst().getValueString());
			}
		} catch (Exception ex) {
			Debug.LogException("DataFormatSetting", ex);
		}
	}
	
	private static void initializeArtifactDisplay() {
		try {
			ApplicationSettingList asl = SecurityServices.getSetting(ApplicationFramework.get(0),
					ApplicationSettingFramework.DISPLAY_ARTIFACT_LONG_NAME);
			if (asl.size() > 0) {
				ArtifactModel.setUseLongName(asl.getFirst().getValueInt()==1);
			}
		} catch (Exception ex) {
			Debug.LogException("DataFormatSetting", ex);
		}
	}
}
