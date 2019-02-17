package com.osrmt.appclient.artifact.graph;

/*
 * @(#)HelloWorld.java 3.3 23-APR-04
 * 
 * Copyright (c) 2001-2004, Gaudenz Alder All rights reserved.
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *  
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.*;
import java.text.*;
import javax.swing.event.*;
import javax.swing.text.*;

import com.jgoodies.forms.builder.*;
import com.jgoodies.forms.layout.*;

import com.osframework.appclient.ui.common.GUI;
import com.osframework.appclient.ui.components.*;
import com.osframework.appclient.ui.controls.*;
import com.osframework.modellibrary.common.*;
import com.osframework.modellibrary.reference.security.*;
import com.osframework.appclient.services.*;
import com.osframework.appclient.ui.listeners.*;
import com.osframework.modellibrary.reference.common.*;
import com.osframework.modellibrary.reference.group.*;
import com.osframework.framework.logging.*;
import com.osframework.framework.utility.*;
import com.osrmt.appclient.services.*;
import com.osrmt.modellibrary.reqmanager.*;

import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import org.jgraph.JGraph;
import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.DefaultGraphModel;
import org.jgraph.graph.GraphConstants;
import org.jgraph.graph.GraphModel;

public class TraceabilityGraph {
	
	private Hashtable cycleMap = new Hashtable();
	private Hashtable vertexMap = new Hashtable();
	
	public UIGraph createGraph(int artifactId) {
		GraphModel model = new DefaultGraphModel();
		UIGraph graph  = new UIGraph(model);		
		Vector v = getComponents(artifactId);
		graph.insertCells(v);
		return graph;
	}
	

	public Vector getComponents(int artifactId) {
		Vector v = new Vector();
		UIPosition position = new UIPosition();
		ArtifactModel m = RequirementServices.getArtifact(artifactId);
		UIVertex v1 = position.getNextVertex(m.getDisplayName());
		populateChildren(position, m, v1);
		position.setCurrentX(0);
		position.setCurrentY(0);
		position.setIncrementY(-1 * position.getIncrementY());
		populateParent(position, m, v1);
		position.reset();
		return position.getGraphcomponents();
	}

	private class VertexModel {
		public ArtifactModel artifact;
		public UIVertex vertex;
		public VertexModel(ArtifactModel a, UIVertex v) {
			artifact = a;
			vertex = v;
		}
	}
	private void populateChildren(UIPosition position, ArtifactModel artifact, UIVertex vertex) {
		ArtifactList list = RequirementServices.getDependentItems(artifact.getArtifactId());
		Enumeration e1 = list.elements();
		position.nextLevel();
		Vector vertices = new Vector();
		while (e1.hasMoreElements()) {
			ArtifactModel am = (ArtifactModel) e1.nextElement();
			UIVertex v;
			if (vertexMap.containsKey("id:" + am.getArtifactId())) {
				v = (UIVertex) vertexMap.get("id:" + am.getArtifactId());
			} else {
				v = position.getNextVertex(am.getDisplayName());
				vertexMap.put("id:" + am.getArtifactId(), v);
			}
			if (cycleMap.containsKey(am.getArtifactId() + ":" + artifact.getArtifactId())) {
				continue;
			}
			position.addEdge(vertex, v);
			VertexModel vm = new VertexModel(am, v);
			vertices.add(vm);
			cycleMap.put(am.getArtifactId() + ":" + artifact.getArtifactId(), "");
		}
		Enumeration e2 = vertices.elements();
		while (e2.hasMoreElements()) {
			VertexModel vm = (VertexModel) e2.nextElement();
			populateChildren(position, vm.artifact, vm.vertex);
		}
		
	}
	
	private void populateParent(UIPosition position, ArtifactModel artifact, UIVertex vertex) {
		ArtifactList list = RequirementServices.getDependentParentItems(artifact.getArtifactId());
		Enumeration e1 = list.elements();
		position.nextLevel();
		Vector vertices = new Vector();
		while (e1.hasMoreElements()) {
			ArtifactModel am = (ArtifactModel) e1.nextElement();
			UIVertex v;
			if (vertexMap.containsKey("id:" + am.getArtifactId())) {
				v = (UIVertex) vertexMap.get("id:" + am.getArtifactId());
			} else {
				v = position.getNextVertex(am.getDisplayName());
				vertexMap.put("id:" + am.getArtifactId(), v);
			}
			if (cycleMap.containsKey(am.getArtifactId() + ":" + artifact.getArtifactId())) {
				continue;
			}
			position.addEdge(v, vertex);
			VertexModel vm = new VertexModel(am, v);
			vertices.add(vm);
			cycleMap.put(am.getArtifactId() + ":" + artifact.getArtifactId(), "");
		}
		Enumeration e2 = vertices.elements();
		while (e2.hasMoreElements()) {
			VertexModel vm = (VertexModel) e2.nextElement();
			populateParent(position, vm.artifact, vm.vertex);
		}
		
	}
	
	public static void main (String[] args) {
		TraceabilityGraph trace = new TraceabilityGraph();
		UIGraph graph = trace.createGraph(0);
		JFrame frame = new JFrame();
		frame.setLayout(new BorderLayout());
		frame.getContentPane().add(graph, BorderLayout.CENTER);
		frame.pack();
		frame.setVisible(true);
	}


}