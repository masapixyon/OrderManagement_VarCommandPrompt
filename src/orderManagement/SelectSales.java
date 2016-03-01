package orderManagement;

public class SelectSales {
	public String salesCode;
	public String salesName;
	
	public SelectSales(String salesCode, String salesName) {
		this.salesCode = salesCode;
		this.salesName = salesName;
	}
	public String getSalesCode() {
		return salesCode;
	}
	public String getSalesName() {
		return salesName;
	}

}
