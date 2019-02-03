package com.osrmt.datalibrary.reqmanager;

import java.sql.*;
import java.util.*;

import com.osrmt.modellibrary.reference.group.*;
import com.osrmt.modellibrary.reqmanager.*;
import com.osframework.datalibrary.common.*;
import com.osframework.ejb.reference.common.IReferenceMap;
import com.osframework.ejb.reference.security.ISecurity;
import com.osframework.framework.logging.Debug;

/**
null
*/
public class ArtifactDataAdapter extends ArtifactDbAdapter{ 
	
	public ArtifactDataAdapter(IReferenceMap reference, ISecurity security) {
		super(reference, security);
	}	

	public int getNextArtifactNbr(int artifactRefId, int productRefId, DbConnection conn) throws DataAccessException {
		String sql = "select max(artifact_nbr) from artifact "
			+ " where artifact_ref_id = ? and product_ref_id = ?";
		Object max = Db.getSingleValue(sql, Db.getParameter(artifactRefId, productRefId), conn);
		if (max == null) {
			return 1;
		} else {
			return ((Integer)max).intValue()+1;
		}
	}
	
	public ArtifactList getTopLevelArtifacts(int artifactRefId, int productRefId) throws DataAccessException {
		String sql = "select a.* from artifact a"
			+ " where a.active_ind = 1 "
			+ " and a.artifact_ref_id = ?"
			+ " and a.product_ref_id = ?"
			+ " and not a.artifact_level_ref_id = " + ArtifactLevelGroup.COMPONENT 
			+ " and not exists"
			+ " (select rt.child_id from requirement_tree rt, artifact a2"
			+ " where a.artifact_id = rt.child_id"
			+ " and rt.parent_id = a2.artifact_id"
			+ " and rt.relation_ref_id = " + RelationGroup.RELATED
			+ " and a2.artifact_ref_id = ?"
			+ " and a2.product_ref_id = ?)"
			+ " order by a.artifact_seq";
		return getArtifact(sql, Db.getParameter(artifactRefId, productRefId, artifactRefId, productRefId));
	}
	
	public ArtifactList getAllArtifacts(int productRefId) throws DataAccessException {
		String sql = "select * from artifact"
			+ " where active_ind = 1"
			+ " and product_ref_id = ?";
		return getArtifact(sql, Db.getParameter(productRefId));
	}

	public int countAllArtifacts(int productRefId) throws DataAccessException {
		String sql = "select count(artifact_id) from artifact"
			+ " where active_ind = 1"
			+ " and product_ref_id = ?";
		return  Integer.parseInt("" + Db.getSingleValue(sql));
	}

	public ArtifactList getAllArtifacts(int artifactRefId, int productRefId) throws DataAccessException {
		String sql = "select * from artifact"
			+ " where artifact_ref_id = ?"
			+ " and active_ind = 1"
			+ " and product_ref_id = ?"
			+ " and not artifact_level_ref_id = " + ArtifactLevelGroup.COMPONENT;
		return getArtifact(sql, Db.getParameter(artifactRefId, productRefId));
	}

	public ArtifactList getDependentArtifacts(int artifactId) throws DataAccessException {
		String sql = "select a.*"
			+ " from requirement_tree t, artifact a"
			+ " where t.parent_id = ? and t.child_id = a.artifact_id"
			+ "   and not a.artifact_level_ref_id = " + ArtifactLevelGroup.COMPONENT
			+ "   and t.relation_ref_id = " + RelationGroup.DEPENDENT;

		return getArtifact(sql, Db.getParameter(artifactId));
	}

	public ArtifactList getDependentParentArtifacts(int artifactId) throws DataAccessException {
		String sql = "select a.*"
			+ " from requirement_tree t, artifact a"
			+ " where t.parent_id = a.artifact_id and t.child_id = ?"
			+ "   and not a.artifact_level_ref_id = " + ArtifactLevelGroup.COMPONENT
			+ "   and t.relation_ref_id = " + RelationGroup.DEPENDENT;

		return getArtifact(sql, Db.getParameter(artifactId));
	}

	public ArtifactList getRelatedArtifacts(int artifactId, RelationGroup relation) throws DataAccessException {
		String sql = "select a.*"
			+ " from requirement_tree t, artifact a"
			+ " where (t.child_id = ? and t.parent_id = a.artifact_id"
			+ "   and t.relation_ref_id = ?"
			+ "     and not a.artifact_level_ref_id = " + ArtifactLevelGroup.COMPONENT + ")"
			+ " or (t.parent_id = ? and t.child_id = a.artifact_id"
			+ "   and t.relation_ref_id = ?"
			+ "      and not a.artifact_level_ref_id = " + ArtifactLevelGroup.COMPONENT + ")"
			+ " order by a.artifact_seq";

		return getArtifact(sql, Db.getParameter(artifactId, relation.getRelationRefId(), artifactId, relation.getRelationRefId()));
	}

