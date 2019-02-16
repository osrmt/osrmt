package com.osrmt.modellibrary.reqmanager;

import java.io.Serializable;


public class TraceModel implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int productRefId;
	private int traceFromArtifactRefId;
	private int traceToArtifactRefId;
	private int traceTypeRefId;
	
	
	public int getProductRefId() {
		return productRefId;
	}
	public void setProductRefId(int productRefId) {
		this.productRefId = productRefId;
	}
	public int getTraceFromArtifactRefId() {
		return traceFromArtifactRefId;
	}
	public void setTraceFromArtifactRefId(int traceFromArtifactRefId) {
		this.traceFromArtifactRefId = traceFromArtifactRefId;
	}
	public int getTraceToArtifactRefId() {
		return traceToArtifactRefId;
	}
	public void setTraceToArtifactRefId(int traceToArtifactRefId) {
		this.traceToArtifactRefId = traceToArtifactRefId;
	}
	public int getTraceTypeRefId() {
		return traceTypeRefId;
	}
	public void setTraceTypeRefId(int traceTypeRefId) {
		this.traceTypeRefId = traceTypeRefId;
	}
	
	

}
