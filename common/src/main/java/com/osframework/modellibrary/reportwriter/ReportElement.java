package com.osframework.modellibrary.reportwriter;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Vector;

import com.osframework.framework.logging.Debug;
import com.osframework.framework.utility.FileProcess;

public class ReportElement implements Serializable {
	
	protected ReportElementList children = new ReportElementList();
	/*
	 * Defines this report element will print
	 */
	private boolean visible = true;
	/**
	 * Element attributes
	 */
	private ReportAttributeList attributes = new ReportAttributeList();
	/**
	 * Defines indentation
	 */
	private int level = 0;
	/*
	 * Element name
	 */
	private String name;
	private String content;
	
	public ReportElement() {
		
	}
	
	public ReportElement(String name, String content) {
		this.name = name;
		this.content = content;
	}
	
	public static ReportElement get(String name, String content) {
		return new ReportElement(name, content);
	}
	
	public ReportElement(String name, String content, ReportAttributeList attributes) {
		this.name = name;
		this.content = content;
		this.attributes = attributes;
	}
	
	public static ReportElement get(String name, String content, ReportAttributeList attributes) {
		return new ReportElement(name, content, attributes);
	}
	
	public ReportElement(String name,ReportAttributeList list) {
		this.name = name;
		this.attributes = list;
	}
	
	public ReportElement(String name,ReportAttribute attribute) {
		this.name = name;
		this.attributes = new ReportAttributeList();
		this.attributes.add(attribute);
	}
	
	public ReportElement(String name, ReportAttribute attribute, ReportAttribute attribute2) {
		this.name = name;
		this.attributes = new ReportAttributeList();
		this.attributes.add(attribute);
		this.attributes.add(attribute2);
	}
	
	public ReportElement(String name,Vector list) {
		this.name = name;
		Enumeration e1 = list.elements();
		while (e1.hasMoreElements()) {
			children.add((ReportElement) e1.nextElement());
		}
	}
	
	public ReportElement(String name, ReportAttributeList attributes, ReportElementList list) {
		this.name = name;
		this.attributes = attributes;
		Enumeration e1 = list.elements();
		while (e1.hasMoreElements()) {
			ReportElement r = (ReportElement) e1.nextElement();
			children.addReportElement(r);
		}
	}
	
	public String toString() {
		try {
			StringBuffer sb = new StringBuffer(64);
			if (visible && name != null) {
				
				sb.append(FileProcess.nl() + indent(level) + "<" + name + attributes);
				if (content == null && children.size()==0) {
					sb.append("/>");
				} else {
					if (content != null && children.size() > 0) {
						Debug.LogError(this, name + " Warning report element has both content and children " + name + " " + content);
					}
					sb.append(">");
					if (content != null) {
						sb.append(content);
					}
					Enumeration e1 = children.elements();
					while (e1.hasMoreElements()) {
						ReportElement reportElement = (ReportElement) e1.nextElement();
						reportElement.setLevel(level+1);
						sb.append(reportElement.toString());
					}
					if (content == null) {
						sb.append(FileProcess.nl() + indent(level));
					}
					sb.append("</" + name + ">");
				}
			}
			return sb.toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}
	
	private static String indent(int level) {
		if (level ==0) {
			return "";
		} else if (level == 1) {
			return "   ";
		} else if (level == 2) {
			return "      ";
		} else {
			String dent = "   ";
			String s = "";
			for (int i=0;i < level; i++) {
				s += dent;
			}
			return s;
		}
	}
	
	public void addChildren(ReportElementList list) {
		Enumeration e1 = list.elements();
		while (e1.hasMoreElements()) {
			ReportElement r = (ReportElement) e1.nextElement();
			children.addReportElement(r);
		}
	}

	/*
	 * Defines if this report element will print
	 */
	public boolean isVisible() {
		return visible;
	}

	/*
	 * Defines this report element will print
	 */
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public ReportAttributeList getAttributes() {
		return attributes;
	}

	public void setAttributes(ReportAttributeList attributes) {
		this.attributes = attributes;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	
}
