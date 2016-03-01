package orderManagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnetion {
	public static Connection connectDatabase(){
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(
				"jdbc:mysql://127.0.0.1/orderdb?"
				+ "useUnicode=true&characterEncoding=WINDOWS-31J",
				"root", "root");

		}catch (SQLException ex) {
			System.out.println("エラーコード：" + ex.getErrorCode());
			System.out.println("SQL状態：" + ex.getSQLState());
			ex.printStackTrace();
		}
		catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		return conn;
	}
}
