/*
 * SendMailAction.java
 *
 * Created on March 13, 2007, 11:34 AM
 */
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


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForward;

import java.util.*;

import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import com.osrmt.www.common.BaseAction;

import org.apache.commons.beanutils.PropertyUtils;
import com.osframework.framework.logging.*;
import com.sun.mail.smtp.SMTPTransport;
/**
 *
 * @author Jacek Kowalczyk kowalczykj@tt.com.pl
 * @version
 */

public class SendMailAction extends BaseAction {
    
    /* forward name="success" path="" */
    private final static String MAILSEND = "mailSend";
    
    /**
     * This is the action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param form The optional ActionForm bean for this request.
     * @param request The HTTP Request we are processing.
     * @param response The HTTP Response we are processing.
     * @throws java.lang.Exception
     * @return
     */
    public String sendMail(String host, String from,String to,String passwd, String filename, String subject, String body, boolean auth, boolean ssl)
    //throws javax.mail.MessagingException,javax.mail.AuthenticationFailedException, javax.mail.SendFailedException
    {
        try {
            
            Properties props = new Properties();
            props.put("mail.smtp.host", host);
            if (auth){
                props.put("mail.smtp.auth", "true");
            }
            Session session = Session.getInstance(props, null);
            session.setDebug(true);
            
            // create a message
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(from));
            
            InternetAddress[] address = {new InternetAddress(to)};
            //InternetAddress[] address = {new InternetAddress(emailAddressTo)};
            msg.setRecipients(Message.RecipientType.TO, address);
            msg.setSubject(subject);
            msg.setSentDate(new Date());
            
            // create and fill the first message part
            MimeBodyPart mbp1 = new MimeBodyPart();
            mbp1.setText(body);
            
            // create and fill the second message part
            MimeBodyPart mbp2 = new MimeBodyPart();
            // Use setText(text, charset), to show it off !
            mbp2.setText(body, "us-ascii");
            
            // attach the file to the message
            
            FileDataSource fds = new FileDataSource(filename);
            mbp2.setDataHandler(new DataHandler(fds));
            mbp2.setFileName(fds.getName());
            
            // create the Multipart and its parts to it
            Multipart mp = new MimeMultipart();
            mp.addBodyPart(mbp1);
            mp.addBodyPart(mbp2);
            
            // add the Multipart to the message
            msg.setContent(mp);
            
            SMTPTransport t = 	(SMTPTransport)session.getTransport(ssl ? "smtps" : "smtp");
            try{
                if (auth){
                    t.connect(host,from, passwd);
                } else{
                    t.connect();
                }
                t.sendMessage(msg, msg.getAllRecipients());
            }catch (javax.mail.SendFailedException sendexc){
                t.close();
                return sendexc.toString();
            }catch (Exception exc){
                t.close();
                return exc.toString();
            }
            
            
        }catch (javax.mail.internet.AddressException addex){
            addex.printStackTrace();
            Exception ex = null;
            if ((ex = addex.getNextException()) != null) {
                ex.printStackTrace();
            }
            Debug.LogException(this, addex);
            //throw authexc;
            return addex.toString();//new String("javax.mail.AuthenticationFailedException");
        } catch (javax.mail.AuthenticationFailedException authexc){
            authexc.printStackTrace();
            Exception ex = null;
            if ((ex = authexc.getNextException()) != null) {
                ex.printStackTrace();
            }
            Debug.LogException(this, authexc);
            //throw authexc;
            return authexc.toString();//new String("javax.mail.AuthenticationFailedException");
        } catch (javax.mail.SendFailedException sendexc){
            sendexc.printStackTrace();
            //throw sendexc;
            return sendexc.toString();//new String("javax.mail.SendFailedException");
        } catch (MessagingException mex) {
            
            mex.printStackTrace();
            Exception ex = null;
            if ((ex = mex.getNextException()) != null) {
                ex.printStackTrace();
            }
            
            Debug.LogException(this, mex);
//            throw mex;
            return mex.toString();//new String("MessagingException");
        } catch(Exception exc){
            exc.printStackTrace();
            return exc.toString();//
        }
        
