package com.osframework.appclient.ui.editor;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionListener;
import java.util.EventObject;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import com.osframework.appclient.ui.common.*;
import com.osframework.framework.logging.*;

import com.osframework.appclient.ui.common.GUI;
import com.osframework.appclient.ui.controls.UIPopupMenu;
import com.osframework.appclient.ui.tree.UITreeNode;
import com.osrmt.appclient.reqmanager.RequirementManagerTools;
import com.osrmt.modellibrary.reqmanager.ArtifactModel;
/**
 * UITextTable is a table fulfilling the function
 * of a text editor.
 * 
 *
 */
public class UIDocumentTable extends JTable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Size of first column used as a control column
	 */
	private static int controlColumnSize = 30;
	private UIDocumentTable self = this;
	private UIDocumentTableModel tableModel = null;

	/**
	 * Text table always uses the text table model
	 * 
	 */
	public UIDocumentTable(UIDocumentTableModel model) {
		super(model);
		this.tableModel = model;
		formatTable();
	}


	/**
	 * Format the table
	 *
	 */
	private void formatTable() {
		setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		setCellSelectionEnabled(false);
		setRowSelectionAllowed(false);
		setShowGrid(false);
		TableColumnModel tcm =  getTableHeader().getColumnModel();
		tcm.getColumn(UIDocumentTableModel.iconColumnIndex).setCellRenderer(new TableCellRenderer() {

			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
				javax.swing.JLabel label = new JLabel();
				label.setIcon(GUI.getImageIcon("texttableline.gif", this));
				label.addMouseListener(getPropertyAdapter());
				return label;
			}
			
		});
		tcm.getColumn(UIDocumentTableModel.textColumnIndex).setCellRenderer(new TableCellRenderer() {

			public Component getTableCellRendererComponent(final JTable table, Object value, boolean isSelected, boolean hasFocus, final int row, final int column) {
				IDocumentLine line = tableModel.getDocumentLine(row);
				table.setRowHeight(row, line.getRowHeight());
				return line.getRendererComponent();
			}
			
		});
		tcm.getColumn(UIDocumentTableModel.textColumnIndex).setCellEditor(new TextCellEditor());
		
		this.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				int row = self.rowAtPoint(e.getPoint());
				int column = self.columnAtPoint(e.getPoint());
				if (row > -1) {
					if (column > 0) {
						IDocumentLine line = tableModel.getDocumentLine(row);
						if (line instanceof DocumentTextLineModel) {
							self.editCellAt(row, column);
						}
					}
				}
			}
		});
		
		this.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row = self.rowAtPoint(e.getPoint());
				int column = self.columnAtPoint(e.getPoint());
				if (row > -1) {
					if (column == 0) {
						showPopup(e.getX(), e.getY(), row);
					}
				}
			}			
		});
		tcm.getColumn(UIDocumentTableModel.iconColumnIndex).setPreferredWidth(controlColumnSize);
		tcm.getColumn(UIDocumentTableModel.iconColumnIndex).setMinWidth(controlColumnSize);
		tcm.getColumn(UIDocumentTableModel.iconColumnIndex).setMaxWidth(controlColumnSize);
	}
	
	/**
	 * Get the action for clicking on the property column
	 * 
	 * @return
	 */
	private MouseAdapter getPropertyAdapter() {
		return new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					ISApplicationMediator.getInstance().receive(ISEvent.APPLIEDCHANGES, UIContext.contextTextTable);
					super.mouseClicked(e);
				} catch (Exception ex) {
					Debug.LogException(this, ex);
				}
			}
		};
	}
	
	private void showPopup(int x, int y, final int row) {
		UIPopupMenu pop = new UIPopupMenu("");
		javax.swing.JMenuItem insertBelow= new javax.swing.JMenuItem();
		insertBelow.setText("insert below");
		insertBelow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tableModel.insertTextRow(row+1);
			}
		});
		javax.swing.JMenuItem insertAbove = new javax.swing.JMenuItem();
		insertAbove.setText("insert above");
		insertAbove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tableModel.insertTextRow(row);
			}
		});
		pop.add(insertAbove);
		pop.add(insertBelow);
        //addFileNew(pop, actions, menuManager);
        //addRefresh(pop, actions, menuManager, node);
        //addDelete(pop, actions, menuManager, node);
        pop.show(this, x, y); 
		
	}
}
