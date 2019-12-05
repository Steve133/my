package cn.center.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class oracleTest {

	public static void main(String[] args) {
		new oracleTest().testOracle();
	}

	public void testOracle() {
//		OracleDataSource ods = new OracleDataSource();
//		ods.setUser("cardstock");//用户名
//		ods.setPassword("sa");//用户密码
//		ods.setURL("jdbc:oracle:thin:@172.17.11.211:1521/MYSHOPCARD");
//		Connection connection = ods.getConnection();
		Connection con = null;// 创建一个数据库连接
		PreparedStatement pre = null;// 创建预编译语句对象，一般都是用这个而不用Statement
		ResultSet result = null;// 创建一个结果集对象
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("开始尝试连接数据库！");
			String url = "jdbc:oracle:thin:@106.116.113.97:1521:crmsp";
			String user = "dbusrcrmsp";
			String password = "futurecrmsp";
//	        String url = "jdbc:oracle:thin:@192.168.116.128:1521:orcl";
//	        String user = "system";
//	        String password = "sa";
			con = DriverManager.getConnection(url, user, password);
			System.out.println("连接成功！");
			String sql = "select * from ERPFUNCTION where ROWNUM <= ?";
			pre = con.prepareStatement(sql);
			pre.setInt(1, 10);
			result = pre.executeQuery();
			while (result.next()) {
				System.out.println(result.getString("NAME") + "--" + result.getString("CODE"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// 逐一将上面的几个对象关闭，因为不关闭的话会影响性能、并且占用资源
				// 注意关闭的顺序，最后使用的最先关闭
				if (result != null)
					result.close();
				if (pre != null)
					pre.close();
				if (con != null)
					con.close();
				System.out.println("数据库连接已关闭！");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