        return new String("success");
    }///sendMail
    
    
    public ActionForward execute(ActionMapping mapping, ActionForm  form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String filename2 = ""+(String) PropertyUtils.getSimpleProperty(form, "filename2");
        String subject = ""+(String) PropertyUtils.getSimpleProperty(form, "subject");
        String emailAddressTo = ""+(String) PropertyUtils.getSimpleProperty(form, "emailAddressTo");
        String messageBody = ""+(String) PropertyUtils.getSimpleProperty(form, "messageBody");
        
        
        String host = "";//+(String) PropertyUtils.getSimpleProperty(form, "host");
        String emailAddressFrom = "";//+(String) PropertyUtils.getSimpleProperty(form, "emailAddressFrom");
        String passwd = "";//+(String) PropertyUtils.getSimpleProperty(form, "passwd");
        
        
        boolean auth = true;
        boolean ssl = false;
        String authString = "";
        String sslString = "";
        
        java.util.HashMap<String, String> propertiesMap = com.osframework.framework.utility.ReadProperties.getParameters("sendMail.properties");
        
        if ((propertiesMap==null) || (propertiesMap.size()==0)){
            System.out.println("null or 0");
            ActionErrors errors = new ActionErrors();
            errors.add("exception", new ActionMessage("mail.read.properties.failed"));
            addErrors(request,errors);
            
            return mapping.findForward("fileNotFound");
        }
        if (propertiesMap.size()>0){
            System.out.println("size >0");
            host = (propertiesMap.get("host")==null)?"smtp.solutioncenter.pl":propertiesMap.get("host");
            emailAddressFrom = (propertiesMap.get("emailAddressFrom")==null)?"smtp.solutioncenter.pl":propertiesMap.get("emailAddressFrom");
            passwd = (propertiesMap.get("passwd")==null)?"noPassword":propertiesMap.get("passwd");
            authString = (propertiesMap.get("auth")==null)?"true":propertiesMap.get("auth");
            auth = java.lang.Boolean.parseBoolean(authString);
            sslString = (propertiesMap.get("ssl")==null)?"false":propertiesMap.get("ssl");
            ssl = java.lang.Boolean.parseBoolean(sslString);
        }
        
        emailAddressTo = emailAddressTo.trim();
        emailAddressTo = emailAddressTo.replaceAll(" ","");
        String [] addresses = emailAddressTo.split(";");
        java.util.HashMap<String, String> results = new java.util.HashMap<String, String>();
        boolean addExc= false;
        for (int i=0;i<addresses.length;i++){
            //if (addresses[i].contains("@"))
            {
                
                
                try{
                    InternetAddress inetAddress = new InternetAddress(addresses[i]);
                    inetAddress.validate();
                } catch (javax.mail.internet.AddressException addex){
                    addex.printStackTrace();
                    addExc = true;
                    ActionErrors errors = new ActionErrors();
                    errors.add("exception", new ActionMessage("mail.address.failed",addresses[i]));
                    addErrors(request,errors);
                    continue;
                }
                //try{
                String res = sendMail(host,emailAddressFrom,addresses[i],passwd, filename2,subject, messageBody, auth,ssl );
                results.put(addresses[i],res);
                
            }
        }
        
        java.util.Set set1 = results.keySet();
        Object [] keys = set1.toArray();
        boolean authExc= false;
        boolean sendExc= false;
        boolean mesExc= false;
        boolean otherExc= false;
        for (Object address: keys){
            String add = address.toString();
            if (results.get(add).equalsIgnoreCase("success")){
                ///ok
                ActionErrors errors = new ActionErrors();
                errors.add("exception", new ActionMessage("mail.success",add));
                
                addErrors(request,errors);
            } else if(results.get(add).contains("AddressException")){
                addExc = true;
                ActionErrors errors = new ActionErrors();
                errors.add("exception", new ActionMessage("mail.address.failed",add));
                addErrors(request,errors);
            } else if(results.get(add).contains("AuthenticationFailedException")){
                authExc = true;
                ActionErrors errors = new ActionErrors();
                errors.add("exception", new ActionMessage("mail.auth.failed",emailAddressFrom));
                addErrors(request,errors);
            } else if(results.get(add).contains("SendFailedException")){
                sendExc = true;
                ActionErrors errors = new ActionErrors();
                errors.add("exception", new ActionMessage("mail.send.failed", add));
                //errors.add("exception", new ActionMessage(add));
                addErrors(request,errors);
            } else if(results.get(add).contains("MessagingException")){
                mesExc = true;
                ActionErrors errors = new ActionErrors();
                errors.add("exception", new ActionMessage("mail.messaging.failed",host,emailAddressFrom,add));
                addErrors(request,errors);
            } else {
                otherExc =true;
                ActionErrors errors = new ActionErrors();
                errors.add("exception", new ActionMessage("mail.failed"));
                addErrors(request,errors);
            }
        }
        
        
        if (addExc||authExc||sendExc||mesExc||otherExc){
            return mapping.findForward("Errors");
        }
        return mapping.findForward(MAILSEND);
        
    }
}
