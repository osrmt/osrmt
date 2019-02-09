package com.osrmt.modellibrary.reqmanager;

import java.util.*;
import java.io.Serializable;
import com.osrmt.modellibrary.reference.group.*;
import com.osframework.ejb.reference.common.IReferenceMap;
import com.osframework.ejb.reference.security.ISecurity;
import com.osframework.modellibrary.common.*;
import com.osframework.modellibrary.reference.common.*;

public class TraceTreeCriteria implements Serializable, IControlModel {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Trace from this set of artifacts
	 */
	private int traceFromArtifactRefId = 0;
	/**
	 * Qualification of artifacts to start the trace with
	 */
	private TraceTreeGroup startTraceWith = TraceTreeGroup.get(TraceTreeGroup.STARTALL);
	/**
	 * Trace down to children or up from parents
	 */
	private TraceTreeGroup traceDirection = TraceTreeGroup.get(TraceTreeGroup.TRACEDOWNTO);
	/**
	 * Restrict the types of artifacts to trace to this list
	 */
	private Vector<ReferenceModel> traceOnlyTo = null;
	/**
	 * Add artifacts multiple times to show all dependencies
	 */
	private boolean allowCircularDependencies = true;
	/**
	 * Constrain the maximum depth of the tree
	 */
	private int maxTreeDepth = 10;
	private static final int ABSOLUTEMAXTREEDEPTH = 100;
	
	public boolean isAllowCircularDependencies() {
		return allowCircularDependencies;
	}
	public void setAllowCircularDependencies(boolean allowCircularDependencies) {
		this.allowCircularDependencies = allowCircularDependencies;
	}
	
	public int getMaxTreeDepth() {
		if (allowCircularDependencies) {
			return maxTreeDepth > ABSOLUTEMAXTREEDEPTH ? ABSOLUTEMAXTREEDEPTH : maxTreeDepth;
		}
		return maxTreeDepth;
	}
	public void setMaxTreeDepth(int maxTreeDepth) {
		this.maxTreeDepth = maxTreeDepth;
	}
	public TraceTreeGroup getStartTraceWith() {
		return startTraceWith;
	}
	public void setStartTraceWith(TraceTreeGroup startTraceWith) {
		this.startTraceWith = startTraceWith;
	}
	public TraceTreeGroup getTraceDirection() {
		return traceDirection;
	}
	public void setTraceDirection(TraceTreeGroup traceDirection) {
		this.traceDirection = traceDirection;
	}
	public int getTraceFromArtifactRefId() {
		return traceFromArtifactRefId;
	}
	public void setTraceFromArtifactRefId(int traceFromArtifactRefId) {
		this.traceFromArtifactRefId = traceFromArtifactRefId;
	}
	public Vector<ReferenceModel> getTraceOnlyTo() {
		return traceOnlyTo;
	}
	public void setTraceOnlyTo(Vector traceOnlyTo) {
		if (traceOnlyTo == null) {
			this.traceOnlyTo = traceOnlyTo;
		} else {
			this.traceOnlyTo  = new Vector<ReferenceModel>();
			Enumeration e1 = traceOnlyTo.elements();
			while (e1.hasMoreElements()) {
				Object o = e1.nextElement();
				if (o instanceof ReferenceModel) {
					this.traceOnlyTo.add(((ReferenceModel) o));
				} else {
					ReferenceModel rm = new ReferenceModel();
					rm.setRefId((Integer) o);
					this.traceOnlyTo.add(rm);
				}
			}
		}
	}
	