	public boolean isRelated(int fromId, int toId, RelationGroup relation) throws DataAccessException {
		String sql = "select a.*"
				   + " from artifact a, requirement_tree t"
				   + " where (t.child_id = ? and t.parent_id = ? and t.parent_id = a.artifact_id and t.relation_ref_id = ?)"
				   + " or (t.parent_id = ? and t.child_id = ? and t.child_id = a.artifact_id and t.relation_ref_id = ?)";
		ArtifactList list = getArtifact(sql, Db.getParameter(fromId, toId, relation.getRelationRefId(), fromId, toId, relation.getRelationRefId()));
		return list.size() > 0;
	}

	public ArtifactList getStepChildren (int parentId, ArtifactLevelGroup group, ComponentTypeGroup componentGroup) throws DataAccessException {
		String sql = "select a.* from artifact a, requirement_tree r"
			+ " where r.parent_id = ?"
			+ " and r.child_id = a.artifact_id"
			+ " and r.relation_ref_id = " + RelationGroup.RELATED
			+ " and a.artifact_level_ref_id = ?"
			+ " and a.component_type_ref_id = ?"
			+ " order by a.artifact_seq";
		Vector params = new Vector();
		params.add(new Integer(parentId));
		params.add(new Integer(group.getArtifactLevelRefId()));
		params.add(new Integer(componentGroup.getComponentTypeRefId()));
		return this.getArtifact(sql, params);
	}	

	public ArtifactList getSameTypeChildren (int parentTableRefId, int parentId) throws DataAccessException {
		String sql = "select a.* from requirement_tree r, artifact a"
			+ " where r.parent_artifact_ref_id = ?"
			+ " and r.parent_id = ?"
			+ " and r.relation_ref_id = ?"
			+ " and r.child_id = a.artifact_id"
			+ " and r.child_artifact_ref_id = ?"
			+ " and not a.artifact_level_ref_id = " + ArtifactLevelGroup.COMPONENT
			+ " order by a.artifact_seq";
		Vector params = new Vector(4);
		params.add(new Integer(parentTableRefId));
		params.add(new Integer(parentId));
		params.add(new Integer(RelationGroup.RELATED));
		params.add(new Integer(parentTableRefId));
		return this.getArtifact(sql, params);
	}	

	/**
	 * Gets the partially populated child artifacts
	 * 
	 * @param parentTableRefId
	 * @param parentId
	 * @return
	 * @throws DataAccessException
	 */
	public ArtifactList getSameTypeTreeChildren (int parentTableRefId, int parentId) throws DataAccessException {
		String sql = "select a.artifact_id, a.artifact_ref_id, a.artifact_name, a.artifact_seq  "
			+ " from requirement_tree r, artifact a"
			+ " where r.parent_artifact_ref_id = ?"
			+ " and r.parent_id = ?"
			+ " and r.relation_ref_id = ?"
			+ " and r.child_id = a.artifact_id"
			+ " and r.child_artifact_ref_id = ?"
			+ " and not a.artifact_level_ref_id = " + ArtifactLevelGroup.COMPONENT
			+ " order by a.artifact_seq";
		Vector params = new Vector(4);
		params.add(new Integer(parentTableRefId));
		params.add(new Integer(parentId));
		params.add(new Integer(RelationGroup.RELATED));
		params.add(new Integer(parentTableRefId));
		return this.getArtifact(sql, params);
	}	

	public ArtifactList getDependentChildren (int parentTableRefId, int parentId) throws DataAccessException {
		String sql = "select a.* from requirement_tree r, artifact a"
			+ " where r.parent_artifact_ref_id = ?"
			+ " and r.parent_id = ?"
			+ " and r.relation_ref_id = ?"
			+ " and r.child_id = a.artifact_id"
			+ " and not a.artifact_level_ref_id = " + ArtifactLevelGroup.COMPONENT
			+ " order by a.artifact_seq";
		Vector params = new Vector();
		params.add(new Integer(parentTableRefId));
		params.add(new Integer(parentId));
		params.add(new Integer(RelationGroup.DEPENDENT));
		return this.getArtifact(sql, params);
	}	

	public ArtifactList getDependentParents(int childTableRefId, int childId) throws DataAccessException {
		String sql = "select a.* from requirement_tree r, artifact a"
			+ " where r.child_artifact_ref_id = ?"
			+ " and r.child_id = ?"
			+ " and r.relation_ref_id = ?"
			+ " and r.parent_id = a.artifact_id"
			+ " and not a.artifact_level_ref_id = " + ArtifactLevelGroup.COMPONENT
			+ " order by a.artifact_seq";
		Vector<Integer> params = new Vector<Integer>();
		params.add(new Integer(childTableRefId));
		params.add(new Integer(childId));
		params.add(new Integer(RelationGroup.DEPENDENT));
		return this.getArtifact(sql, params);
	}	


