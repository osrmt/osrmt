package com.osframework.appclient.ui.components;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.event.*;
import java.io.*;
import java.lang.ref.WeakReference;
import java.util.Random;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.osframework.appclient.services.ReferenceServices;
import com.osframework.appclient.services.SystemServices;
import com.osframework.appclient.ui.common.IParent;
import com.osframework.appclient.ui.controls.ICustomBind;
import com.osframework.appclient.ui.listeners.DoubleClickListener;
import com.osframework.framework.logging.Debug;
import com.osframework.framework.utility.*;
import com.osframework.modellibrary.system.*;
import com.osframework.modellibrary.reference.group.FileTypeFramework;
import com.osframework.modellibrary.reference.group.FormButtonTextFramework;
import com.osframework.modellibrary.reference.group.RecordTypeFramework;
import com.osframework.modellibrary.reference.group.ReferenceGroup;
import com.osframework.modellibrary.reference.group.SystemMessageFramework;
import com.osframework.modellibrary.reference.security.*;

public class UIAttachmentList extends MultiColumnList implements ICustomBind, IParent{
	
	private JDialog parentFrame;
	private JFrame frame;
	private int tableRefId;
	private int tableId;
	private UIAttachmentList self = this;
	private UIInputBox hyperlinkInputBox;
	private static final long serialVersionUID = 1L;
	private RecordTypeFramework recordType;
	private boolean locked = false;
	private ChangeListener changeListener = null;

	private void configureUI() {
		this.setPreferredSize(new java.awt.Dimension(50,50));
		jtable.setPreferredSize(new java.awt.Dimension(50,50));
		this.setFocusable(false);
	}
	
	public void initialize(JDialog parentFrame, JFrame frame, int tableRefId, int tableId, RecordTypeFramework recordType) {
		try {
			this.parentFrame = parentFrame;
			this.frame = frame;
			this.tableRefId = tableRefId;
			this.tableId = tableId;
			this.recordType = recordType;
			configureUI();
			addListeners();
			updateAttachmentList();
		} catch (Exception ex) {
			Debug.LogException(this, ex);
		}
	}
	
	private void updateAttachmentList() throws Exception {
		new TimedAction(0.5) {
			public void executeTask() {
				try {
					RecordFileList list = SystemServices.getFiles(tableRefId, tableId);
					list.setColumnOrder(getColumnDisplay());
					setTableModel(list,60);
					notifyListener();
				} catch (Exception ex) {
					Debug.LogException(this, ex);
				}
			};
		};
	}
	
	private ApplicationControlList getColumnDisplay() {
		//TODO replace this with database entry
		ApplicationControlList acl = new ApplicationControlList();
		
		ApplicationControlModel acm = new ApplicationControlModel();
		acm.setControlRefDisplay("description");
		acm.setControlText(ReferenceServices.getDisplay(FormButtonTextFramework.ATTACHMENTDESCRIPTION));
		acl.add(acm);
		
		ApplicationControlModel acm1 = new ApplicationControlModel();
		acm1.setControlRefDisplay("fileName");
		acm1.setControlText(ReferenceServices.getDisplay(FormButtonTextFramework.FILENAME));
		acl.add(acm1);
		
		ApplicationControlModel acm2 = new ApplicationControlModel();
		acm2.setControlRefDisplay("sourceFile");
		acm2.setControlText(ReferenceServices.getDisplay(FormButtonTextFramework.SOURCEFILENAME));
		acl.add(acm2);
		
		return acl;
	}
	
	private void addListeners() {
		DoubleClickListener attachDblClick = new DoubleClickListener() {
			public void call(ActionEvent me) {
				try {
					self.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
					if (isRowSelected()) {						
						Object o = getSelectedRow();
						if (o != null) {
							RecordFileModel m = (RecordFileModel) o;
							if (m.getFileTypeRefId() == FileTypeFramework.ATTACHEDFILE) {
								FileProcess.executeFile(storeLocal(m));
							} else {
								FileProcess.executeFile(m.getSourceFile());
							}
						}
					}
					self.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				} catch (Exception ex) {
					Debug.LogException(this, ex);
					self.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				}
			}
		};
		addMouseListener(attachDblClick);
		addMouseListener(getPopup());
	}
	
	private MouseAdapter getPopup() {
		
		MouseAdapter popupNew = new MouseAdapter() { 
            public void mousePressed(MouseEvent e) 
            { 
            	try {
                    if (!locked && SwingUtilities.isRightMouseButton(e) == true) 
                    { 
    					Object o = getSelectedRow();
                        JPopupMenu pop = new JPopupMenu("");
    					if (o != null) {
	                        pop.add(getInsertFileAction());
	                        pop.add(getInsertLinkAction());
	                        pop.addSeparator();
	                        pop.add(getDeleteAction());
	                        pop.addSeparator();
	                        pop.add(getPropertiesAction());
	                        pop.setLocation(300, 300);
	                        pop.show(e.getComponent(), e.getX(), e.getY());
    	                } else {
    	                	if (tableId > 0) {
		                        pop.add(getInsertFileAction());
		                        pop.add(getInsertLinkAction());
		                        pop.setLocation(300, 300);
		                        pop.show(e.getComponent(), e.getX(), e.getY()); 
    	                	} else {
    	                		Debug.displayGUIMessage(ReferenceServices.getDisplay(SystemMessageFramework.SAVEARTIFACTBEFOREATTACHINGDOCUMENTS));
    	                	}
    	                }
                    } 
            	} catch (Exception ex) {
            		Debug.LogException(this, ex);
            	}
            } 
        };

		return popupNew; 
	
	}
	