	public void updateWith(TraceTreeCriteria criteria) {
		this.setAllowCircularDependencies(criteria.isAllowCircularDependencies());
		this.setMaxTreeDepth(criteria.getMaxTreeDepth());
		this.setStartTraceWith(criteria.getStartTraceWith());
		this.setTraceDirection(criteria.getTraceDirection());
		this.setTraceFromArtifactRefId(criteria.getTraceFromArtifactRefId());
		if (criteria.getTraceOnlyTo() == null) {
			this.setTraceOnlyTo(null);
		} else {
			this.traceOnlyTo = new Vector<ReferenceModel>();
			Enumeration e1 = criteria.getTraceOnlyTo().elements();
			while (e1.hasMoreElements()) {
				Object o = (Object) e1.nextElement();
				this.traceOnlyTo.add((ReferenceModel) o);
			}
		}
	}
	public Object getModelColDataAt(int modelColRefId) {
		switch(modelColRefId) {
		case ModelColumnGroup.TRACETREECRITERIAALLOWCIRCULARDEPENDENCIES: return isAllowCircularDependencies() ? 1 : 0;
		case ModelColumnGroup.TRACETREECRITERIAMAXTREEDEPTH: return new Integer(this.maxTreeDepth);
		case ModelColumnGroup.TRACETREECRITERIASTARTTRACEWITH: return new Integer(this.getStartTraceWith().getTraceTreeRefId());
		case ModelColumnGroup.TRACETREECRITERIATRACEDIRECTION: return new Integer(this.getTraceDirection().getTraceTreeRefId());
		case ModelColumnGroup.TRACETREECRITERIATRACEFROMARITFACTREFID: return new Integer(this.getTraceFromArtifactRefId());
		case ModelColumnGroup.TRACETREECRITERIATRACEONLYTO: return this.getTraceOnlyTo();
		}
		return null;
	}
	public int getModelColDatabaseDataType(int modelColRefId) {
		switch(modelColRefId) {
			case ModelColumnGroup.TRACETREECRITERIAALLOWCIRCULARDEPENDENCIES: return DatabaseDataTypeGroup.INTEGER;
			case ModelColumnGroup.TRACETREECRITERIAMAXTREEDEPTH: return DatabaseDataTypeGroup.INTEGER;
			case ModelColumnGroup.TRACETREECRITERIASTARTTRACEWITH: return DatabaseDataTypeGroup.INTEGER;
			case ModelColumnGroup.TRACETREECRITERIATRACEDIRECTION: return DatabaseDataTypeGroup.INTEGER;
			case ModelColumnGroup.TRACETREECRITERIATRACEFROMARITFACTREFID: return DatabaseDataTypeGroup.INTEGER;
			case ModelColumnGroup.TRACETREECRITERIATRACEONLYTO: return DatabaseDataTypeGroup.INTEGER;
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
		switch(modelColRefId) {
		case ModelColumnGroup.TRACETREECRITERIAALLOWCIRCULARDEPENDENCIES: setAllowCircularDependencies(((Integer)value).equals(new Integer(1))); break;
		case ModelColumnGroup.TRACETREECRITERIAMAXTREEDEPTH: this.setMaxTreeDepth(((Integer)value).intValue()); break;
		case ModelColumnGroup.TRACETREECRITERIASTARTTRACEWITH: setStartTraceWith(TraceTreeGroup.get(((Integer)value).intValue())); break;
		case ModelColumnGroup.TRACETREECRITERIATRACEDIRECTION: setTraceDirection(TraceTreeGroup.get(((Integer)value).intValue())); break;
		case ModelColumnGroup.TRACETREECRITERIATRACEFROMARITFACTREFID: setTraceFromArtifactRefId(((Integer)value).intValue()); break;
		case ModelColumnGroup.TRACETREECRITERIATRACEONLYTO: setTraceOnlyTo((Vector) value); break;
		}
	}
	public void setReferenceDisplay(IReferenceMap reference, ISecurity security) {
	}
	
	@Override
	public String toString() {
		return super.toString() 
		+ "\tMax Depth " + this.getMaxTreeDepth()
		+ "\tCycles " + this.isAllowCircularDependencies()
		+ "\tTrace from " + this.getTraceFromArtifactRefId()
		+ "\tStart with " + this.getStartTraceWith().getTraceTreeRefId()
		+ "\tTrace direction " + this.getTraceDirection().getTraceTreeRefId()
		+ "\tTrace only to " + this.getTraceOnlyTo();
	}
}