	public ArtifactList getStarterArtifacts(int artifactRefId, TraceTreeGroup startWith, int productRefId) throws DataAccessException {
		String tables = "artifact a";
		String sql = " where a.artifact_ref_id = ?"
			+ " and a.active_ind = 1"
			+ " and a.product_ref_id = ?"
			+ " and not a.artifact_level_ref_id = " + ArtifactLevelGroup.COMPONENT;
		Vector params = Db.getParameter(artifactRefId, productRefId);
		switch (startWith.getTraceTreeRefId()) {
			case TraceTreeGroup.STARTALL:	break;
			case TraceTreeGroup.STARTCHILDREN: 
				tables += ", requirement_tree rt";
				sql+= " and a.artifact_id = rt.parent_id and rt.relation_ref_id = " + RelationGroup.DEPENDENT;
				break;
			case TraceTreeGroup.STARTPARENTS: 
				tables += ", requirement_tree rt";
				sql+= " and a.artifact_id = rt.child_id and rt.relation_ref_id = " + RelationGroup.DEPENDENT;
				break;
			case TraceTreeGroup.STARTNOCHILDREN: 
				return getDifference(getStarterArtifacts(artifactRefId, TraceTreeGroup.get(TraceTreeGroup.STARTALL), productRefId),
						getStarterArtifacts(artifactRefId, TraceTreeGroup.get(TraceTreeGroup.STARTCHILDREN), productRefId));
			case TraceTreeGroup.STARTNOPARENTS: 
				return getDifference(getStarterArtifacts(artifactRefId, TraceTreeGroup.get(TraceTreeGroup.STARTALL), productRefId),
						getStarterArtifacts(artifactRefId, TraceTreeGroup.get(TraceTreeGroup.STARTPARENTS), productRefId));
			default: Debug.LogError(this, startWith.toString());
					
		}
		String fullSql = "select a.* from " + tables + sql;
		Hashtable<Integer, Integer> check = new Hashtable<Integer, Integer>(1024);
		Enumeration e1= getArtifact(fullSql, params).elements();
		ArtifactList nodups = new ArtifactList();
		while (e1.hasMoreElements()) {
			ArtifactModel am = (ArtifactModel) e1.nextElement();
			if (!check.containsKey(new Integer(am.getArtifactId()))) {
				nodups.add(am);
				check.put(new Integer(am.getArtifactId()), new Integer(am.getArtifactId()));
			}
		}
		return nodups;
	}
	
	private ArtifactList getDifference(ArtifactList completeList, ArtifactList remove) {
		ArtifactList difference = new ArtifactList();
		Enumeration e1 = completeList.elements();
		while (e1.hasMoreElements()) {
			ArtifactModel am = (ArtifactModel) e1.nextElement();
			Enumeration e2 = remove.elements();
			boolean exclude = false;
			while (e2.hasMoreElements()) {
				ArtifactModel amex = (ArtifactModel) e2.nextElement();
				if (am.getArtifactId() == amex.getArtifactId()) {
					exclude = true;
					break;
				}
			}
			if (!exclude) {
				difference.add(am);
			}
		}
		return difference;
	}

	/**
	 * Update the list of artifact ids sequence
	 * @param list
	 */
	public void updateArtifactSequence(java.util.ArrayList<Integer> list, DbConnection conn) throws Exception {
		String sql = "update artifact set artifact_seq = ? where artifact_id = ?";
		for (int i=0; i< list.size(); i++) {
			Integer artifactId = list.get(i);
			//i+1 so seq  = 1.1 1.2.1 etc
			Db.getAccess().executeUpdate(sql, Db.getParameter(i+1, artifactId.intValue()), conn);
		}
	}
	
	public ArtifactModel getAllSameChildrenLastModified (int parentTableRefId, int parentId) throws DataAccessException {
		String sql = "select max(a.update_dt) as update_dt from artifact a"
			+ " where a.artifact_ref_id = ?";
		Vector params = new Vector(1);
		params.add(new Integer(parentTableRefId));
		return this.getArtifact(sql, params).getFirst();
	}	

	/**
	 * Update the list of artifact ids sequence
	 * @param list
	 */
	public void updateArtifactOutline(ArtifactModel am, String outline) throws Exception {
		String sql = "update artifact set report_outline = ? where artifact_id = ?";
		Vector params = new Vector(2);
		params.add(outline);
		params.add(new Integer(am.getArtifactId()));
		Db.getAccess().executeUpdate(sql, params);
	}
	
	/**
	 * Update the list of artifact ids sequence
	 * @param list
	 */
	public void updateArtifactReportSeq(ArtifactModel am, int seq) throws Exception {
		String sql = "update artifact set report_sequence = ? where artifact_id = ?";
		Vector params = new Vector(2);
		params.add(seq);
		params.add(new Integer(am.getArtifactId()));
		Db.getAccess().executeUpdate(sql, params);
	}
	

}