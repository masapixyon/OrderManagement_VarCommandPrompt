package orderManagement;

public class SelectOrderItem {
	private int ItemCode;
	private String ItemName;
	private int ItemQuantity;

	public SelectOrderItem(int ItemCode, String ItemName, int ItemQuantity) {
		this.ItemCode = ItemCode;
		this.ItemName = ItemName;
		this.ItemQuantity = ItemQuantity;
	}

	public int getItemCode() {
		return ItemCode;
	}

	public String getItemName() {
		return ItemName;
	}

	public int getItemQuantity() {
		return ItemQuantity;
	}

}
