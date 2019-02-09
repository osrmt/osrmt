package com.osrmt.appclient.tools.importfiles;
import java.io.File;
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
import javax.servlet.http.*;
import org.apache.struts.*;
import org.apache.struts.action.*;

import com.osrmt.appclient.services.RequirementServices;
import com.osrmt.modellibrary.reference.group.RelationGroup;
import com.osrmt.modellibrary.reqmanager.*;

public class ImportDirectory {
	
	private static int productId;

	public ImportDirectory() {
		super();
	}
	
	private static String getUsage() {
		return "usage:\n\tImportDirectory <directory path> <artifact ref id> <matchName> <ignoreDirectory> [product ref id]"
			+ "\n\t\te.g. ImportDirectory javadir 132 .java CVS";
	}

	public static void main (String[] args) {
		try {
			if (args.length < 2) {
				System.err.println(getUsage());
			} else {
				int productId = ReferenceServices.getActiveReferenceList(ReferenceGroup.Product).getFirst().getRefId();
				if (args.length > 4) {
					productId = Integer.parseInt(args[4]);
				}
				ImportDirectory.setProductId(productId);
				importFiles(new ArtifactModel(), new File(args[0]), Integer.parseInt(args[1]), args[2], args[3]);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private static void importFiles(ArtifactModel parentArtifact, File file, int artifactRefId, String matchName, String ignoreDirectory) throws Exception {
		try {
			if (!file.isDirectory()) {
				if (!file.getName().matches(matchName)) {
					return;
				}
			} else {
				if (file.getName().matches(ignoreDirectory)) {
					return;
				}
			}
			ArtifactModel artifact = new ArtifactModel();
			artifact.setArtifactName(file.getName());
			artifact.setArtifactRefId(artifactRefId);
			artifact.setProductRefId(productId);
			artifact.setArtifactNbr(file.getAbsolutePath().hashCode());
			artifact.setArtifactId(RequirementServices.UpdateArtifact(artifact).getPrimaryKeyId());
			System.out.println("artifact_id = " + artifact.getArtifactId() + ";");
			
			if (parentArtifact.getArtifactId() != 0) {
				RequirementTreeModel rtm = new RequirementTreeModel();
				rtm.setChildArtifactRefId(artifactRefId);
				rtm.setChildId(artifact.getArtifactId());
				rtm.setParentArtifactRefId(artifactRefId);
				rtm.setParentId(parentArtifact.getArtifactId());
				rtm.setRelationRefId(RelationGroup.RELATED);
				rtm.setRequirementTreeId(RequirementServices.UpdateRelationship(rtm).getPrimaryKeyId());
				System.out.println("requirement_tree_id = " + rtm.getRequirementTreeId() + ";");
			}
			
			if (file.isDirectory()) {
				File[] files = file.listFiles();
				for (int i=0; i < files.length; i++) {
					importFiles(artifact, files[i], artifactRefId, matchName, ignoreDirectory);
				}
			}
		} catch (Exception ex) {
			Debug.LogException("importFiles", ex);
		}
	}

	public static int getProductId() {
		return productId;
	}

	public static void setProductId(int productId) {
		ImportDirectory.productId = productId;
	}

	
	
}

