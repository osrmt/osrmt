package utilities;

import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;
import java.io.*;
import java.net.URL;

import javax.swing.*;
import javax.swing.table.*;
import javax.swing.text.JTextComponent;
import javax.swing.border.Border;
import javax.swing.event.*;

/**
 * The GUI class is a temporary location for uncategorized framework 'helper' methods
 * for client applications.  As groups of related methods are determined a separate
 * class should be created and the static methods moved from this location.
 *
 */
public class GUI {
	
	
	/**
	 * Returns the document text contents.
	 * Returns null if errors occur or anything is null.
	 * 
	 * @param e
	 * @return
	 */
	public static String getText(DocumentEvent e) {
		try {
			if (e == null) {
				return null;
			} else if (e.getDocument() == null) {
				return null;
			} else {
				return e.getDocument().getText(0, e.getDocument().getLength());
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Highlights (selects) the text in the combo box
	 * @param jcb
	 */
	public static void selectText(JComboBox jcb) {
		if (jcb != null && jcb.getEditor() != null) {
			JTextField field = (JTextField)jcb.getEditor().getEditorComponent();
			if (field != null) {
				field.selectAll();
			}
		}
	}
	
	/**
	 * Iterates through the list to find an item matching text
	 * If the item is found it is selected
	 * @param jcb
	 * @param text
	 */
	public static boolean selectValue(JComboBox jcb, String text) {
		if (jcb != null && text != null) {
			for (int i=0; i<jcb.getItemCount(); i++) {
				Object o = jcb.getItemAt(i);
				if (o != null) {
					if (text.compareTo(o.toString())==0) {
						jcb.setSelectedIndex(i);
						return true;
					}
				}
			}
		}
		return false;
	}
	/**
	 * Returns 90% of the maximum height and width of the screen
	 * @return max size
	 */
	public static Dimension maximumSize() {
	      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); 
	      int h =  (int) ( (double) screenSize.height * 0.90 ); 
	      int w =  (int) ( (double) screenSize.width  * 0.90 ); 
	      return new Dimension(w,h);
	}
	
	/**
	 * Determine column size by sampling data
	 * @param table
	 * @param nbrRows number of rows to sample
	 * @param nbrColumns number of columns in the table
	 * @param minWidth minimum width of the column
	 */
    public static void initColumnSizes(JTable table, int nbrRows, int nbrColumns, int minWidth, Class[] classList) {
        TableColumn column = null;
        Component comp = null;
        int cellWidth = 0;
        
        // Get the maximum size for each column
        for (int i = 0; i < nbrColumns; i++) {
        	int maxWidth = minWidth;
            column = table.getColumnModel().getColumn(i);
        	for (int r = 0; r < nbrRows; r++) {	
	            comp = table.getDefaultRenderer(classList[i]).
	            getTableCellRendererComponent(
	                table, table.getValueAt(r,i),
	                false, false, 0, i);
	
	            cellWidth = comp.getPreferredSize().width;
	
	            maxWidth = Math.max(maxWidth, cellWidth);
        	}
        	if (maxWidth > minWidth) {
        		maxWidth += 10;
        	}
        	column.setPreferredWidth(maxWidth);
        }
    }
    
    /**
     * Sets the header text and tooltip on the column
     * 
     * @param column
     * @param text
     * @param tooltip
     */
    public static void setToolTip(TableColumn column, String text, String tooltip) {
    	JLabel headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setText(text);
        headerRenderer.setToolTipText(tooltip);
        headerRenderer.setBackground(new java.awt.Color(153,153,255));
        headerRenderer.setForeground(java.awt.Color.black);
        column.setHeaderRenderer((TableCellRenderer) headerRenderer);
    }
    
	/**
	 * Returns the object value on the selected row from the column
	 * with matching column name.  Returns null if row not selected
	 * and throws an exception if the column name does not match
	 * 
	 * @param colName
	 * @param jtable
	 * @param columnNames
	 * @return
	 */
	public static Object getSelectedColumnValue(String colName, JTable jtable, Vector columnNames) throws Exception {
		if (jtable.getSelectedRow() > -1) {
			Enumeration e1 = columnNames.elements();
			int i=0;
			while (e1.hasMoreElements()) {
				String columnName = (String) e1.nextElement();
				if (columnName.compareToIgnoreCase(colName)==0) {
					return jtable.getValueAt(jtable.getSelectedRow(), i);
				}
				i++;
			}
			throw new Exception("Column name not found " + colName);
		}
		return null;		
	}

	/**
	 * Returns the object value on the selected row from the column
	 * with matching column name.  Returns null if row not selected
	 * and throws an exception if the column name does not match
	 * 
	 * @param colName
	 * @param jtable
	 * @param columnNames
	 * @return
	 */
	public static Vector getSelectedColumnValues(String colName, JTable jtable, Vector columnNames) throws Exception {
		if (jtable.getSelectedColumnCount() > 0) {
			Enumeration e1 = columnNames.elements();
			int i=0;
			while (e1.hasMoreElements()) {
				String columnName = (String) e1.nextElement();
				if (columnName.compareToIgnoreCase(colName)==0) {
					Vector v = new Vector();
					int[] rows = jtable.getSelectedRows();
					for (int r=0; r < rows.length; r++ ) {
						v.add(jtable.getValueAt(rows[r], i));
					}
					return v;
				}
				i++;
			}
			throw new Exception("Column name not found " + colName);
		}
		return new Vector();		
	}

	
	public static void setClipboard(String s) {
		
		StringSelection ss = new StringSelection(s);
			 
		 Toolkit.getDefaultToolkit().getSystemClipboard().setContents(
				 ss, null);
	}
	
	public static String getClipboardText () {
		 Transferable t = 
			   Toolkit.getDefaultToolkit().getSystemClipboard().
			   getContents(null);
			     
			 try {
			   if (t != null && t.isDataFlavorSupported(
			     DataFlavor.stringFlavor)) {
			       String s = (String)t.getTransferData(
			         DataFlavor.stringFlavor);
			       return s;
			    }
			 } catch (UnsupportedFlavorException e) {
			 } catch (IOException e) {
			 }
		return null;
	}
	
	public static String substring(String s, int maxNbrChars) {
		if (s != null) {
			if (s.length() > maxNbrChars) {
				return s.substring(0,maxNbrChars);
			}
		}
		return s;
	}
	
}
