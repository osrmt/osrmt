package com.osrmt.modellibrary.reqmanager;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import com.osframework.modellibrary.common.ResultList;

public class ArtifactMatrix extends ResultList {
	
	private static final long serialVersionUID = 1L;

	int rowCount = 0;
	int columnCount = 0;
	private Class[] classes;
	private Vector columnNames;
	private String[][] values;
	private Hashtable dependentTree = null;
	private ArtifactList listFrom;
	private ArtifactList listTo;
	
	public ArtifactMatrix(ArtifactList fromList, ArtifactList toList, RequirementTreeList tree) {
		this.listFrom = fromList;
		this.listTo = toList;
		dependentTree = new Hashtable(tree.size() * 2);
		Enumeration e1 = tree.elements();
		while (e1.hasMoreElements()) {
			RequirementTreeModel rtm = (RequirementTreeModel) e1.nextElement();
			String key = getKey(rtm.getParentId(), rtm.getChildId());
			dependentTree.put(key,key);
		}
	}
	
	public void buildMatrix() {
		init(listFrom.getRowCount(),listTo.getRowCount());
		setColumnNames(listTo, listFrom);
		for (int i=0; i<listFrom.size(); i++) {
			ArtifactModel m = (ArtifactModel) listFrom.getValueAt(i);
			for (int j=0; j<listTo.size(); j++) {
				ArtifactModel n = (ArtifactModel) listTo.getValueAt(j);
				if (isDependent(m.getArtifactId(),n.getArtifactId())) {
					setValueAt(i,j,"X");
				} else {
					setValueAt(i,j," ");
				}
			}
		}
	}
	
	public ArtifactList getTraced() {
		ArtifactList traced = new ArtifactList();
		for (int i=0; i<listFrom.size(); i++) {
			ArtifactModel m = (ArtifactModel) listFrom.getValueAt(i);
			for (int j=0; j<listTo.size(); j++) {
				ArtifactModel n = (ArtifactModel) listTo.getValueAt(j);
				if (isDependent(m.getArtifactId(),n.getArtifactId())) {
					traced.add(m);
					break;
				}
			}
		}
		return traced;
	}
	
	public ArtifactList getNotTraced() {
		ArtifactList notTraced = new ArtifactList();
		for (int i=0; i<listFrom.size(); i++) {
			ArtifactModel m = (ArtifactModel) listFrom.getValueAt(i);
			boolean traced = false;
			for (int j=0; j<listTo.size(); j++) {
				ArtifactModel n = (ArtifactModel) listTo.getValueAt(j);
				if (isDependent(m.getArtifactId(),n.getArtifactId())) {
					traced = true;
					break;
				}
			}
			if (!traced) {
				notTraced.add(m);
			}
		}
		return notTraced;
	}
	
	public boolean isDependent(int parentId, int childId) {
		String key = getKey(parentId, childId);
		return dependentTree.containsKey(key);
	}
	
	public static String getKey (int parentId, int childId) {
		StringBuffer sb = new StringBuffer(24);
		sb.append(parentId);
		sb.append(":");
		sb.append(childId);
		return sb.toString();
	}



	private void init(int rows, int columns) {
		rowCount = rows;
		columnCount = columns+1;
		classes = new Class[columnCount];
		columnNames = new Vector(columnCount);
		for (int i=0; i<columnCount; i++) {
			classes[i] = String.class;
			columnNames.add(" ");
		}
		values = new String[rows][columnCount];
	}

	public Class[] getClasses() {
		return classes;
	}

	public Vector getColumnNames() {
		return columnNames;
	}

	public void setColumnNames(ArtifactList list, ArtifactList rowList) {
		columnNames.set(0,"-->");
		for (int i=0; i<list.size(); i++) {
			ArtifactModel m = (ArtifactModel) list.getValueAt(i);
			columnNames.setElementAt(m.getArtifactName(), i+1);
		}
		for (int i=0; i<rowList.size(); i++) {
			ArtifactModel m = (ArtifactModel) rowList.getValueAt(i);
			values[i][0] = m.getArtifactName();
		}
	}

	public Comparable getValueAt(int rowIndex) {
		return new ComparableString(values[rowIndex].toString());
	}

	public int getRowCount() {
		return rowCount;
	}

	public int getColumnCount() {
		return columnCount;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		return values[rowIndex][columnIndex];
	}

	public void setValueAt(int rowIndex, int columnIndex, String value) {
		values[rowIndex][columnIndex+1] = value;
	}

	public void setModelValueAt(Object aValue, int rowIndex, int columnIndex) {
		setValueAt(rowIndex, columnIndex, aValue.toString());
		
	}
	
	public void sort() {
	}
}
