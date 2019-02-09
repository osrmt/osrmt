package com.osrmt.appclient.artifact.graph;

import java.awt.Color;
import java.awt.geom.Rectangle2D;

import javax.swing.BorderFactory;

import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphConstants;

public class UIVertex extends DefaultGraphCell {
	
	private static final long serialVersionUID = 1L;

	private int defaultWidth = 120;
	private int defaultHeight = 20;
	private double xPos = 0;
	private double yPos = 0;
	
	public UIVertex(String name, double x, double y) {
		super(name);
		this.xPos = x;
		this.yPos = y;
		GraphConstants.setBounds(getAttributes(), new Rectangle2D.Double(
				x, y, defaultWidth, defaultHeight));		
		GraphConstants.setBorderColor(getAttributes(), Color.black);
		addPort();
	}
	
	public void setColor(Color bg) {
		// Set fill color
		if (bg != null) {
			GraphConstants.setGradientColor(getAttributes(), bg);
			GraphConstants.setOpaque(getAttributes(), true);
		}
	}
	
	public void setRaised() {
		GraphConstants.setBorder(getAttributes(), BorderFactory
				.createRaisedBevelBorder());
	}

	public double getXPos() {
		return xPos;
	}

	public void setXPos(double pos) {
		xPos = pos;
		GraphConstants.setBounds(getAttributes(), new Rectangle2D.Double(
				xPos, yPos, defaultWidth, defaultHeight));		
	}

	public double getYPos() {
		return yPos;
	}

	public void setYPos(double pos) {
		yPos = pos;
		GraphConstants.setBounds(getAttributes(), new Rectangle2D.Double(
				xPos, yPos, defaultWidth, defaultHeight));		
	}
	
	
}

