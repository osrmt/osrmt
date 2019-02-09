package com.osrmt.appclient.setup;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

import javax.swing.JOptionPane;

import com.osframework.appclient.services.*;
import com.osframework.appclient.services.SystemServices;
import com.osframework.datalibrary.common.*;
import com.osframework.framework.utility.FileSystemUtil;
import com.osframework.framework.utility.SerializeUtility;
import com.osframework.modellibrary.common.*;
import com.osframework.modellibrary.reference.common.*;
import com.osframework.modellibrary.reference.security.*;
import com.osframework.modellibrary.reference.group.ReferenceModificationFramework;

public class LanguageManager extends ConsoleManager {


	
	public void exportLanguageFile() {
		try {
			doption("Enter the file name [language.csv]: ");
			String filename = getStringData("language.csv");
			File file = new File(filename);
			if (file.exists()) {
				d("Do not specify an existing file");
			} else {
				exportLanguage(file);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.err.flush();
		}
	}
	
	public void importLanguageFile() {
		try {
			doption("Enter the file name [language.csv]: ");
			String filename = getStringData("language.csv");
			File file = new File(filename);
			if (!file.exists()) {
				d(filename + " does not exist");
			} else {
				importLanguage(file);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.err.flush();
		}
	}
	
	private void writeReference(BufferedWriter writer) throws IOException {
		ReferenceGroupList groups = ReferenceServices.exportReferenceGroup();
		Enumeration e1 = groups.elements();
		while (e1.hasMoreElements()) {
			ReferenceGroupModel group = (ReferenceGroupModel) e1.nextElement();
			if (group.getModificationRefId() != ReferenceModificationFramework.NOACCESS) {
				ReferenceList refs = ReferenceServices.getActiveReferenceList(group.getReferenceGroup());
				Enumeration e2 = refs.elements();
				while (e2.hasMoreElements()) {
					ReferenceModel rm = (ReferenceModel) e2.nextElement();
					writer.write(formatLine(rm));
				}
			}
		}
	}
	
	private void writeApplicationControls(BufferedWriter writer) throws IOException {
		ApplicationControlList controls = SecurityServices.exportApplicationControl();
		Enumeration e1 = controls.elements();
		while (e1.hasMoreElements()) {
			ApplicationControlModel acm = (ApplicationControlModel) e1.nextElement();
			writer.write(formatLine(acm));
		}
	}
	
	public void exportLanguage(File file) throws IOException {
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(file));
			writeReference(writer);
			writeApplicationControls(writer);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}
	
	private String delim = ",";
	
	private String formatLine(ReferenceModel rm) {
		StringBuffer sb = new StringBuffer(1024);
		sb.append("REFERENCE");
		sb.append(delim);
		sb.append(String.valueOf(rm.getRefId()));
		sb.append(delim);
		sb.append(prepare(rm.getDisplay()));
		sb.append(delim);
		sb.append(prepare(rm.getDescription()));
		sb.append(com.osframework.framework.utility.FileProcess.nl());
		return sb.toString();
	}
	
	private String formatLine(ApplicationControlModel rm) {
		StringBuffer sb = new StringBuffer(1024);
		sb.append("APPLICATIONCONTROL");
		sb.append(delim);
		sb.append(String.valueOf(rm.getApplicationControlId()));
		sb.append(delim);
		sb.append(prepare(rm.getControlText()));
		sb.append(delim);
		sb.append(prepare(rm.getControlDescription()));
		sb.append(com.osframework.framework.utility.FileProcess.nl());
		return sb.toString();
	}
	
	private String prepare(String s) {
		if (s == null) {
			return "";
		} else if (s.indexOf(delim)>-1){
			return s.replace(delim, "~");
		} else {
			return s;
		}
	}
	
	private boolean isNotInteger(String s) {
		try {
			int i = Integer.parseInt(s);
			return false;
		} catch (Exception ex) {
			return true;
		}
	}
	
	private void importList(ReferenceList list) throws Exception {
		doption("Import " + list.size() + " updates to REFERENCE [N]: ");
		if (! getStringData("Y").equals("Y")) {
			return;
		}
		Enumeration e1 = list.elements();
		while (e1.hasMoreElements()) {
			ReferenceModel rm = (ReferenceModel) e1.nextElement();
			ReferenceModel current = ReferenceServices.getReference(rm.getRefId());
			rm.copyModifiedTo(current);
			ReferenceServices.updateReference(current);
		}
	}
	
	private void importList(ApplicationControlList list) throws Exception {
		doption("Import " + list.size() + " updates to APPLICATIONCONTROL [Y]: ");
		if (! getStringData("Y").equals("Y")) {
			return;
		}
		Enumeration e1 = list.elements();
		while (e1.hasMoreElements()) {
			ApplicationControlModel rm = (ApplicationControlModel) e1.nextElement();
			ApplicationControlModel current = SecurityServices.getApplicationControl(rm.getApplicationControlId());
			rm.copyModifiedTo(current);
			SecurityServices.UpdateApplicationControl(current);
		}
	}
	
	public void importLanguage(File file) throws Exception {
		BufferedReader reader = null;
		try {
			ReferenceList list = new ReferenceList();
			ApplicationControlList aclist = new ApplicationControlList();
			reader = new BufferedReader(new FileReader(file));
			int line = 0;
			String s = reader.readLine();
			while (s != null) {
				line++;
				StringTokenizer st = new StringTokenizer(s, delim);
				if (st.countTokens() < 3) {
					System.err.println("line " + line + " skipped not enough delimeters");
					s = reader.readLine();
					continue;
				}
				String tableName = st.nextToken();
				String refId = st.nextToken();
				String display = st.nextToken();
				String description = null;
				if (st.countTokens() >3) {
					description = st.nextToken();
				}
				if (isNotInteger(refId)) {
					System.err.println("line " + line + " skipped, second token is not an integer");
					s = reader.readLine();
					continue;
				}
				if (tableName.equalsIgnoreCase("REFERENCE")) {
					ReferenceModel rm = new ReferenceModel();
					rm.resetModified();
					rm.setRefId(Integer.parseInt(refId));
					rm.setDisplay(display);
					rm.setDescription(description);
					list.add(rm);
				} else if (tableName.equalsIgnoreCase("APPLICATIONCONTROL")) {
					ApplicationControlModel acm = new ApplicationControlModel();
					acm.resetModified();
					acm.setApplicationControlId(Integer.parseInt(refId));
					acm.setControlText(display);
					acm.setControlDescription(description);
					aclist.add(acm);
				}
					
				s = reader.readLine();
			}
			importList(list);
			importList(aclist);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (reader != null) {
				reader.close();
			}
		}

	}
	
}

