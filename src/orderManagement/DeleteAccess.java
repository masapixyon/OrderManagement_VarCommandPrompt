package orderManagement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteAccess {
	static Connection conn = null;

	public static void Delete(int deleteOrderNumber) {
		try {
			conn = DBconnetion.connectDatabase();

            String sql = "DELETE order_title,order_detail FROM order_title LEFT JOIN order_detail ON order_title.order_no = order_detail.order_no WHERE order_title.order_no = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt( 1,deleteOrderNumber );
            int num = pstmt.executeUpdate();
		}
		catch (SQLException ex) {
			System.out.println( "エラーコード：" + ex.getErrorCode() );
			System.out.println( "SQL状態：" + ex.getSQLState() );
			ex.printStackTrace();
		}
		try {
			if ( conn != null ) {
				conn.close();
			}
		}catch ( SQLException ex ) {
			ex.printStackTrace();
		}
	
	}	
}
