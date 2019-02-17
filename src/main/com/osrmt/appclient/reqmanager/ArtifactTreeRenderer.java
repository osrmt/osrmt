package com.osrmt.appclient.reqmanager;

import java.awt.*;
import java.awt.image.*;
import java.util.Hashtable;

import javax.swing.*;
import javax.swing.tree.*;
import com.osrmt.modellibrary.reqmanager.*;
import com.osframework.appclient.ui.common.*;

public class ArtifactTreeRenderer extends DefaultTreeCellRenderer {


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static ArtifactTreeRenderer artifactTreeRendered = null;
	
	private java.util.Hashtable<Integer, Icon> iconCache = new Hashtable<Integer, Icon>();
	
	private ArtifactTreeRenderer() {
		
	}
	
	public static ArtifactTreeRenderer getInstance() {
		if (artifactTreeRendered == null) {
			artifactTreeRendered = new ArtifactTreeRenderer();
		}
		return artifactTreeRendered;
	}
	
	@Override
	public Component getTreeCellRendererComponent(
                        JTree tree,
                        Object value,
                        boolean sel,
                        boolean expanded,
                        boolean leaf,
                        int row,
                        boolean hasFocus) {
		JLabel label = new JLabel();

        try {
            label = (JLabel) super.getTreeCellRendererComponent(
                    tree, value, sel,
                    expanded, leaf, row,
                    hasFocus);
            Icon icon = getArtifactIcon(value);
            if (icon != null) {
            	label.setIcon(icon);
            }
        	return label;
        } catch (Exception ex) {
        	ex.printStackTrace();
        	return label;
        }
    }

    protected Icon getArtifactIcon(Object value) {
        DefaultMutableTreeNode node =
                (DefaultMutableTreeNode)value;
        if (node.getUserObject() instanceof ArtifactModel) {
            ArtifactModel am =(ArtifactModel)(node.getUserObject());
            if (iconCache.containsKey(am.getArtifactRefId())) {
            	return iconCache.get(am.getArtifactRefId());
            } else {
            	String gifname = com.osframework.appclient.services.ReferenceServices.getDisplay(am.getArtifactRefId()).replace(" ","")  + ".gif";
                Icon icon = GUI.getImageIcon(gifname.toLowerCase(), this);
                if (icon != null) {
                    iconCache.put(am.getArtifactRefId(), icon);
                }
                return icon;
            }
        
        } else {
        	return null;
        }
    }
}