	public JMenuItem getInsertFileAction() {
        JMenuItem menu = new JMenuItem(ReferenceServices.getDisplay(FormButtonTextFramework.ATTACHFILE));
        menu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					FileDialog fileDialog;
					if (frame != null) {
						fileDialog = new FileDialog(frame,ReferenceServices.getDisplay(FormButtonTextFramework.ATTACHFILE), FileDialog.LOAD);
					} else {
						fileDialog = new FileDialog(parentFrame,ReferenceServices.getDisplay(FormButtonTextFramework.ATTACHFILE), FileDialog.LOAD);
					}
					fileDialog.setVisible(true);
					RecordFileModel m = new RecordFileModel();
					m.setFileTypeRefId(FileTypeFramework.ATTACHEDFILE);
					m.setFileName(fileDialog.getFile());
					m.setTableId(tableId);
					m.setTableRefId(tableRefId);
					if (fileDialog.getFile() != null) {
						SystemServices.storeBinaryFile(m, 
								FileSystemUtil.getBinaryContents(fileDialog.getDirectory(), fileDialog.getFile()),
								false);
						updateAttachmentList();
					}
				} catch (Exception ex) {
					Debug.LogException(this, ex);
				}
			}			
		});
        return menu;
	}
	
	public JMenuItem getInsertLinkAction() {
		//TODO these menu item strings should come from the database (ideally app cached)
        JMenuItem menu = new JMenuItem(ReferenceServices.getDisplay(FormButtonTextFramework.INSERTHYPERLINK));
        menu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ActionListener okAction = new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							try {
								RecordFileModel m = new RecordFileModel();
								m.setFileTypeRefId(FileTypeFramework.HYPERLINK);
								m.setSourceFile(hyperlinkInputBox.getInputText());
								m.setTableId(tableId);
								m.setTableRefId(tableRefId);
								SystemServices.storeHyperlink(m);
								updateAttachmentList();
							} catch (Exception ex) {
								Debug.LogException(this, ex);
							}
						}
					};
					hyperlinkInputBox = new UIInputBox(okAction, ReferenceServices.getDisplay(FormButtonTextFramework.INSERTHYPERLINK),frame,"URL:",null,new Dimension(500,150), false);
					hyperlinkInputBox.setVisible(true);
				} catch (Exception ex) {
					Debug.LogException(this, ex);
				}
			}			
		});
        return menu;
	}
	
	public JMenuItem getPropertiesAction() {
        JMenuItem menu = new JMenuItem(ReferenceServices.getDisplay(FormButtonTextFramework.PROPERTIES));
        menu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Object o = getSelectedRow();
					RecordFileModel m = (RecordFileModel) o;
					AttachmentProperty property = new AttachmentProperty(self, frame,m);
					property.setVisible(true);
				} catch (Exception ex) {
					Debug.LogException(this, ex);
				}
			}			
		});
        return menu;
	}
	
	public JMenuItem getDeleteAction() {
        JMenuItem menu = new JMenuItem(ReferenceServices.getDisplay(FormButtonTextFramework.DELETE));
        menu.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					Object o = getSelectedRow();
					RecordFileModel m = (RecordFileModel) o;
					m.setActiveInd(0);
					SystemServices.updateFile(m, recordType);
					updateAttachmentList();
				} catch (Exception ex) {
					Debug.LogException(this, ex);
				}
			}
			
		});
        return menu;
	}
	
	/**
	 * Store the file locally and return path to file
	 * @param m
	 * @return
	 */
	private String storeLocal(RecordFileModel m) throws IOException {
		String localFile = FileProcess.getRandomFile(m.getFileName());
		FileSystemUtil.createBinaryFile(FileProcess.getTemporaryDirectory(), localFile,SystemServices.getBinaryFile(m));
		
		return FileProcess.getFilePath(FileProcess.getTemporaryDirectory(),localFile);
	}

	public void setValue(Object o1, Object o2) {
		// TODO Auto-generated method stub
		
	}

	public void setValue(Object o) {
		if (o != null) {
			try {
				RecordFileModel m = (RecordFileModel) o;
				SystemServices.updateFile(m, recordType);
				updateAttachmentList();
			} catch (Exception ex) {
				Debug.LogException(this, ex);
			}
		}
		
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}
	
	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		this.locked = !enabled;
	}
	
	public void addChangeListener(ChangeListener change) {
		if (changeListener != null) {
			changeListener = change;
		}		
	}
	
	private void notifyListener() {
		if (changeListener != null) {
			changeListener.stateChanged(new ChangeEvent(this));
		}		
	}


}
