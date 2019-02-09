package com.osrmt.appclient.artifact.graph;

import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.GraphConstants;

public class UIEdge extends DefaultEdge {
	
	private static final long serialVersionUID = 1L;

	public UIEdge(UIVertex v1, UIVertex v2) {
		setSource(v1.getChildAt(0));
		setTarget(v2.getChildAt(0));		
		// Set Arrow Style for edge
		int arrow = GraphConstants.ARROW_CLASSIC;
		GraphConstants.setLineEnd(getAttributes(), arrow);
		GraphConstants.setEndFill(getAttributes(), true);
	}

}

