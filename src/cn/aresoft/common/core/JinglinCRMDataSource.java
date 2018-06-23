package cn.aresoft.common.core;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.puff.jdbc.core.DataBase;
import com.puff.jdbc.core.Procedure;
import com.puff.jdbc.core.ProcedureOut;
import com.puff.jdbc.executor.SQLReport;
import com.puff.jdbc.executor.SimpleExecutor;

public class JinglinCRMDataSource {
	
	public static final SimpleExecutor executor = SimpleExecutor.getInstance("jinglincrm");

	public static Map<String, Object> callProc(Procedure procedure, String name) throws Exception {
		DataBase dataBase = executor.getDataBase();
		Connection conn = dataBase.getConnection();
		CallableStatement cs = null;
		Map<String, Object> map = new HashMap<String, Object>();
		boolean[] sqlRunErr = new boolean[] { false };
		try {	
			List<Object> in = procedure.getIn();
			List<ProcedureOut> out = procedure.getOut();
				String procName = getName(procedure, name);
				cs = conn.prepareCall(procName);
				int idx = 1;
				int startIdx = 1;
				if (out != null && out.size() > 0) {
					for (int i = 0, size = out.size(); i < size; i++) {
						System.out.println("registerOutParameter , idx :" + idx + ", type :" + out.get(i).getJdbcType().getType());
						cs.registerOutParameter(idx++, out.get(i).getJdbcType().getType());
					}
				}
				if (in != null && in.size() > 0) {
					for (int j = 0, size = in.size(); j < size; j++) {
						System.out.println("set in , idx :" + idx + ", value :" + in.get(j));
						cs.setObject(idx++, in.get(j));
					}
				}
				long elapsed = System.currentTimeMillis();
				execute(cs, sqlRunErr);
				if (out != null && out.size() > 0) {
					for (int i = 0, size = out.size(); i < size; i++) {
						map.put(out.get(i).getName(), cs.getObject(startIdx++));
					}
				}
				sqlProcedureReport(procName, elapsed, procedure.getIn());
			} catch (Exception e) {
				throw e;
			} finally {
				dataBase.close(cs, conn);
			}
		return map;
	}
	private static boolean execute(PreparedStatement pst, boolean[] sqlRunErr) throws SQLException {
		try {
			return pst.execute();
		} catch (SQLException e) {
			sqlRunErr[0] = true;
			throw e;
		}
	}
	
	private static void sqlProcedureReport(String sql, long elapsed, List<Object> param) {
		SQLReport.report(SQLReport.SQL_PROCEDURE_ELAPSED, new Object[] { sql, new Long(System.currentTimeMillis() - elapsed) });
		if (param != null && param.size() > 0) {
			SQLReport.parameter(SQLReport.SQL_PARAMETER, param);
		}
	}

	public static String getName(Procedure proc, String name) {
		int size = 0;
		if (proc.getIn() != null) {
			size += proc.getIn().size();
		}
		StringBuilder sb = new StringBuilder();
		if (proc.getOut() != null && proc.getOut().size() > 0) {
			sb.append("{ ? = call ");
		} else {
			sb.append("{ call ");
		}
		sb.append(name).append("(");
		for (int i = 0; i < size; i++) {
			sb.append("?");
			if (i < size - 1) {
				sb.append(",");
			}
		}
		sb.append(") ");
		sb.append("} ");
		//System.out.println("size : " + proc.getIn().size());
		//System.out.println("Procedure name  : " + sb.toString());
		return sb.toString(); // {call PROC_XXX(?,?,?,?,?,?,?,?)}
	}

}