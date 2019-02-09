package com.osframework.appclient.ui.components;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeListener;
import java.util.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.table.*;

import com.jgoodies.forms.factories.Borders;
import com.osframework.appclient.services.SecurityServices;
import com.osframework.appclient.ui.common.*;
import com.osframework.appclient.ui.listeners.DoubleClickListener;
import com.osframework.modellibrary.common.*;
import com.osframework.modellibrary.reference.security.ApplicationControlList;

/**
 * The MultiColumnList provides consisten functionality to a 
 * single or multi column list of values.
 * 
 * Example usage
 * 		MultiColumnList mclist = new MultiColumnList();
 * 		mclist.setTableModel(patients, 
 * 			PatientSearchModel.getColumnNames(),
 * 			PatientSearchModel.getColumnClasses(),
 * 			PatientSearchModel.MIN_COL_WIDTH);
 * 		mclist.addListSelectionListener(new ListSelectionListener() {
 * 			public void valueChanged(ListSelectionEvent e) {
 * 				if (!e.getValueIsAdjusting()) {	
 * 					if (mclist.isRowSelected()) {
 * 						YourModel m = (YourModel) mclist.getSelectedRow();						
 * 					}
 * 				}
 * 			}
 * 		});
 *
 */
public class MultiColumnList extends JScrollPane {

	private static final long serialVersionUID = 1L;
	/**
	 * Default minimum width of a column
	 */
	public static final int MIN_COL_WIDTH = 40;
	private static final int SAMPLE_ROWS = 25;
	/**
	 * Internal table
	 */
	protected JTable jtable = new JTable();
	private ResultList list = null;
	private TableSorter sorter = null;
	private Vector selectionListeners = new Vector();
	private Vector propertyListeners = new Vector();
	private Vector mouseListeners = new Vector();
	
	public MultiColumnList() {
		super();
		setBorders();
		handleFocus(this);
		jtable.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE); 
	}
	
	private static void handleFocus(Component c) {
		c.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				FocusManager fm = FocusManager.getCurrentManager(); 
				fm.focusNextComponent();
			};
			public void focusLost(FocusEvent e) {};
		});
	}
	
	private void setBorders() {
		this.setBorder(Borders.DIALOG_BORDER);
		jtable.setBorder(Borders.DIALOG_BORDER);
	}
	
	public void clear() {
		jtable = new JTable();
		jtable.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE); 
		handleFocus(jtable);
		setViewportView(jtable);
		handleFocus(getViewport());
		setBorders();
	}
	
	public ResultList getResultList() {
		return list;
	}
	
	/**
	 * Displays all the columns in the list.  To customize the list of columns provide
	 * a Vector of column names to display, in the order to display e.g.
	 * 
	 * ApplicationControlList acl = SecurityServices.getAppControlsByUser(
	 * 										ApplicationGroup.REPORTLISTCOLUMNS);
	 * list.setColumnOrder(acl);
	 * 
	 * @param list
	 * @param colMinWidth
	 */
	public void setTableModel(ResultList list, int colMinWidth) {
		// Add sorting functionality
		this.list = list;
		sorter = new TableSorter(this.list);
		jtable = new JTable(sorter){
			public Component prepareEditor(TableCellEditor editor, final int row, final int column) 
			{ 
			    final Component c = super.prepareEditor(editor,row,column); 
			    if (c instanceof JTextField) { 
			      c.addFocusListener(new FocusListener() {

					public void focusGained(FocusEvent e) {
						// TODO Auto-generated method stub
						
					}

					public void focusLost(FocusEvent e) {
						jtable.editingStopped(new ChangeEvent(c));
					}
			      
			      });
			    } 
			    return c; 
			  } 
		};
		jtable.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE); 
		jtable.setPreferredScrollableViewportSize(jtable.getPreferredSize());
