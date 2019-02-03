package com.osrmt.www.artifact;
import java.io.Serializable;
import java.util.*;
import com.osframework.modellibrary.common.*;
import com.osframework.modellibrary.reference.security.*;
import com.osframework.appclient.services.*;
import com.osframework.appclient.ui.listeners.*;
import com.osframework.modellibrary.reference.common.*;
import com.osframework.modellibrary.reference.group.*;
import com.osframework.framework.logging.*;
import com.osframework.framework.utility.*;
import com.osrmt.www.services.*;
import com.osrmt.modellibrary.reqmanager.*;
import javax.servlet.http.*;
import org.apache.struts.*;
import org.apache.struts.action.*;
import com.osframework.appclient.ui.tree.*;
import javax.swing.tree.*;

public class WebArtifact implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int artifactId;
	private String artifactName;
	private int treeLevel;
	private int indent;
	private int parentArtifactId=-1;
	private int artifactRefId = 0;

	public WebArtifact() {

	}
	
	public WebArtifact(int treeLevel, ArtifactModel artifact, int artifactRefId) {
		setArtifactId(artifact.getArtifactId());
		setArtifactName(artifact.getArtifactName());
		setTreeLevel(treeLevel);
		setIndent(treeLevel*20);
		setArtifactRefId(artifactRefId);
	}
	
	public static ArrayList createList(UITreeModel tree) {
		ArrayList list = new ArrayList(1024);
		if (tree != null && tree.getRoot() != null) {
			addChildren(list, (UITreeNode) tree.getRoot(), 0);
		}
		return list;
	}
	
	private static void addChildren(ArrayList list, UITreeNode node, int level) {
		ArtifactModel am = new ArtifactModel();
		if ( node.getUserObject() instanceof ArtifactModel ) {
			am = (ArtifactModel) node.getUserObject();
			list.add(new WebArtifact(level, am, am.getArtifactRefId()));
		} else {
			am.setArtifactName(node.getUserObject().toString());
			list.add(new WebArtifact(level, am, am.getArtifactRefId()));
		}
		level++;
		for (int i=0; i< node.getChildCount(); i++) {
			addChildren(list, (UITreeNode) node.getChildAt(i), level);
		}
		if (node.getChildCount() > 0) {
			WebArtifact wa = new WebArtifact(level, new ArtifactModel(), am.getArtifactRefId());
			wa.setParentArtifactId(am.getArtifactId());
			wa.setArtifactName(am.getArtifactName().toLowerCase());
			list.add(wa);
		}
	}

	public int getArtifactId() {
		return artifactId;
	}

	public void setArtifactId(int artifactId) {
		this.artifactId = artifactId;
	}

	public String getArtifactName() {
		return artifactName;
	}

	public void setArtifactName(String artifactName) {
		this.artifactName = artifactName;
	}

	public int getTreeLevel() {
		return treeLevel;
	}

	public void setTreeLevel(int treeLevel) {
		this.treeLevel = treeLevel;
	}

	public int getIndent() {
		return indent;
	}

	public void setIndent(int indent) {
		this.indent = indent;
	}

	public int getParentArtifactId() {
		return parentArtifactId;
	}

	public void setParentArtifactId(int parentArtifactId) {
		this.parentArtifactId = parentArtifactId;
	}

	public int getArtifactRefId() {
		return artifactRefId;
	}

	public void setArtifactRefId(int artifactRefId) {
		this.artifactRefId = artifactRefId;
	}

	
	
}

