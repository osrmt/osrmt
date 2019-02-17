package com.osrmt.appclient.artifact.form;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.*;
import java.text.*;
import javax.swing.event.*;
import javax.swing.text.*;

import com.jgoodies.forms.builder.*;
import com.jgoodies.forms.layout.*;

import com.osframework.appclient.ui.components.*;
import com.osframework.appclient.ui.controls.*;
import com.osframework.modellibrary.common.*;
import com.osframework.modellibrary.reference.security.*;
import com.osframework.appclient.services.*;
import com.osframework.appclient.ui.listeners.*;
import com.osframework.modellibrary.reference.common.*;
import com.osframework.modellibrary.reference.group.*;
import com.osframework.ejb.reference.common.IReferenceMap;
import com.osframework.ejb.reference.security.ISecurity;
import com.osframework.framework.logging.*;
import com.osframework.framework.utility.*;


public class ArtifactExportModel implements IControlModel {
	
	private String filePath;
	private boolean exportAllArtifacts;
	private boolean exportList;
	private boolean removePrimaryKey;
	private boolean exportRelationships;
	private boolean exportDependencies;
	private boolean importAsChildren;
		
	public Object getModelColDataAt(int modelColRefId) {
		switch (modelColRefId) {
		case ModelColumnFramework.ARTIFACTEXPORTMODELFILEPATH: return getFilePath();
		}
		return null;
	}

	public int getModelColDatabaseDataType(int modelColRefId) {
		switch (modelColRefId) {
		case ModelColumnFramework.ARTIFACTEXPORTMODELFILEPATH: return DatabaseDataTypeFramework.STRING;
		}
		return 0;
	}

	public Object getPrimaryValue() {
		return null;
	}

	public boolean isNew() {
		return true;
	}

	public void setModelColDataAt(Object value, int modelColRefId) {
		switch (modelColRefId) {
		case ModelColumnFramework.ARTIFACTEXPORTMODELFILEPATH: setFilePath((String) value);
		}
	}
	
	public String getFilePath() {
		return filePath;
	}
	
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	public void setReferenceDisplay(IReferenceMap reference, ISecurity security) {
	}

	public boolean isExportAllArtifacts() {
		return exportAllArtifacts;
	}

	public void setExportAllArtifacts(boolean exportAllArtifacts) {
		this.exportAllArtifacts = exportAllArtifacts;
		this.exportList = !exportAllArtifacts;
	}

	public boolean isExportDependencies() {
		return exportDependencies;
	}

	public void setExportDependencies(boolean exportDependencies) {
		this.exportDependencies = exportDependencies;
	}

	public boolean isExportList() {
		return exportList;
	}

	public void setExportList(boolean exportList) {
		this.exportList = exportList;
		this.exportAllArtifacts = !exportList;
	}

	public boolean isExportRelationships() {
		return exportRelationships;
	}

	public void setExportRelationships(boolean exportRelationships) {
		this.exportRelationships = exportRelationships;
	}

	public boolean isRemovePrimaryKey() {
		return removePrimaryKey;
	}

	public void setRemovePrimaryKey(boolean removePrimaryKey) {
		this.removePrimaryKey = removePrimaryKey;
	}

	public boolean isImportAsChildren() {
		return importAsChildren;
	}

	public void setImportAsChildren(boolean importAsChildren) {
		this.importAsChildren = importAsChildren;
	}
	
	
}

