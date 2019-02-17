package com.osrmt.appclient.artifact.graph;

import java.util.Enumeration;
import java.util.Vector;

import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphModel;
import org.jgraph.JGraph;


public class UIGraph extends JGraph {

	private static final long serialVersionUID = 1L;

	public UIGraph(GraphModel model) {
		super(model);

		// Control-drag should clone selection
		setCloneable(true);

		// Enable edit without final RETURN keystroke
		setInvokesStopCellEditing(true);

		// When over a cell, jump to its default port (we only have one, anyway)
		setJumpToDefaultPort(true);
	}
	
	public void insertCells(Vector v) {
		DefaultGraphCell[] cells = new DefaultGraphCell[v.size()];
		Enumeration e1 = v.elements();
		int i=0;
		while (e1.hasMoreElements()) {
			DefaultGraphCell cell = (DefaultGraphCell) e1.nextElement();
			cells[i] = cell;
			i++;
		}
		getGraphLayoutCache().insert(cells);
	}
}
