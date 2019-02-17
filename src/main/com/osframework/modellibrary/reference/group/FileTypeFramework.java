//************ AUTO GENERATED DO NOT EDIT *********//
package com.osframework.modellibrary.reference.group;

public class FileTypeFramework implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	public static final int ATTACHEDFILE = 226;
	public static final int HYPERLINK = 227;

	private int FileTypeRefId = 0;
	public FileTypeFramework(int FileTypeRefId) {
		this.FileTypeRefId = FileTypeRefId;		
	}

	public int getFileTypeRefId() {
		return FileTypeRefId;
	}

	public static FileTypeFramework get(int FileTypeRefId) {
		return new FileTypeFramework(FileTypeRefId);
	}

}
