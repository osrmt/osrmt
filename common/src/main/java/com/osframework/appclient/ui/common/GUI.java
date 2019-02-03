package com.osframework.appclient.ui.common;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import com.jgoodies.forms.factories.Borders;
import com.jgoodies.looks.LookUtils;
import com.jgoodies.looks.Options;
import com.osframework.appclient.services.ReferenceServices;
import com.osframework.appclient.system.SystemLogForm;
import com.osframework.appclient.ui.components.MultiColumnList;
import com.osframework.appclient.ui.controls.UIComboBox;
import com.osframework.appclient.ui.controls.UIJPanel;
import com.osframework.appclient.ui.controls.UILabel;
import com.osframework.appclient.ui.listeners.DoubleClickListener;
import com.osframework.framework.logging.Debug;
import com.osframework.framework.logging.DebugLevel;
import com.osframework.framework.logging.DebugMessage;
import com.osframework.framework.utility.FileProcess;
import com.osframework.modellibrary.common.IControlModel;
import com.osframework.modellibrary.common.ResultList;
import com.osframework.modellibrary.reference.group.SystemMessageFramework;
import com.osframework.modellibrary.reference.security.ApplicationControlList;
import com.osframework.modellibrary.reference.security.ApplicationControlModel;

/**
 * The GUI class is a temporary location for uncategorized framework 'helper' methods
 * for client applications.  As groups of related methods are determined a separate
 * class should be created and the static methods moved from this location.
 *
 */
public class GUI {
	
