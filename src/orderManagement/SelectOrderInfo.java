package orderManagement;

import java.sql.Date;

public class SelectOrderInfo {
	private int OrderNumber;
	private Date OrderDate;
	private String CustomName;
	private int CustomCode;
	private String SalesName;
	private String SalesCode;

	public SelectOrderInfo(int OrderNumber, Date OrderDate, String CustomName, 
                       int CustomCode,String SalesName, String SalesCode) {
		this.OrderNumber = OrderNumber;
		this.OrderDate = OrderDate;
		this.CustomName = CustomName;
		this.CustomCode = CustomCode;
		this.SalesName = SalesName;
		this.SalesCode = SalesCode;
	}

	public int getOrderNumber() {
		return OrderNumber;
	}

	public Date getOrderDate() {
		return OrderDate;
	}

	public String getCustomName() {
		return CustomName;
	}

	public int getCustomCode() {
		return CustomCode;
	}

	public String getSalesName() {
		return SalesName;
	}

	public String getSalesCode() {
		return SalesCode;
	}

}
