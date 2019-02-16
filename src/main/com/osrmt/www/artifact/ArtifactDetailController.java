package com.osrmt.www.artifact;
import java.awt.*;
import java.io.UnsupportedEncodingException;
import java.util.*;

import com.osframework.modellibrary.common.*;
import com.osframework.modellibrary.reference.security.*;
import com.osframework.appclient.services.*;
import com.osframework.appclient.ui.common.ControlPanel;
import com.osframework.appclient.ui.common.FormControl;
import com.osframework.appclient.ui.common.GUI;
import com.osframework.appclient.ui.common.IHtmlApplicationControl;
import com.osframework.appclient.ui.common.UIFormLayout;
import com.osframework.appclient.ui.controls.ICustomBind;
import com.osframework.appclient.ui.controls.IGetCombo;
import com.osframework.appclient.ui.controls.IGetDocument;
import com.osframework.appclient.ui.controls.UILabel;
import com.osframework.appclient.ui.controls.UIValueList;
import com.osframework.appclient.ui.listeners.*;
import com.osframework.modellibrary.reference.common.*;
import com.osframework.modellibrary.reference.group.*;
import com.osframework.framework.logging.*;
import com.osframework.framework.utility.*;
import com.osrmt.modellibrary.reference.group.ApplicationGroup;
import com.osrmt.modellibrary.reqmanager.*;
import com.osrmt.www.common.*;
import com.osrmt.www.services.*;

import javax.servlet.http.*;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.text.*;
import javax.swing.text.html.HTMLEditorKit;

import org.apache.struts.*;
import org.apache.struts.action.*;

public class ArtifactDetailController {

	public ArtifactDetailController() {
		super();
	}
	
	public static String buildControls(ArtifactModel artifact, IControlModel model, ServiceCall call, boolean readOnly) throws InvalidUserLoginException, InvalidUserPasswordException, Exception{
		String hiddenInput = "<input name=\"artifactid\" value=\"" + artifact.getArtifactId() + "\" type=\"hidden\"/>";
		ApplicationControlList controls = LocalSecurityServices.getAppControlsByUser(artifact.getArtifactRefId(), 
				ApplicationFramework.get(ApplicationGroup.HTMLARTIFACTFORM), call);

		return HtmlControlPanel.buildControls(hiddenInput, controls, model, call, readOnly);
	}
}

