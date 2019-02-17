package com.osframework.appclient.ui.common;
import java.util.*;
import com.osframework.modellibrary.common.*;
import com.osframework.modellibrary.reference.security.*;
import com.osframework.appclient.services.*;
import com.osframework.appclient.ui.listeners.*;
import com.osframework.modellibrary.reference.common.*;
import com.osframework.modellibrary.reference.group.*;
import com.osframework.framework.logging.*;
import com.osframework.framework.utility.*;
import javax.servlet.http.*;

public interface IHtmlApplicationControl {
	
	public int getModelColumnRefId();
	
	public int getHeight();
	
	public int getWidth();
	
	public String getLabel();
	
	public boolean isLocked();
	
	public boolean isDisabled();
	
	public String getHtmlScript();
	

}

