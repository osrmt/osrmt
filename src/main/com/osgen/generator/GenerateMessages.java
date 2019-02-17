package generator;

import java.util.Enumeration;
import java.io.*;
import utilities.FileSystemUtil;

import models.*;

public class GenerateMessages {
	
	MainGenerator maingen;

	public GenerateMessages(MainGenerator m) {
		this.maingen = m;
	}
	
	public void CreateProperties() throws IOException {

		Enumeration e2 = maingen.getMessageList().languages.elements();
		while (e2.hasMoreElements()) {
			String language = (String) e2.nextElement();
			CreatePropertyFile(language);
		}
	}
	
	private void CreatePropertyFile(String language) throws IOException {
		String propDir = maingen.props.getProperty(MainGenerator.propMSGBUNDLEDIR);
		FileSystemUtil.CreateDirectory(propDir);
		FileSystemUtil.CreateFile(propDir, "MessagesBundle_" + language + ".properties", getProperties(language));
	}

	private String getProperties(String language) {
		StringBuffer sb = new StringBuffer(1024*1024*8);
		Enumeration e1 = maingen.getMessageList().elements();
		while (e1.hasMoreElements()) {
			MessageModel mm = (MessageModel) e1.nextElement();
			sb.append(MessageModel.key(mm.getMessage()));
			sb.append(" = ");
			sb.append(mm.translate(language, mm.getMessage()));
			sb.append("\r\n");
		}
		return sb.toString();
	}
	
	
}