//		jtable.setPreferredSize(new Dimension(10,10));
//		jtable.setSize(new Dimension(10,10));
		jtable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		addSelectionListeners(jtable);
		addPropertyListeners(jtable);
		addMouseListeners(jtable);
		sorter.setTableHeader(jtable.getTableHeader());
		setColumnHeaders(jtable, this.list.getColumnNames(), this.list.getClasses(), colMinWidth);
		setViewportView(jtable);
		handleFocus(this.getViewport());
		handleFocus(jtable);
		// Setup copy capability //TODO replace the TableCopy class
		//TableCopy tableCopy = new TableCopy(jtable);	
		//tableCopy.hashCode();
		setBorders();
	}
	
    /**
     * Creates a jtable columns with the column name and tooltiptext
     * and estimated width
     * @param jtable
     * @param columnNames
     */
	public static void setColumnHeaders(JTable jtable, Vector columnNames, Class[] classes, int minColumnWidth) {
		JTableHeader header = jtable.getTableHeader();
		TableColumnModel tcm = header.getColumnModel();
		for (int i=0; i < tcm.getColumnCount(); i++) {
			tcm.getColumn(i).setHeaderValue(columnNames.elementAt(i));
			tcm.getColumn(i).setMinWidth(minColumnWidth);
			GUI.setToolTip(tcm.getColumn(i),"" + columnNames.get(i),
					"" + columnNames.get(i));
		}
		GUI.initColumnSizes(jtable, Math.min(jtable.getRowCount(),SAMPLE_ROWS),
				jtable.getColumnCount(),
				minColumnWidth,
				classes);
	}
		
	/**
	 * Returns true if a row is selected
	 * @return true if a row is selected
	 */
	public int getSelectedIndex() {
		return jtable.getSelectedRow();
	}
	
	/**
	 * Returns true if a row is selected
	 * @return true if a row is selected
	 */
	public boolean isRowSelected() {
		return jtable.getSelectedRow() > -1;
	}
	
	/**
	 * Returns true if a row is selected
	 * @return true if a row is selected
	 */
	public boolean isFieldSelected() {
		return isRowSelected() && jtable.getSelectedColumn() > -1;
	}
	
	/**
	 * Returns the object for the selected row
	 * or null if no row was selected.
	 * 
	 * @return object for selected row
	 */
	public Object getSelectedRow() {
		// Object is stored in the last column
		if (isRowSelected()) {
			return list.getValueAt(sorter.modelIndex(jtable.getSelectedRow()));
		} else {
			return null;
		}
	}
	
	/**
	 * Returns a Vector of objects for the selected rows
	 * or an empty Vector if no rows was selected.
	 * 
	 * @return object for selected row
	 */
	public Vector getSelectedRows() {
		// Object is stored in the last column
		if (isRowSelected()) {
			int[] rows = jtable.getSelectedRows();
			Vector values = new Vector();
			for (int i=0; i<rows.length;i++) {
				values.add(list.getValueAt(sorter.modelIndex(rows[i])));				
			}
			return values;
		} else {
			return new Vector();
		}
	}
	
	/**
	 * Returns the object for the row
	 * 
	 * @return object for selected row
	 */
	public Object getRow(int row) {
		// Object is stored in the last column
		return list.getValueAt(sorter.modelIndex(row));
	}
	
	/**
	 * Returns the object for the selected row/column
	 * or null if no row was selected.
	 * 
	 * @return object for selected field
	 */
	public Object getSelectedField() {
		if (isFieldSelected()) {
			return jtable.getValueAt(jtable.getSelectedRow(), jtable.getSelectedColumn());
		} else {
			return null;
		}
	}
	
	/**
	 * Adds a listener to be notified when a selection occurs
	 * 
	 * @param listener
	 */
	public void addListSelectionListener(ListSelectionListener listener) {
		selectionListeners.add(listener); 
		this.jtable.getSelectionModel().addListSelectionListener(listener);
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		if (propertyListeners != null) {
			propertyListeners.add(listener);
			jtable.addPropertyChangeListener(listener);
		}
	}
	
	private void addSelectionListeners(JTable table) {
		Enumeration e1 = this.selectionListeners.elements();
		while (e1.hasMoreElements()) {
			ListSelectionListener l = (ListSelectionListener) e1.nextElement();
			table.getSelectionModel().addListSelectionListener(l);
		}
	}

	private void addPropertyListeners(JTable table) {
		Enumeration e1 = this.propertyListeners.elements();
		while (e1.hasMoreElements()) {
			PropertyChangeListener l = (PropertyChangeListener) e1.nextElement();
			table.addPropertyChangeListener(l);
		}
	}

	/**
	 * Adds a listener to be notified when a selection occurs
	 * 
	 * @param listener
	 */
	public void addMouseListener(MouseListener listener) {
		super.addMouseListener(listener);
		mouseListeners.add(listener); 
		jtable.addMouseListener(listener);
	}
	
	private void addMouseListeners(JTable table) {
		Enumeration e1 = this.mouseListeners.elements();
		while (e1.hasMoreElements()) {
			MouseListener l = (MouseListener) e1.nextElement();
			table.addMouseListener(l);
		}
	}

	public JTable getJtable() {
		return jtable;
	}

	public void setJtable(JTable jtable) {
		this.jtable = jtable;
	}
	
	public void selectRow(int row) {
		ListSelectionModel selectionModel = 
			  jtable.getSelectionModel();
		if (row < jtable.getRowCount()) {
			selectionModel.setSelectionInterval(row,row);
		}
	}

	public void selectAnotherRow(int row) {
		ListSelectionModel selectionModel = 
			  jtable.getSelectionModel();
		if (row < jtable.getRowCount()) {
			selectionModel.addSelectionInterval(row,row);
		}
	}
	
	public Class getClass(int columnIndex) {
		if (list != null && columnIndex >=0 && columnIndex < list.getClasses().length) {
			return list.getClasses()[columnIndex];
		} else {
			return String.class;
		}
	}

}
