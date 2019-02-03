/*
    //usage

    Copyright (C) 2006  Aron Lancout Smith

    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program; if not, write to the Free Software
    Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA

*/
package com.osrmt.modellibrary.reqmanager;

import com.osframework.appclient.ui.common.GUI;
import com.osframework.appclient.ui.editor.DocumentTextLineModel;
import com.osframework.appclient.ui.editor.IDocumentLine;
import com.osframework.appclient.ui.editor.ImageLineModel;
import com.osframework.framework.utility.FileProcess;
import com.osframework.modellibrary.system.RecordFileModel;

/**
null
*/
public class ArtifactDocumentModel extends ArtifactDocumentDataModel implements Comparable {

	private static final long serialVersionUID = 1L;
	private RecordFileModel imageFile = null;
	
	public ArtifactDocumentModel() {

	}
	
	public boolean isImageLineModel() {
		return this.getImageRecordFileId() > 0;
	}

	public int compareTo(Object m) {
		return 0;
	}

	public IDocumentLine getDocumentLine() {
		if (isImageLineModel()) {
			String path = FileProcess.getFilePath(imageFile.getStorageDirectory(), imageFile.getStorageFileName());
			ImageLineModel m = new ImageLineModel(
					GUI.getExternalImage(path, this), path);
			return m;
		} else {
			DocumentTextLineModel m = new DocumentTextLineModel();
			m.setValue(super.getLineText());
			return m;
		}
	}

	public RecordFileModel getImageFile() {
		return imageFile;
	}

	public void setImageFile(RecordFileModel imageFile) {
		this.imageFile = imageFile;
	}

	
}