package com.osrmt.appclient.artifact.graph;

import java.util.Enumeration;
import java.util.Vector;


public class UIPosition {

	private int currentX = 0;
	private int currentY = 0;
	private int incrementX = 150;
	private int incrementY = 50;
	private Vector graphcomponents = new Vector();
	
	public UIVertex getNextVertex(String label) {
		UIVertex uiv = new UIVertex(label, currentX, currentY);
		currentX += incrementX;
		if (currentX % incrementX == 7) {
			currentX = 0;
			currentY += incrementY;
		}
		graphcomponents.add(uiv);
		return uiv;
	}
	
	public void addEdge(UIVertex v1, UIVertex v2) {
		UIEdge e1 = new UIEdge(v1, v2);
		graphcomponents.add(e1);
	}
	
	public void reset() {
		double minY = getMinY();
		if (minY < 0) {
			Enumeration e1 = graphcomponents.elements();
			while (e1.hasMoreElements()) {
				Object o = e1.nextElement();
				if (o instanceof UIVertex) { 
					UIVertex v = (UIVertex) o;
					v.setYPos(v.getYPos() + (minY*-1));
				}
			}
		}
	}
	
	private double getMinY() {
		double minY = 0;
		Enumeration e1 = graphcomponents.elements();
		while (e1.hasMoreElements()) {
			Object o = e1.nextElement();
			if (o instanceof UIVertex) { 
				UIVertex v = (UIVertex) o;
				if (v.getYPos() < minY) {
					minY = v.getYPos();
				}
			}
		}
		return minY;
	}

	public Vector getGraphcomponents() {
		return graphcomponents;
	}

	public void nextLevel() {
		this.currentY += incrementY;
		this.currentX = 0;
	}

	public int getCurrentX() {
		return currentX;
	}

	public void setCurrentX(int currentX) {
		this.currentX = currentX;
	}

	public int getCurrentY() {
		return currentY;
	}

	public void setCurrentY(int currentY) {
		this.currentY = currentY;
	}

	public int getIncrementX() {
		return incrementX;
	}

	public void setIncrementX(int incrementX) {
		this.incrementX = incrementX;
	}

	public int getIncrementY() {
		return incrementY;
	}

	public void setIncrementY(int incrementY) {
		this.incrementY = incrementY;
	}

	
}


