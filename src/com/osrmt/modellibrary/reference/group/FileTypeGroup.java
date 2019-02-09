//************ AUTO GENERATED DO NOT EDIT *********//
package com.osrmt.modellibrary.reference.group;

public class FileTypeGroup implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	public static final int ATTACHEDFILE = 226;
	public static final int HYPERLINK = 227;

	private int FileTypeRefId = 0;
	public FileTypeGroup(int FileTypeRefId) {
		this.FileTypeRefId = FileTypeRefId;		
	}

	public int getFileTypeRefId() {
		return FileTypeRefId;
	}

	public static FileTypeGroup get(int FileTypeRefId) {
		return new FileTypeGroup(FileTypeRefId);
	}

}
