package cn.center.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class mysqlTest {

	public static void main(String arg[]) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Connection con = null; // 定义一个MYSQL链接对象
		Class.forName("com.mysql.cj.jdbc.Driver").newInstance(); // MYSQL驱动
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/taotao2?characterEncoding=utf-8&useSSL=false", "root", "root"); // 链接本地MYSQL
		System.out.print("yes");
		con.close();
	}
}
