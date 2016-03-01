package orderManagement;

public class SelectItem {
    private int itemCode;
    private String itemName;
	public SelectItem(int itemCode, String itemName) {
        this.itemCode = itemCode;
        this.itemName = itemName;
	}

	public int getItemCode() {
		return itemCode;
	}
	
	public String getItemName() {
		return itemName;
	}
}
