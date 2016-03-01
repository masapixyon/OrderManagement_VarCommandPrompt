package orderManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SelectAccess{

	private ArrayList<SelectOrderInfo> orderList = new ArrayList<>();
	private ArrayList<SelectOrderItem> orderItemList = new ArrayList<>();
	private ArrayList<SelectCustom> customList = new ArrayList<>();
	private ArrayList<SelectSales> salesList = new ArrayList<>();
    private ArrayList<SelectItem> itemList = new ArrayList<>();

	private static PreparedStatement pstmt;
	private static ResultSet resultSet;
	private static Connection conn = null;
	private static Statement stmt = null;

	/**********
	 * 顧客検索
	 * @UseSession Search
	 * @param customName
	 * @return orderList
	 ***********/
	public ArrayList<SelectOrderInfo> getOrderList(String customName) {
		try {
			conn = DBconnetion.connectDatabase();
			pstmt = conn.prepareStatement( "SELECT order_title.order_no, order_title.order_date, order_title.custom_c,custom.custom_nam, order_title.sales_c, sales.sales_nam "
					+ "FROM order_title, custom, sales WHERE (order_title.custom_c = custom.custom_c) AND (order_title.sales_c = sales.sales_c) AND custom.custom_nam LIKE ?" );

			pstmt.setString( 1, "%" + customName + "%" );
			resultSet = pstmt.executeQuery();
			while ( resultSet.next() ) {
				SelectOrderInfo it = new SelectOrderInfo(
						resultSet.getInt("order_no"),
						resultSet.getDate("order_date"),
						resultSet.getString("custom_nam"),
						resultSet.getInt("custom_c"),
						resultSet.getString("sales_nam"),
						resultSet.getString("sales_c"));
				orderList.add( it );
			}
		}
		catch (SQLException ex) {
			System.out.println( "エラーコード：" + ex.getErrorCode() );
			System.out.println( "SQL状態：" + ex.getSQLState() );
			ex.printStackTrace();
		}
		try {
			if ( pstmt != null ) {
				pstmt.close();
			}
		}catch ( SQLException ex ) {
			ex.printStackTrace();
		}
		try {
			if ( resultSet != null ) {
				resultSet.close();
			}
		}catch ( SQLException ex ) {
			ex.printStackTrace();
		}
		return orderList;
	}
	
	/**********
	 * 商品検索
	 * @UseSession Search
	 * @param orderNumber
	 * @return itemList
	 ***********/

	public ArrayList<SelectOrderItem> getOrderItemList(int orderNumber) {
		try {
//			itemList.clear();
			Connection conn = DBconnetion.connectDatabase();
			pstmt = conn.prepareStatement( "SELECT order_detail.quantity,item.item_c, item.item_nam FROM order_title, order_detail, item "
					+ "WHERE (order_title.order_no = order_detail.order_no) AND (order_detail.item_c = item.item_c) AND order_title.order_no = ?");

			pstmt.setInt( 1, orderNumber );
			resultSet = pstmt.executeQuery();
			while ( resultSet.next() ) {
				SelectOrderItem item = new SelectOrderItem(
						resultSet.getInt( "item_c" ),
						resultSet.getString( "item_nam" ),
						resultSet.getInt( "quantity" ) );
				orderItemList.add( item );
			}
		}
		catch (SQLException ex) {
			System.out.println( "エラーコード：" + ex.getErrorCode() );
			System.out.println( "SQL状態：" + ex.getSQLState() );
			ex.printStackTrace();
		}
		try {
			if ( pstmt != null ) {
				pstmt.close();
			}
		}catch ( SQLException ex ) {
			ex.printStackTrace();
		}
		try {
			if ( resultSet != null ) {
				resultSet.close();
			}
		}catch ( SQLException ex ) {
			ex.printStackTrace();
		}
		return orderItemList;
	}


		
		/***********
		 *顧客名検索
		 *@useSession entry
		 */
		public ArrayList<SelectCustom> getCustomResult() {
			Connection conn = null;
			Statement stmt = null;
			ResultSet resultSet = null;
			try {
				conn = DBconnetion.connectDatabase();
				stmt = conn.createStatement();     //データベース（SQL文）を管理する
				resultSet = stmt.executeQuery( "SELECT custom_nam,custom_c FROM custom" );

				while ( resultSet.next() ) {
					SelectCustom sc = new SelectCustom(
							resultSet.getInt( "custom_c"),
							resultSet.getString( "custom_nam" ) );
					customList.add( sc );
				}
			}
			catch (SQLException ex) {
				System.out.println("エラーコード：" + ex.getErrorCode());
				System.out.println("SQL状態：" + ex.getSQLState());
				ex.printStackTrace();
			}

			finally {
				try {
					if (resultSet != null) {
						resultSet.close();
					}
				}
				catch (SQLException ex) {
					ex.printStackTrace();
				}
				try {
					if (stmt != null) {
						stmt.close();
					}
				}
				catch (SQLException ex) {
					ex.printStackTrace();
				}
				try {
					if (conn != null) {
						conn.close();
					}
				}
				catch (SQLException ex) {
					ex.printStackTrace();
				}

			}
			return customList;
		}
		
		
		/***********
		 *担当者名検索
		 *@useSession entry
		 *@return 
		 */
		
		public ArrayList<SelectSales> getSalesResult() {
			try {
				conn = DBconnetion.connectDatabase();
				stmt = conn.createStatement();
				resultSet = stmt.executeQuery( "SELECT sales_nam, sales_c FROM sales" );

				while ( resultSet.next() ) {
					SelectSales ss = new SelectSales(
							resultSet.getString( "sales_c"),
							resultSet.getString( "sales_nam" ) );
					salesList.add( ss );
				}
			}
			catch (SQLException ex) {      //SQLを実行したときに発生した例外事象
				System.out.println("エラーコード：" + ex.getErrorCode());
				System.out.println("SQL状態：" + ex.getSQLState());
				ex.printStackTrace();
			}

			finally {
				try {
					if (resultSet != null) {
						resultSet.close();
					}
				}
				catch (SQLException ex) {
					ex.printStackTrace();
				}
				try {
					if (stmt != null) {
						stmt.close();
					}
				}
				catch (SQLException ex) {
					ex.printStackTrace();
				}
				try {
					if (conn != null) {
						conn.close();
					}
				}
				catch (SQLException ex) {
					ex.printStackTrace();
				}

			}
			return salesList;
		}
		
		
		/********
		 * 商品検索
		 * @return 
		 * @useSession entry
		 */
		public ArrayList<SelectItem> getItemResult() {
			try {
				conn = DBconnetion.connectDatabase();
				stmt = conn.createStatement();     //データベース（SQL文）を管理する
				resultSet = stmt.executeQuery( "SELECT item_nam,item_c FROM item ORDER BY item_nam" );     //resultSetは検索結果

				while ( resultSet.next() ) {
					SelectItem si = new SelectItem(
							resultSet.getInt( "item_c" ),
							resultSet.getString( "item_nam" ) );
					itemList.add( si );
				}
			}
			catch (SQLException ex) {     //SQLを実行したときに発生した例外事象
				System.out.println("エラーコード：" + ex.getErrorCode());
				System.out.println("SQL状態：" + ex.getSQLState());
				ex.printStackTrace();
			}

			finally {
				try {
					if (resultSet != null) {
						resultSet.close();
					}
				}
				catch (SQLException ex) {
					ex.printStackTrace();
				}
				try {
					if (stmt != null) {
						stmt.close();
					}
				}
				catch (SQLException ex) {
					ex.printStackTrace();
				}
				try {
					if (conn != null) {
						conn.close();
					}
				}
				catch (SQLException ex) {
					ex.printStackTrace();
				}

			}
			return itemList;
		}
		


		/************
		 * 
		 * 一連番号から商品コードitem_cを求めるメソッド
		 * @useSession entry
		 * @param inItemNumber
		 * @return itemNumberResult
		 */
		public static int getItemCode( int inItemNumber ){
			try{
				int itemNumberResult = 0;
				conn = DBconnetion.connectDatabase();
				stmt = conn.createStatement();     //データベース（SQL文）を管理する
				resultSet = stmt.executeQuery( "SELECT item_nam,item_c FROM item ORDER BY item_nam" );     //resultSetは検索結果
				int i = 1;
				while ( resultSet.next() ) {
					if( inItemNumber == i )
						itemNumberResult = resultSet.getInt( "item_c" );
					i++;
				}
				return itemNumberResult;

			}catch (SQLException ex) {
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
			return -1;
		}

		
	    /************
	     * 
	     * 商品表の商品コードを使って、商品表を参照して単価を返却するメソッド
	     * @useSession entry
	     * @param itemCode
	     * @return unitPrice
	     */
		public static double getTanka( int itemCode ){
			
			try{
				double unitPrice = 0.0;
				conn = DBconnetion.connectDatabase();
				pstmt = conn.prepareStatement( "select price from item where item_c = ? ;" );

				pstmt.setInt( 1, itemCode );
				resultSet = pstmt.executeQuery();

				while(resultSet.next()){
					unitPrice = resultSet.getDouble( "price" );
				}
				return unitPrice;

			}catch (SQLException ex) {
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
			return -1;
		}
		
		
		
		/**********
		 * 受注削除の番号で受注番号の有無の確認
		 * @useSession deletion
		 * @param deleteOrderNumber
		 * @return decision (true or false)
		 ***********/
		
			public static Boolean DeleteOrderNameShow(int deleteOrderNumber) {
				int deleteNumResult = 0;
				Boolean decision = false;
				try {
					Connection conn = DBconnetion.connectDatabase();

					pstmt = conn.prepareStatement( "select order_no from order_title where order_no =?" );

					pstmt.setInt( 1,deleteOrderNumber );
					resultSet = pstmt.executeQuery();
					while (resultSet.next()) {
						deleteNumResult = resultSet.getInt( "order_no" );
					}
					if(deleteNumResult != 0){
						decision = true;
					}
				}
				catch (SQLException ex) {
					System.out.println( "エラーコード：" + ex.getErrorCode() );
					System.out.println( "SQL状態：" + ex.getSQLState() );
					ex.printStackTrace();
				}
				try {
					if ( pstmt != null ) {
						pstmt.close();
					}
				}catch ( SQLException ex ) {
					ex.printStackTrace();
				}
				try {
					if ( resultSet != null ) {
						resultSet.close();
					}
				}catch ( SQLException ex ) {
					ex.printStackTrace();
				}
				return decision;
			}
			

}
