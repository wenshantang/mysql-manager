package cn.aresoft.manager.plugin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.puff.framework.parse.XNode;
import com.puff.framework.parse.XPathParser;
import com.puff.framework.utils.PathUtil;
import com.puff.jdbc.core.DBType;
import com.puff.jdbc.core.DataBase;
import com.puff.jdbc.core.DbManager;
import com.puff.jdbc.executor.SimpleExecutor;

public class DataInit {
	public void checkDB() {
		Map<String, Map<String, String>> tables = new HashMap<String, Map<String, String>>();
		XPathParser parser = new XPathParser(PathUtil.fromJar("db/table.xml"));
		List<XNode> evalNodes = parser.evalNodes("table");
		for (XNode xNode : evalNodes) {
			String name = xNode.getStringAttribute("name");
			XNode mysql = xNode.evalNode("mysql");
			XNode oracle = xNode.evalNode("oracle");
			Map<String, String> ddl = new HashMap<String, String>();
			ddl.put("mysql", mysql.getStringBody());
			ddl.put("oracle", oracle.getStringBody());
			tables.put(name, ddl);
		}
		SimpleExecutor executor = SimpleExecutor.getInstance();
		DataBase db = DbManager.getDataBase();
		for (Entry<String, Map<String, String>> entry : tables.entrySet()) {
			String tableName = entry.getKey();
			boolean exist = executor.tableExist(tableName);
			if (!exist) {
				DBType dbType = db.getDbType();
				String type = dbType.toString().toLowerCase();
				String sql = entry.getValue().get(type);
				executor.execute(sql);
			}
		}

	}
}
