package com.osrmt.www.services;
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
import org.apache.struts.*;
import org.apache.struts.action.*;
import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import org.jboss.security.SimplePrincipal;
import org.jboss.security.auth.callback.SecurityAssociationHandler;
import java.io.*;
import java.util.*;
import java.security.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.osframework.framework.locale.*;
import com.osrmt.modellibrary.reqmanager.*;


public class BaseService {

	public BaseService() {
		super();
	}

	public static void authenticateContainer() {
        SecurityAssociationHandler handler = new SecurityAssociationHandler();
        Principal user = new SimplePrincipal("200");
        handler.setSecurityInfo(user, "j2ee".toCharArray());
        LoginContext loginContext = null;
		try {
			loginContext = new LoginContext("client-login", handler);
	        loginContext.login();
	        Subject subject = loginContext.getSubject();
	        Set principals = subject.getPrincipals();
	        principals.add(user);

		} catch (LoginException e) {
			Debug.LogException("BaseService", e);
			
		}
	}
	
	public static void convertUTF(ArtifactModel artifact) {
		artifact.setArtifactName(ConvertUtil.toUTF8(artifact.getArtifactName()));
		artifact.setDescription(ConvertUtil.toUTF8(artifact.getDescription()));
		artifact.setRationale(ConvertUtil.toUTF8(artifact.getRationale()));
		artifact.setOrigin(ConvertUtil.toUTF8(artifact.getOrigin()));
		artifact.setGoal(ConvertUtil.toUTF8(artifact.getGoal()));
		artifact.setContext(ConvertUtil.toUTF8(artifact.getContext()));
		artifact.setPrecondition(ConvertUtil.toUTF8(artifact.getPrecondition()));
		artifact.setPostcondition(ConvertUtil.toUTF8(artifact.getPostcondition()));
		artifact.setSummary(ConvertUtil.toUTF8(artifact.getSummary()));
		artifact.setExternalReferences(ConvertUtil.toUTF8(artifact.getExternalReferences()));
		artifact.setCustomText1(ConvertUtil.toUTF8(artifact.getCustomText1()));
		artifact.setCustomText2(ConvertUtil.toUTF8(artifact.getCustomText2()));
		artifact.setCustomText3(ConvertUtil.toUTF8(artifact.getCustomText3()));
		artifact.setCustomText4(ConvertUtil.toUTF8(artifact.getCustomText4()));
		artifact.setCustomMemo1(ConvertUtil.toUTF8(artifact.getCustomMemo1()));
		artifact.setCustomMemo2(ConvertUtil.toUTF8(artifact.getCustomMemo2()));
	}
}

