package orderManagement;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;

public class InsertAccess {
	static Connection conn = null;
	static PreparedStatement stmt = null;
	static Statement stmtC = null;

	/*********
     * order_titleに登録
	 * @param rQuantity 
	 * @param rItemCode 
     ********/

	public static void insertOT(Calendar cal, int inOrderNumber, String inSalesNumber,
			ArrayList<Integer> itemCodeList, ArrayList<Integer> quantityList, double totalItemPrice, double consumptionTax, double requestMoney) {
		try {
			Connection conn = DBconnetion.connectDatabase();
			stmt = conn.prepareStatement( "insert into order_title(order_date,custom_c,sales_c,total_amount,sales_tax,bill) values(?,?,?,?,?,?)", stmt.RETURN_GENERATED_KEYS );

			Date date = new java.sql.Date( cal.getTime().getTime() );
			
			
			stmt.setDate( 1, date );
			stmt.setInt( 2, inOrderNumber );
			stmt.setString( 3, inSalesNumber );
			stmt.setDouble( 4, totalItemPrice );      //合計金額
			stmt.setDouble( 5, consumptionTax );      //消費税額
			stmt.setDouble( 6, requestMoney );        //請求金額		
			stmt.execute();
			
			ResultSet orderCode = stmt.getGeneratedKeys();
			orderCode.next();
			stmt = conn.prepareStatement( "insert into order_detail(order_no,item_c,quantity) values(?,?,?);" );
			for(int i = 0; i < itemCodeList.size(); i++){
					stmt.setInt(1, orderCode.getInt(1));
					stmt.setInt(2, itemCodeList.get(i));
					stmt.setInt(3, quantityList.get(i));
					stmt.executeUpdate();
			}
		}
		catch (SQLException ex) {
			System.out.println("エラーコード：" + ex.getErrorCode());
			System.out.println("SQL状態：" + ex.getSQLState());
			ex.printStackTrace();
		}
		try {
			if (stmt != null) {
				stmt.close();
			}
		}catch (SQLException ex) {
			ex.printStackTrace();
		}
		try {
			if (conn != null) {
				conn.close();
			}
		}catch (SQLException ex) {
			ex.printStackTrace();
		}
		
	}
}