	public static void setLookAndFeel() {
        String lafName =
            LookUtils.IS_OS_WINDOWS_XP
                ? Options.getCrossPlatformLookAndFeelClassName()
                : Options.getSystemLookAndFeelClassName();

        try {
            UIManager.setLookAndFeel(lafName);
        } catch (Exception e) {
        	Debug.LogExceptionOnly("GUI", e);
        }
	}
	
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
		} catch (Exception ex) {
			Debug.LogExceptionOnly("GUI", ex);
			return null;
		}
	}
	
	/**
	 * Highlights (selects) the text in the combo box
	 * @param jcb
	 */
	public static void selectText(JComboBox jcb) {
		try {
		if (jcb != null && jcb.getEditor() != null) {
			JTextField field = (JTextField)jcb.getEditor().getEditorComponent();
			if (field != null) {
				field.selectAll();
			}
		}
		} catch (Exception ex) {
			Debug.LogExceptionOnly("GUI", ex);
		}
	}
	
	/**
	 * Iterates through the list to find an item matching text
	 * If the item is found it is selected
	 * @param jcb
	 * @param text
	 */
	public static boolean selectValue(JComboBox jcb, String text) {
		try {
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
		} catch (Exception ex) {
			Debug.LogExceptionOnly("GUI", ex);
			return false;
		}
	}
	
	/**
	 * Iterates through the list to find an item matching text
	 * If the item is found it is selected
	 * @param jcb
	 * @param text
	 */
	public static boolean selectValue(MultiColumnList jcb, String text) {
		try {
		if (jcb != null && text != null) {
			for (int i=0; i<jcb.getJtable().getRowCount(); i++) {
				Object o = jcb.getRow(i);
				if (o != null) {
					if (text.compareTo(o.toString())==0) {
						jcb.selectRow(i);
						return true;
					}
				}
			}
		}
		return false;
		} catch (Exception ex) {
			Debug.LogExceptionOnly("GUI", ex);
			return false;
		}
	}
	
	/**
	 * Iterates through the list to find an item matching text
	 * If the item is found it is selected
	 * @param jcb
	 * @param text
	 */
	public static boolean selectValue(MultiColumnList jcb, Object value) {
		try {
		if (jcb != null && value != null) {
			for (int i=0; i<jcb.getJtable().getRowCount(); i++) {
				Object o = jcb.getRow(i);
				if (o != null) {
					if (o instanceof IControlModel) {
						if (((IControlModel) o).getPrimaryValue() != null) {
							if (((IControlModel) o).getPrimaryValue().equals(value)) {
								jcb.selectAnotherRow(i);
								return true;
							}
						}
					}
				}
			}
		}
		return false;
		} catch (Exception ex) {
			Debug.LogExceptionOnly("GUI", ex);
			return false;
		}
	}
	
	/**
	 * Iterates through the list to find an item matching text
	 * If the item is found it is selected
	 * @param jcb
	 * @param text
	 */
	public static boolean selectValue(ComboBoxModel jcb, String text) {
		try {
			if (jcb != null && text != null) {
				for (int i=0; i<jcb.getSize(); i++) {
					Object o = jcb.getElementAt(i);
					if (o != null) {
						if (text.compareTo(o.toString())==0) {
							jcb.setSelectedItem(o);
							return true;
						}
					}
				}
			}
			return false;
		} catch (Exception ex) {
			Debug.LogExceptionOnly("GUI", ex);
			return false;
		}
	}
	
	/**
	 * Iterates through the list to find an item matching text
	 * If the item is found it is selected
	 * @param jcb
	 * @param text
	 */
	public static boolean selectValue(ComboBoxModel jcb, Object text) {
		try {
			if (jcb != null && text != null) {
				for (int i=0; i<jcb.getSize(); i++) {
					Object o = jcb.getElementAt(i);
					if (o != null) {
						if (text.equals(o)) {
							jcb.setSelectedItem(o);
							return true;
						}
					}
				}
			}
			return false;
		} catch (Exception ex) {
			Debug.LogExceptionOnly("GUI", ex);
			return false;
		}
	}
	/**
	 * Returns 90% of the maximum height and width of the screen
	 * @return max size
	 */
	public static Dimension maximumSize() {
		try {
	      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); 
	      int h =  (int) ( (double) screenSize.height * 0.90 ); 
	      int w =  (int) ( (double) screenSize.width  * 0.90 ); 
	      return new Dimension(w,h);
		} catch (Exception ex) {
			Debug.LogExceptionOnly("GUI", ex);
			return new Dimension(800,600);
		}
	}
	
	/**
	 * Determine column size by sampling data
	 * @param table
	 * @param nbrRows number of rows to sample
	 * @param nbrColumns number of columns in the table
	 * @param minWidth minimum width of the column
	 */
    public static void initColumnSizes(JTable table, int nbrRows, int nbrColumns, int minWidth, Class[] classList) {
    	try {
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
		} catch (Exception ex) {
			Debug.LogException("GUI", ex);
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
    	try {
	    	JLabel headerRenderer = new DefaultTableCellRenderer();
	        headerRenderer.setText(text);
	        headerRenderer.setToolTipText(tooltip);
	        headerRenderer.setBackground(new java.awt.Color(153,153,255));
	        headerRenderer.setForeground(java.awt.Color.black);
	        column.setHeaderRenderer((TableCellRenderer) headerRenderer);
		} catch (Exception ex) {
			Debug.LogException("GUI", ex);
		}
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
			throw new Exception(ReferenceServices.getMsg(SystemMessageFramework.VALUENOTFOUNDINLIST, colName));
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
			throw new Exception(ReferenceServices.getMsg(SystemMessageFramework.VALUENOTFOUNDINLIST, colName));
		}
		return new Vector();		
	}

	/**
	 * Retrieves an image file from the same directory as the class.
	 * 
	 * @param fileName
	 * @return
	 */
	public static Image getImage(String imagePath, Object o) {
		try {
		    String imgLocation = "/resources/images/" + imagePath;
		    URL imageURL = o.getClass().getResource(imgLocation);
		    if (imageURL != null) {
		    	ImageIcon ii = new ImageIcon(imageURL);
		    	if (ii != null) {
		    		return ii.getImage();
		    	}
		    }
		    Debug.LogWarningOnly(o, ReferenceServices.getMsg(SystemMessageFramework.RESOURCEFILENOTFOUND, imgLocation));
		    return null;
		} catch (Exception ex) {
			Debug.LogExceptionOnly("GUI",ex);
			return null;
		}
	}
	
	/**
	 * Retrieves an image file from the same directory as the class.
	 * 
	 * @param fileName
	 * @return
	 */
	public static Image getExternalImage(String fullPath, Object o) {
		try {
	    	ImageIcon ii = new ImageIcon(fullPath);
	    	if (ii != null) {
	    		return ii.getImage();
	    	}
		    Debug.LogWarningOnly(o, ReferenceServices.getMsg(SystemMessageFramework.RESOURCEFILENOTFOUND, fullPath));
		    return null;
		} catch (Exception ex) {
			Debug.LogExceptionOnly("GUI",ex);
			return null;
		}
	}
	
	/**
	 * Retrieves an image file from the same directory as the class.
	 * 
	 * @param fileName
	 * @return
	 */
	public static Icon getImageIcon(String imagePath, Object o) {
		try {
		    String imgLocation = "/resources/images/" + imagePath;
		    URL imageURL = o.getClass().getResource(imgLocation);
		    if (imageURL != null) {
		    	ImageIcon ii = new ImageIcon(imageURL);
		    	if (ii != null) {
		    		return ii;
		    	}
		    }
		    //Debug.LogWarningOnly(o, ReferenceServices.getMsg(SystemMessageFramework.RESOURCEFILENOTFOUND, imgLocation));
		    return null;
		} catch (Exception ex) {
			Debug.LogExceptionOnly("GUI",ex);
			return null;
		}
	}
	
	public static void setClipboard(String s) {
		try {
			StringSelection ss = new StringSelection(s);
				 
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
		} catch (Exception ex) {
			Debug.LogException("GUI", ex);
		}
	}
	
	public static String getClipboardText () {
		try {
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
		} catch (Exception ex) {
			Debug.LogException("GUI", ex);
			return null;
		}
	}
	
	public static String substring(String s, int maxNbrChars) {
		if (s != null) {
			if (s.length() > maxNbrChars) {
				return s.substring(0,maxNbrChars);
			}
		}
		return s;
	}
	
	public static void registerDebug(final JFrame frame, final Container container) {
		try {
			if (frame != null) {
				ActionListener action = new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							DebugMessage msg = (DebugMessage) e.getSource();
							if (frame != null) {// && frame.isActive()) {
								displayMessage(container, msg);
							}
						} catch (Exception ex) {
							Debug.LogExceptionOnly(this, ex);
						}
					}
				};
				Debug.addListener(frame, action);
			}
		} catch (Exception ex) {
			Debug.LogExceptionOnly("GUI", ex);
		}
	}
	
	public static void registerDebug(final JDialog dialog, final Container container) {
		try {
		if (dialog != null) {
			ActionListener action = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						DebugMessage msg = (DebugMessage) e.getSource();
						if (dialog != null ) { //&& dialog.isActive()) {
							displayMessage(container, msg);
						}
					} catch (Exception ex) {
						Debug.LogExceptionOnly(this, ex);
					}
				}
			};
			Debug.addListener(dialog, action);
		}
		} catch (Exception ex) {
			Debug.LogExceptionOnly("GUI",ex);
		}
	}
	
	public static void unregisterDebug(final Container container) {
		try {
			Debug.removeListener(container);
		} catch (Exception ex) {
			Debug.LogExceptionOnly("GUI",ex);
		}
	}
	
	private static void displayMessage(Container container, DebugMessage msg) {
		try {
		if (container != null) {
			UIJPanel panel = new UIJPanel(new BorderLayout());
			panel.setBorder(Borders.EMPTY_BORDER);
			try {
				String imageName = "info.gif";
				if (msg.getDebugLevel() == DebugLevel.Warning) {
					imageName = "warning.gif";
				} else if (msg.getDebugLevel() == DebugLevel.Error) {
					imageName = "error.gif";
				}
				if (msg.getDebugLevel() <= DebugLevel.Info) {
					Image image = getImage(imageName, container);
					if (image != null && msg.getErrorMessage() != null && !msg.getErrorMessage().equals("")) {
						JLabel imageLabel = new JLabel(new ImageIcon(image));
						imageLabel.addMouseListener(getErrorLabelListener(imageLabel, container));
						panel.add(imageLabel, BorderLayout.WEST);
					}
				}
			} catch (Exception ex) {
				Debug.LogExceptionOnly("GUI", ex);
			}
			UILabel errorLabel = new UILabel("  " + msg.getErrorMessage());
			errorLabel.addMouseListener(getErrorLabelListener(errorLabel, container));
			panel.add(errorLabel, BorderLayout.CENTER);
			container.removeAll();
			container.add(panel, BorderLayout.CENTER);
			container.invalidate();
			container.validate();
			container.repaint();
		}
		} catch (Exception ex) {
			Debug.LogExceptionOnly("GUI", ex);
		}
	}
	
	private static DoubleClickListener getErrorLabelListener(JLabel label, final Container container) {
		DoubleClickListener l = new DoubleClickListener(true) {
			public void call(ActionEvent me) {
				SystemLogForm s = new SystemLogForm(null, false);
				s.setVisible(true);
			}			
		};
		label.addMouseMotionListener(new MouseMotionListener(){
			public void mouseMoved(MouseEvent e) {
				container.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			public void mouseDragged(MouseEvent e) {
			}
		});
		label.setOpaque(true);
		label.setBackground(Color.decode("#FFFFCC"));
		return l;
	}
	
	public static void setGUIMessage(final JComponent field, final ApplicationControlModel rm) {
		if (rm != null && rm.getControlDescription() != null) {
			try {
				final FocusListener listener = new FocusAdapter() {
					public void focusGained(FocusEvent e) {
						Debug.displayGUIMessage(rm.getControlDescription());
					};
					public void focusLost(FocusEvent e) {
						Debug.displayGUIMessage("");
					};
				};
				field.addFocusListener(listener);
				if (field instanceof UIComboBox) {
					try {
					((UIComboBox) field).getEditor().getEditorComponent().addFocusListener(listener);
					((UIComboBox) field).getEditor().getEditorComponent().addMouseMotionListener(new MouseMotionListener() {
						public void mouseDragged(MouseEvent e) {}
						public void mouseMoved(java.awt.event.MouseEvent e) {listener.focusGained(null);};
					});
					} catch (Exception e){};
				}
				field.addMouseMotionListener(new MouseMotionListener() {
					public void mouseDragged(MouseEvent e) {}
					public void mouseMoved(java.awt.event.MouseEvent e) {listener.focusGained(null);};
				});
			} catch (Exception ex) {};
		}
	}
	
	
	public static String toCSV(ResultList list, Vector columns, String separator) {
		StringBuffer sb = new StringBuffer(8 * columns.size() * list.getRowCount());
		for (int i=0; i < list.getColumnCount(); i++) {
			if (i > 0) {
				sb.append(separator);
			}
			sb.append( columns.elementAt(i));
		}
		sb.append(FileProcess.nl());
		for (int r=0; r<list.getRowCount(); r++) {
			for (int c=0; c< list.getColumnCount(); c++) {
				if (c>0) {
					sb.append(separator);
				}
				sb.append(getCsvValue(list.getValueAt(r,c)));
			}
			sb.append(FileProcess.nl());
		}
		return sb.toString();
	}
	
	private static String getCsvValue(Object o) {
		if (o != null && o instanceof String) {
			return o.toString();
		} else if (o!= null){
			return o.toString();
		} else {
			return "";
		}
	}
	
	public static void setValue(Document document, Object o) {
		try {
			if (o != null && document != null) {
				document.remove(0,document.getLength());
				document.insertString(0,o.toString(),null);
			}
		} catch (BadLocationException e) {
			Debug.LogException("GUI", e);			
		}
	}
	
	public static String toUpperCase(String s) {
		if (s != null) {
			s = s.toUpperCase();
		}
		return s;
	}
	
	public static boolean requiredFieldsPopulated(ApplicationControlList acl, IControlModel m) {
		Enumeration e1 = acl.elements();
		while (e1.hasMoreElements()) {
			ApplicationControlModel acm = (ApplicationControlModel) e1.nextElement();
			if (acm.isRequired()) {
				Object value = m.getModelColDataAt(acm.getModelColumnRefId());
				if (value == null || (value instanceof String && value.toString().equals(""))) {
					Debug.displayGUIMessage(ReferenceServices.getDisplay(SystemMessageFramework.REQUIREDFIELDNOTPOPULATED) + " "  + acm.getControlText());
					return false;
				}
			}
		}
		return true;
	}
	
	public static boolean hasDialog(Component c) {
		if (c == null) {
			return false;
		} else {
			Component parent = c.getParent();
			while (parent != null) {
				if (parent instanceof JDialog) {
					return true;
				} else {
					parent = parent.getParent();
				}
			}
			return false;
		}
	}
	
	public static JDialog getDialog(Component c) {
		if (c == null) {
			return null;
		} else {
			Component parent = c.getParent();
			while (parent != null) {
				if (parent instanceof JDialog) {
					return (JDialog) parent;
				} else {
					parent = parent.getParent();
				}
			}
			return null;
		}
	}
	
	public static JFrame getFrame(Component c) {
		if (c == null) {
			return null;
		} else {
			Component parent = c.getParent();
			while (parent != null) {
				if (parent instanceof JDialog) {
					return (JFrame) parent;
				} else {
					parent = parent.getParent();
				}
			}
			return null;
		}
	}
}
