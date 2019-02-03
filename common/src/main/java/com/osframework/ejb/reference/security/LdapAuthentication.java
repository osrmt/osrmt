package com.osframework.ejb.reference.security;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

import com.osframework.appclient.services.SecurityServices;
import com.osframework.framework.logging.Debug;
import com.osframework.modellibrary.reference.group.ApplicationFramework;
import com.osframework.modellibrary.reference.group.ApplicationSettingFramework;
import com.osframework.modellibrary.reference.security.ApplicationSettingList;
import com.osframework.modellibrary.reference.security.InvalidUserLoginException;

public class LdapAuthentication {
	
	private String initialContext = "com.sun.jndi.ldap.LdapCtxFactory";
	private String authenticationMethod = "simple";
	private String providerUrl = "ldap://ldap:636";

	public LdapAuthentication() {
		super();
		init();
	}

	private void init() {
		try {
			ApplicationSettingList asl = SecurityServices.getSetting(ApplicationFramework.get(0),
					ApplicationSettingFramework.LDAP_INITIAL_CONTEXT_FACTORY);
			if (asl.size() > 0) this.initialContext = asl.getFirst().getValueString();
			// authentication method
			asl = SecurityServices.getSetting(ApplicationFramework.get(0),
					ApplicationSettingFramework.LDAP_SECURITY_AUTHENTICATION);
			if (asl.size() > 0) this.authenticationMethod = asl.getFirst().getValueString();
			// ldap server
			asl = SecurityServices.getSetting(ApplicationFramework.get(0),
					ApplicationSettingFramework.LDAP_PROVIDER_URL);
			if (asl.size() > 0) this.providerUrl = asl.getFirst().getValueString();
			
		} catch (Exception ex) {
			Debug.LogException("DataFormatSetting", ex);
		}
	}
	
	public void authenticate (String username, String password) throws InvalidUserLoginException {
        DirContext ctx = null;        
        try {
            ctx = getContext(username, password);
        } catch (NamingException ne) {
            throw new InvalidUserLoginException(ne.toString());
        }  finally {
            if (ctx != null) {
                try {
                    ctx.close();
                } catch (NamingException ne) {
                }
            }
        }
    }
	
    protected DirContext getContext(String username, String password) throws NamingException {
        Hashtable env = new Hashtable();
          
        env.put(Context.INITIAL_CONTEXT_FACTORY, initialContext); 
        env.put(Context.PROVIDER_URL, providerUrl); 
        env.put(Context.SECURITY_AUTHENTICATION,authenticationMethod);
        env.put(Context.SECURITY_PRINCIPAL, username); 
        env.put(Context.SECURITY_CREDENTIALS, password);
        
        DirContext ctx = new InitialDirContext(env);  
        return ctx;
		
	}
}

