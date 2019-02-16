package generator;

import java.util.*;

import models.*;
import utilities.FileSystemUtil;

public class GenerateDataAdapter {
	private MainGenerator maingen = null;
	private String nl = "\r\n";
	
	public GenerateDataAdapter(MainGenerator m) {
		this.maingen = m;
	}
	
	public void CreateAdapters() throws java.io.IOException {
		
		String genSourceDir = maingen.props.getProperty(MainGenerator.propGENSRCDIR);
		Enumeration e1 = maingen.getModelLibraryList().elements();
		// each model library has a package of format example
		// com." + MainGenerator.root + ".modellibrary.registration.patient
		// each . will equate to a directory
		while (e1.hasMoreElements()) {
			// Db adapter
			ModelLibraryModel mlm = (ModelLibraryModel) e1.nextElement();
			//if (mlm.getTableInd()==1) {
				String dbSource = getDbSource(mlm);
				String frameworkSourceDir = maingen.props.getProperty(MainGenerator.propFRAMEWORKSRCDIR);
				String javaPath;
				if (mlm.getJavaPackage().indexOf("framework")>0) {
					javaPath = mlm.getJavaPath(frameworkSourceDir).replaceAll("modellibrary","datalibrary");
				} else {
					javaPath = mlm.getJavaPath(genSourceDir).replaceAll("modellibrary","datalibrary");
				}
				FileSystemUtil.CreateDirectory(javaPath);
				FileSystemUtil.CreateFile(javaPath, mlm.getDbAdapterName() + ".java", dbSource);
			String dataSource = getDataSource(mlm);
			frameworkSourceDir = maingen.props.getProperty(MainGenerator.propFRAMEWORKSRCDIR);
			if (mlm.getJavaPackage().indexOf("framework")>0) {
				javaPath = mlm.getJavaPath(frameworkSourceDir).replaceAll("modellibrary","datalibrary");
			} else {
				javaPath = mlm.getJavaPath(genSourceDir).replaceAll("modellibrary","datalibrary");
			}
			FileSystemUtil.CreateDirectory(javaPath);
			if (!FileSystemUtil.Exists(javaPath,  mlm.getDataAdapterName() + ".java")) {
				FileSystemUtil.CreateFile(javaPath, mlm.getDataAdapterName() + ".java", dataSource);
			}
		}
	}
	
	
	public String getDbSource(ModelLibraryModel m) {
		StringBuffer sb = new StringBuffer(1024);
		sb.append(MainGenerator.getLicense(m.getDescription()) + nl
		+ "package " + m.getJavaPackage().replaceAll("modellibrary","datalibrary") + ";" + nl
		+ "" + nl
		+ "import java.sql.*;" + nl
		+ "import java.util.*;" + nl
		+ "import com." + MainGenerator.framework + ".framework.exceptions.*;" + nl
		+ "import com." + MainGenerator.framework + ".framework.logging.*;" + nl
		+ "import com." + MainGenerator.framework + ".datalibrary.common.*;" + nl
		+ "import com." + MainGenerator.framework + ".modellibrary.common.*;" + nl
		+ "import com." + MainGenerator.framework + ".modellibrary.framework.*;" + nl
		+ "import com." + MainGenerator.framework + ".ejb.reference.common.*;" + nl
		+ "import com." + MainGenerator.framework + ".modellibrary.reference.group.*;" + nl
		+ "import com." + MainGenerator.framework + ".ejb.reference.security.*;" + nl
		+ "import " + m.getJavaPackage() + ".*;" + nl);
		Enumeration e1 = maingen.getBusinessMethodList().elements();
		while (e1.hasMoreElements()) {
			BusinessMethodModel bmm = (BusinessMethodModel) e1.nextElement();
			if (bmm.getBean().compareToIgnoreCase(m.getUpdateName())==0
					&& bmm.getModelPackage().compareToIgnoreCase(m.getJavaPackage()) != 0) {
				sb.append("import " + bmm.getModelPackage() + ".*;" + nl);
			}
		}
		String pkeyColumn = maingen.getIndexLibraryList().getPrimaryKey(m.getTableName());
		if (pkeyColumn==null) {
			pkeyColumn = "";
		} else {
			pkeyColumn = pkeyColumn.toLowerCase();
		}
		String pkeyField = m.FormatName(pkeyColumn);
		sb.append(
		  "" + nl
			+ "/**" + nl
			+ m.getDescription() + nl
			+ "*/" + nl
		+ "public class " + m.getDbAdapterName() + " extends BaseAdapter {" + nl
		+ "	" + nl);

		sb.append(nl
		+ "	private IReferenceMap reference;" + nl
		+ "	private ISecurity security;" + nl + nl
		+ "	public " + m.getDbAdapterName() + "(IReferenceMap reference, ISecurity security) {" + nl
		+ "		this.reference = reference;" + nl
		+ "		this.security = security;" + nl
		+ "	}" + nl);

		Enumeration e5 = maingen.getModelLibraryList().elements();
		int nbr = 1;
		while (e5.hasMoreElements()) {
			ModelLibraryModel t = (ModelLibraryModel) e5.nextElement();
			if (t.getParentTable() != null && m.getTableName()!=null) {
				if (t.getParentTable().compareToIgnoreCase(m.getTableName())==0) {
					sb.append("	private " + t.getDataAdapterName() + " da" + nbr + " = new " + t.getDataAdapterName() + "(reference, security);" + nl);
					nbr++;
				}
			}
		}
		if (m.getUserDefinedInd()==1) {
			sb.append("	private RecordExtensionDataAdapter daext = new RecordExtensionDataAdapter(reference, security);" + nl);
		}
		sb.append(nl);
//		sb.append("	public " + m.getDbAdapterName() + "() {" + nl
//		+ "" + nl
//		+ "	}" + nl + nl);
		
		
		if (m.isParent(maingen.getModelLibraryList()) || m.getUserDefinedInd()==1) {
			sb.append(nl + "\tpublic UpdateResult Update" + m.getModelName() + "(" + m.getModelName() + " m, ServiceCall call) throws DataUpdateException {" + nl
					+ "		DbConnection conn = null;" + nl
					+ "		try {" + nl				
					+ "			conn = Db.getConnection();" + nl
					+ "			return Update" + m.getModelName() + "(m,call,conn); " + nl
					+ "		} catch (Exception ex) {" + nl
					+ "			throw new DataUpdateException(ex);" + nl
					+ "		} finally {" + nl
					+ "			if (conn != null) {" + nl
					+ "				conn.close();" + nl
					+ "			}" + nl
					+ "		}" + nl
					+ "	}");					
			sb.append(nl + "\tpublic UpdateResult Update" + m.getModelName() + "(" + m.getModelName() + " m, ServiceCall call, DbConnection conn) throws DataUpdateException {" + nl
					+ "		try { " + nl
			+ "			if (m == null) {" + nl
			+ "				throw new NullArgumentException();" + nl
			+ "			}" + nl
			+ "			UpdateResult result = Update" + m.getClassName() + "(m, call, conn);" + nl 
			+ "			int nbrrows = result.getRowsUpdated();" + nl + nl);
			
			Enumeration e0 = maingen.getModelLibraryList().elements();
			nbr = 1;
			while (e0.hasMoreElements()) {
				ModelLibraryModel t = (ModelLibraryModel) e0.nextElement();
				if (t.getParentTable() != null && m.getTableName()!=null) {
					if (t.getParentTable().compareToIgnoreCase(m.getTableName())==0) {
						sb.append("			Enumeration e"+ nbr + " = m.get" + t.getListName() + "().elements();" + nl
								+ "			while (e"+ nbr + ".hasMoreElements()) {" + nl
								+ "				" + t.getModelName() + " m"+ nbr + " = (" + t.getModelName() + ") e"+ nbr + ".nextElement();" + nl
								+ "				m"+ nbr + "." + t.getSetParentId() + ";"  + nl);

						if (t.isParent(maingen.getModelLibraryList())) {
							sb.append("				nbrrows += da"+ nbr + ".Update" + t.getClassName() + "Model(m"+ nbr + ", call, conn).getRowsUpdated();" + nl);
						} else {
							sb.append("				nbrrows += da"+ nbr + ".Update" + t.getClassName() + "(m"+ nbr + ", call, conn).getRowsUpdated();" + nl);
						}
						sb.append("			}" + nl + nl);						
						nbr++;
					}
				}
			}
			if (m.getUserDefinedInd()==1) {
				sb.append("		Enumeration e1 = m.getRecordExtensionList().elements();" + nl
						+ "			while (e1.hasMoreElements()) {" +nl
						+ "				RecordExtensionModel m1 = (RecordExtensionModel) e1.nextElement();" + nl
						+ "				m1.setTableId(m.getArtifactId());" + nl
						+ "				daext.UpdateRecordExtension(m1, call, conn);" + nl
						+ "			}" + nl);
			}
			sb.append("			return result;" + nl + nl
					+ "		} catch (Exception ex) {" + nl
					+ "			Debug.LogException(this, ex);" + nl
					+ "			throw new DataUpdateException(ex);" + nl
					+ "		}" + nl
					+ "	}" + nl);
		}
		sb.append("" + nl
				+ "	public UpdateResult Update" + m.getClassName() + "(" + m.getModelName() + " m, ServiceCall call) throws DataUpdateException {" + nl
				+ "		DbConnection conn = null;" + nl
				+ "		try {" + nl				
				+ "			conn = Db.getConnection();" + nl
				+ "			return Update" + m.getClassName() + "(m, call, conn);" + nl
				+ "		} catch (Exception ex) {" + nl
				+ "			throw new DataUpdateException(ex);" + nl
				+ "		} finally {" + nl
				+ "			if (conn != null) {" + nl
				+ "				conn.close();" + nl
				+ "			}" + nl
				+ "		}" + nl
				+ "	}" + nl + nl);
		sb.append("" + nl
		+ "	public UpdateResult Update" + m.getClassName() + "(" + m.getModelName() + " m, ServiceCall call, DbConnection conn) throws DataUpdateException {" + nl
		+ "		" + m.getModelName() + " original = null;" + nl
		+ "		try {" + nl
		+ "			if (m == null) {" + nl
		+ "				throw new NullArgumentException();" + nl
		+ "			}" + nl
		+ "			if (m.isNew()) {" + nl
		+ "				m.setCreateUserId(call.getUserId());" + nl
		+ "				m.setCreateDt(new GregorianCalendar());" + nl
		+ "			}" + nl
		+ "			if (m.hasModified()) {" + nl
		+ "				original = get" + m.getClassName() + "(m.get" + pkeyField + "(), conn);" + nl);
		Enumeration e2 = maingen.getTableLibraryList().elements();
		while (e2.hasMoreElements()) {
			TableLibraryModel tlm = (TableLibraryModel) e2.nextElement();
			if (tlm.getTableName().compareToIgnoreCase(m.getTableName())==0) {
				if (tlm.getLowerColumnName().compareToIgnoreCase("update_dt")==0) {
					sb.append("				m.setUpdateDt(new GregorianCalendar());" + nl);
				} else if (tlm.getLowerColumnName().compareToIgnoreCase("update_user_id")==0) {
					sb.append("				m.setUpdateUserId(call.getUserId());" + nl);
				} else if (tlm.getLowerColumnName().compareToIgnoreCase("update_count")==0) {
					sb.append("				m.setUpdateCount(original.getUpdateCount()+1);" + nl);
				} else if (tlm.getLowerColumnName().compareToIgnoreCase("system_assigned_version_nbr")==0) {
					sb.append("				m.setSystemAssignedVersionNbr(" + MainGenerator.SystemVersion + ");" + nl);
				}
			}
		}
		sb.append("				m.copyModifiedTo(original);" + nl
		+ "				UpdateResult result = save(original, conn);" + nl
		+ "				m.set" + pkeyField + "(original.get" + pkeyField + "());" + nl
		+ "				return result;" + nl
		+ "			} else {" + nl
		+ "				return new UpdateResult();" + nl
		+ "			}" + nl
		+ "		} catch (Exception ex) {" + nl
		+ "			if (original != null) {" + nl
		+ "				Debug.LogException(this, ex, original.toString());" + nl
		+ "			} else {" + nl
		+ "				Debug.LogException(this, ex);" + nl
		+ "			}" + nl
		+ "			throw new DataUpdateException(ex); " + nl
		+ "		}" + nl
		+ "	}" + nl
		+ "	" + nl
		+ "	private UpdateResult save(" + m.getModelName() + " m, DbConnection conn) throws SQLException, AccessDataTypeException {" + nl);
		if (m.getTableName().toLowerCase().compareToIgnoreCase("sequence_key")==0) {
			sb.append("		return new UpdateResult();"); 
		} else {
			sb.append("		int nbrRows = 0;" + nl
			+ "		String sql = \"\";" + nl
			+ "		SQLResult result = null;" + nl
			+ "		Vector params = null;" + nl
			+ "		try {" + nl
			+ "			if (m.get" + pkeyField + "()==0) {" + nl
			+ "				m.set" + pkeyField + "(Db.getNextSequence(TableNameFramework." + MainGenerator.getDisplayCode(m.getTableName()) + ", conn));" + nl
			+ "				sql = getInsertSql();" + nl
			+ "				params = getInsertParameters(m);" + nl
			+ "				result = Db.getAccess().executeUpdate(getInsertSql(), getInsertParameters(m), conn);" + nl
			+ "				nbrRows = result.getRowsUpdated();" + nl
			+ "			} else {" + nl
			+ "				sql = getUpdateSql();" + nl
			+ "				params = getInsertParameters(m);" + nl
			+ "				result = Db.getAccess().executeUpdate(getUpdateSql(), getUpdateParameters(m), conn);" + nl
			+ "				nbrRows = result.getRowsUpdated();" + nl
			+ "			}" + nl
			+ "			return new UpdateResult(nbrRows,m.get" + pkeyField + "());" + nl
			+ "		} catch (SQLException ex) {" + nl
			+ "			Debug.LogError(ex.getMessage(), Db.getFormattedException(ex, sql, params));" + nl
			+ "			throw ex;" + nl
			+ "		}" + nl);
		}
		sb.append( "	}" + nl
		+ "		" + nl
		+ "	protected int Import" + m.getClassName() + "(" + m.getModelName() + " m) throws SQLException, AccessDataTypeException {" + nl
		+ "		int nbrRows = 0;" + nl
		+ "		String sql = \"\";" + nl
		+ "		SQLResult result = null;" + nl
		+ "		Vector params = null;" + nl
		+ "		try {" + nl
		+ "			sql = getInsertSql();" + nl
		+ "			params = getInsertParameters(m);" + nl
		+ "			result = Db.getAccess().executeUpdate(getInsertSql(), getInsertParameters(m));" + nl
		+ "			nbrRows = result.getRowsUpdated();" + nl
		+ "			return nbrRows;" + nl
		+ "		} catch (SQLException ex) {" + nl
		+ "			Debug.LogError(ex.getMessage(), Db.getFormattedException(ex, sql, params));" + nl
		+ "			throw ex;" + nl
		+ "		}" + nl
		+ "	}" + nl
		+ "		" + nl
		+ "	public int Delete" + m.getClassName() + "(" + m.getModelName() + " m, DbConnection conn) throws DataUpdateException {" + nl
		+ "		SQLResult result = null;" + nl
		+ "		try {" + nl
		+ "			int nbrRows = 0;" + nl
		+ "			result = Db.getAccess().executeUpdate(getDeleteSql(), getDeleteParameters(m), conn);" + nl
		+ "			nbrRows = result.getRowsUpdated();" + nl
		+ "			return nbrRows;" + nl
		+ "		} catch (Exception ex) {" + nl
		+ "			Debug.LogException(this, ex);" + nl
		+ "			throw new DataUpdateException(ex);" + nl
		+ "		}" + nl
		+ "	}" + nl);
		
		sb.append( "	/**" + nl
		+ "	 * Maps the resultset to the model." + nl
		+ "	 */" + nl
		+ "	public void map" + m.getClassName() + "(ResultSet rset, " + m.getModelName() + " m) throws SQLException {" + nl);
		Enumeration e6 = maingen.getTableLibraryList().elements();
		while (e6.hasMoreElements()) {
			TableLibraryModel tlm = (TableLibraryModel) e6.nextElement();
			if (tlm.getTableName().compareToIgnoreCase(m.getTableName())==0) {
				sb.append("		if (columnExists(rset, \"" + tlm.getColumnName().toLowerCase() + "\")) {" + nl);							
				if (tlm.getJavaType().compareToIgnoreCase("GregorianCalendar")==0) {
					sb.append(
				    "			m." + tlm.getJavaFieldSetter() + "(Db.getCalendarDate(rset.getTimestamp(\"" + tlm.getColumnName().toLowerCase() + "\")));" + nl
				 +  "			if (rset.wasNull()) m." + tlm.getJavaFieldSetter() + "(null);" + nl);
				} else if (tlm.getJavaType().compareToIgnoreCase("int")==0){
					sb.append(
				    "			m." + tlm.getJavaFieldSetter() + "(rset.getInt(\"" + tlm.getColumnName().toLowerCase() + "\"));" + nl
				  + "			if (rset.wasNull()) m." + tlm.getJavaFieldSetter() + "(0);" + nl);
				} else if (tlm.getJavaType().compareToIgnoreCase("double")==0){
					sb.append(
				    "			m." + tlm.getJavaFieldSetter() + "(rset.getDouble(\"" + tlm.getColumnName().toLowerCase() + "\"));" + nl
				  + "			if (rset.wasNull()) m." + tlm.getJavaFieldSetter() + "(0);" + nl);
				} else {
					sb.append(
				    "			m." + tlm.getJavaFieldSetter() + "(rset.getString(\"" + tlm.getColumnName().toLowerCase() + "\"));" + nl
				  + "			if (rset.wasNull()) m." + tlm.getJavaFieldSetter() + "(null);" + nl);
				}		  
						sb.append("		}" + nl);							
			}
		}
		sb.append("		m.resetModified();" + nl);
		sb.append("	}" + nl + nl
				+ " /**" + nl
				+ "  * Execute the SQL and return a list for the result set" + nl
				+ "  */" + nl
				+ "	public " + m.getListName() + "  get" + m.getClassName() + "(String sql) throws DataAccessException {" + nl
				+ "		DbConnection conn = null;" + nl
				+ "		try {" + nl				
				+ "			conn = Db.getConnection();" + nl
				+ "			return get" + m.getClassName() + "(sql, conn);" + nl
				+ "		} catch (Exception ex) {" + nl
				+ "			throw new DataAccessException(ex);" + nl
				+ "		} finally {" + nl
				+ "			if (conn != null) {" + nl
				+ "				conn.close();" + nl
				+ "			}" + nl
				+ "		}" + nl
				+ "	}");					
		sb.append(" /**" + nl
		+ "  * Execute the SQL and return a list for the result set" + nl
		+ "  */" + nl
		+ "	public " + m.getListName() + "  get" + m.getClassName() + "(String sql, DbConnection conn) throws DataAccessException {" + nl
		+ "		SQLResult result = null;" + nl
		+ "		try {" + nl
		+ "			" + m.getListName() + " list = new " + m.getListName() + "();" + nl
		+ "			result = Db.getAccess().executeQuery(sql, conn);" + nl
		+ "			ResultSet rset = result.getRset();" + nl
		+ "			while (rset.next()) {" + nl
		+ "				" + m.getModelName() + " m = new " + m.getModelName() + "();" + nl
		+ "				map" + m.getClassName() + "(rset, m);" + nl
		+ "				list.add(m);" + nl
		+ "			}" + nl);
		if (m.getClassName().equals("Reference") || m.getClassName().equals("Security")) {
			//sb.append("			list.setReferenceDisplay(null, null);" + nl);
		} else {
			sb.append("			list.setReferenceDisplay(reference, security);" + nl);
		}
		sb.append( "			return list;" + nl
		+ "		} catch (SQLException se) {" + nl
		+ "			Debug.LogError(this, Db.getFormattedException(se, sql));" + nl
		+ "			throw new DataAccessException(se);" + nl
		+ "		} finally {" + nl
//29-Aug		+ "			try { if (result!=null) result.getRset().close();} catch (Exception ex) {}" + nl
		+ "			try { if (result!=null) result.closeStatements();} catch (Exception ex) {}" + nl
		+ "		}" + nl
		+ "	}" + nl + nl);
		sb.append(" /**" + nl
				+ "  * Execute the SQL and return a list for the result set" + nl
				+ "  */" + nl
				+ "	public " + m.getListName() + "  get" + m.getClassName() + "(String sql, Vector params) throws DataAccessException {" + nl
				+ "		DbConnection conn = null;" + nl
				+ "		try {" + nl				
				+ "			conn = Db.getConnection();" + nl
				+ "		return get" + m.getClassName() + "(sql, params, conn);" + nl
				+ "		} catch (Exception ex) {" + nl
				+ "			throw new DataAccessException(ex);" + nl
				+ "		} finally {" + nl
				+ "			if (conn != null) {" + nl
				+ "				conn.close();" + nl
				+ "			}" + nl
				+ "		}" + nl
				+ "	}" + nl + nl);
		sb.append(" /**" + nl
		+ "  * Execute the SQL and return a list for the result set" + nl
		+ "  */" + nl
		+ "	public " + m.getListName() + "  get" + m.getClassName() + "(String sql, Vector params, DbConnection conn) throws DataAccessException {" + nl
		+ "		SQLResult result = null;" + nl
		+ "		try {" + nl
		+ "			" + m.getListName() + " list = new " + m.getListName() + "();" + nl
		+ "			result = Db.getAccess().executeQuery(sql, params, conn);" + nl
		+ "			ResultSet rset = result.getRset();" + nl
		+ "			while (rset.next()) {" + nl
		+ "				" + m.getModelName() + " m = new " + m.getModelName() + "();" + nl
		+ "				map" + m.getClassName() + "(rset, m);" + nl
		+ "				list.add(m);" + nl
		+ "			}" + nl
		+ "			list.setReferenceDisplay(reference, security);" + nl
		+ "			return list;" + nl
		+ "		} catch (SQLException se) {" + nl
		+ "			Debug.LogError(this, Db.getFormattedException(se, sql, params));" + nl
		+ "			throw new DataAccessException(se);" + nl
		+ "		} catch (AccessDataTypeException ae) {" + nl
		+ "			throw new DataAccessException(ae);" + nl
		+ "		} finally {" + nl
		+ "			try { if (result!=null) result.closeStatements();} catch (Exception ex) {}" + nl
		+ "		}" + nl
		+ "	}" + nl + nl);
		
		sb.append( "	/**" + nl
				+ "	 * Returns the model from the database." + nl
				+ "	 * Returns an empty model if the id is 0 or not found" + nl
				+ "	 */" + nl
				+ "	public " + m.getModelName() + " get" + m.getClassName() + "(int id) throws DataAccessException { " + nl
				+ "		DbConnection conn = null;" + nl
				+ "		try {" + nl				
				+ "			conn = Db.getConnection();" + nl
				+ "			return get" + m.getClassName() + "(id, conn);" + nl
				+ "		} catch (Exception ex) {" + nl
				+ "			throw new DataAccessException(ex);" + nl
				+ "		} finally {" + nl
				+ "			if (conn != null) {" + nl
				+ "				conn.close();" + nl
				+ "			}" + nl
				+ "		}" + nl					
				+ "	}" + nl + nl);
	
		sb.append( "	/**" + nl
		+ "	 * Returns the model from the database." + nl
		+ "	 * Returns an empty model if the id is 0 or not found" + nl
		+ "	 */" + nl
		+ "	public " + m.getModelName() + " get" + m.getClassName() + "(int id, DbConnection conn) throws DataAccessException { " + nl
		+ "		SQLResult result = null;" + nl
		+ "		try {" + nl
		+ "			" + m.getModelName() + " m = new " + m.getModelName() + "();" + nl
		+ "			// TODO This fails if you actually wanted to get the zero row" + nl
		+ "			if (id > 0) {" + nl
		+ "				result = Db.getAccess().executeQuery(getSelectSql(), Db.getParameter(id), conn);" + nl
		+ "				ResultSet rset = result.getRset();" + nl
		+ "				if (rset.next()) {" + nl
		+ "					map" + m.getClassName() + "(rset, m);" + nl				
		+ "					m.resetModified();" + nl
		+ "				}" + nl
		+ "			}" + nl);
		if (m.getClassName().compareToIgnoreCase("ApplicationUser")!=0
				&& m.getClassName().compareToIgnoreCase("Reference")!=0) {
			sb.append("			m.setReferenceDisplay(reference, security);" + nl);
		}
		sb.append("			return m;" + nl
		+ "		} catch (Exception ex) {" + nl
		+ "			Debug.LogException(this, ex, \"" + m.getModelName() + "\" + id);" + nl
		+ "			throw new DataAccessException(ex);" + nl
		+ "		} finally {" + nl
		+ "			try { if (result!=null) result.closeStatements();} catch (Exception ex) {}" + nl
		+ "		}" + nl
		+ "	}" + nl
		+ "" + nl);
		sb.append("	public " + m.getListName() + " export" + m.getClassName() + "() throws DataAccessException {" + nl
		+ "		int rows = Db.countTable(\"" + m.getTableName().toLowerCase() + "\");" + nl
		+ "		Debug.LogInfo(this, rows + \" found in " + m.getTableName().toLowerCase() + "\");" + nl
		+ "		String sql = \"select * from " + m.getTableName().toLowerCase() + " where record_type_ref_id in (?, ?)\";" + nl
		+ "		" + m.getListName() + " list = this.get" + m.getClassName() + "(sql, Db.getParameter(RecordTypeFramework.SYSTEMREFERENCE, RecordTypeFramework.USERREFERENCE)); " + nl
		+ "		Debug.LogInfo(this,list.size()  + \" " + m.getTableName().toLowerCase() + " rows exported\");" + nl
		+ "		return list;" + nl		
		+ "	}" + nl + nl		
		+ "	public int import" + m.getClassName() + "(" + m.getListName() + " list) throws Exception {" + nl
		+ "		Enumeration e1 = list.elements();" + nl
		+ "		int rows = 0;" + nl
		+ "		while (e1.hasMoreElements()) {" + nl
		+ "			" + m.getModelName() + " m = (" + m.getModelName() + ") e1.nextElement();" + nl
		+ "			rows += Import" + m.getClassName() + "(m);" + nl
		+ "		}" + nl
		+ "		Debug.LogInfo(this, rows + \" reference_group rows imported\");" + nl
		+ "		return rows;" + nl
		+ "	}" + nl + nl);
		sb.append ("	private String getSelectSql() {" + nl
		+ "		return \"select ");
		Enumeration e3 = maingen.getTableLibraryList().elements();
		String delimetedFields = "";
		int cnt =0;
		while (e3.hasMoreElements()) {
			TableLibraryModel tlm = (TableLibraryModel) e3.nextElement();
			if (tlm.getTableName().compareToIgnoreCase(m.getTableName())==0) {
				if (cnt>0){
					sb.append(", ");
					delimetedFields += ", ";
					if (cnt%4==0) {
						sb.append("\"" + nl + "			+ \"");
						delimetedFields += "\"" + nl + "			+ \"";
					}
				} 
				sb.append(tlm.getColumnName().toLowerCase());
				delimetedFields += tlm.getColumnName().toLowerCase();
				cnt++;
			}
		}
		sb.append("\"" + nl + "			+ \" from " + m.getTableName() + " \"" + nl
		+ "			+ \" where " + pkeyColumn + " = ?\";				 " + nl
		+ "	}" + nl
		+ "	" + nl
		+ "	private String getInsertSql() {" + nl
		+ "		return \"insert into " + m.getTableName()
		+ " (" + delimetedFields.toLowerCase() + ")\"");
		sb.append(nl + "			+ \" values (");
		for (int i=0; i<cnt; i++) {
			if (i>0) {
				sb.append(",");
			}
			sb.append("?");
		}
		sb.append(")\";				 " + nl
		+ "	}" + nl
		+ "	" + nl
		+ "	private Vector getInsertParameters(" + m.getModelName() + " m) {" + nl
		+ "		Vector v = new Vector(" + cnt + ");" + nl);
		Enumeration e4 = maingen.getTableLibraryList().elements();
		String updateParam = "";
		String lastParam = "";
		String deleteParam = "";
		while (e4.hasMoreElements()) {
			TableLibraryModel tlm = (TableLibraryModel) e4.nextElement();
			if (tlm.getTableName().compareToIgnoreCase(m.getTableName())==0) {
				if (tlm.getJavaType().compareToIgnoreCase("int")==0) {
					sb.append("		v.add(new Integer(m." + tlm.getJavaFieldGetter() + "()));" + nl);
					if (tlm.getColumnName().compareToIgnoreCase(pkeyColumn)!=0) {
						updateParam += "		v.add(new Integer(m." + tlm.getJavaFieldGetter() + "()));" + nl;
					} else {
						lastParam = "		v.add(new Integer(m." + tlm.getJavaFieldGetter() + "()));" + nl;
						deleteParam = "		v.add(new Integer(m." + tlm.getJavaFieldGetter() + "()));" + nl;
					}
				} else if (tlm.getJavaType().compareToIgnoreCase("double")==0) {
					sb.append("		v.add(new Double(m." + tlm.getJavaFieldGetter() + "()));" + nl);
					updateParam += "		v.add(new Double(m." + tlm.getJavaFieldGetter() + "()));" + nl;
				} else if (tlm.getJavaType().compareToIgnoreCase("GregorianCalendar")==0) {
					sb.append("		if (m." + tlm.getJavaFieldGetter() + "() != null) v.add(m." + tlm.getJavaFieldGetter() + "());" + nl);
					sb.append("		else v.add(new Null(new GregorianCalendar()" + "));" + nl);
					updateParam += "		if (m." + tlm.getJavaFieldGetter() + "() != null) v.add(m." + tlm.getJavaFieldGetter() + "());" + nl;
					updateParam += "		else v.add(new Null(new GregorianCalendar()" + "));" + nl;
				} else {
					sb.append("		if (m." + tlm.getJavaFieldGetter() + "() != null) v.add(m." + tlm.getJavaFieldGetter() + "());" + nl);
					sb.append("		else v.add(new Null(new " + tlm.getJavaType() + "()" + "));" + nl);
					updateParam += "		if (m." + tlm.getJavaFieldGetter() + "() != null) v.add(m." + tlm.getJavaFieldGetter() + "());" + nl;
					updateParam += "		else v.add(new Null(new " + tlm.getJavaType() + "()" + "));" + nl;
			}
			}
		}
		sb.append("		return v;" + nl
		+ "	}" + nl
		+ "	" + nl
		+ "	private String getUpdateSql() {" + nl
		+ "		return \"update " + m.getTableName() + " set " 
		+ delimetedFields.replaceFirst(pkeyColumn + ",","").replaceAll(","," = ?,").toLowerCase().replace("record_type_ \"","record_type_\"") + " = ? \"" + nl);
		sb.append("			+ \" where " + pkeyColumn + " = ?\";				 " + nl
		+ "	}" + nl
		+ "	" + nl
		+ "	private Vector getUpdateParameters(" + m.getModelName() + " m) {" + nl
		+ "		Vector v = new Vector(" + cnt + ");" + nl
		+ updateParam + lastParam
		+ "		return v;" + nl
		+ "	}" + nl
		+ "" + nl
		+ "	private Vector getDeleteParameters(" + m.getModelName() + " m) {" + nl
		+ "		Vector v = new Vector(" + cnt + ");" + nl
		+ deleteParam
		+ "		return v;" + nl
		+ "	}" + nl
		+ "" + nl
		+ "	private String getDeleteSql() {" + nl
		+ "		return \"delete from " + m.getTableName() + " where " + pkeyColumn + " = ?\";				 " + nl
		+ "	}" + nl + nl
		+ "}" + nl);

		return sb.toString();
	}

	public String getDataSource(ModelLibraryModel mlm) {
		StringBuffer sb = new StringBuffer();
		sb.append(MainGenerator.getLicense(mlm.getDescription()) + "package " + mlm.getJavaPackage().replaceAll("modellibrary","datalibrary") + ";" + nl+nl
		+ "import java.sql.*;" + nl 
		+ "import java.util.*;" + nl + nl
		+ "import " + mlm.getJavaPackage() + ".*;" + nl 
		+ "import com." + MainGenerator.framework + ".datalibrary.common.*;" + nl + nl
		+ "/**" + nl
		+ mlm.getDescription() + nl
		+ "*/" + nl
		+ "public class " + mlm.getDataAdapterName() + " extends " + mlm.getDbAdapterName() + "{ " + nl + nl + "}");
		return sb.toString();
	}

}
