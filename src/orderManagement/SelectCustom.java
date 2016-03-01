package orderManagement;

public class SelectCustom {
	public String customName;
	public int customCode;

	public SelectCustom(int customCode, String customName) {
		this.customCode = customCode;
		this.customName = customName;
	}
	public SelectCustom(String customName) {

		this.customName = customName;
	}
	public int getCustomCode() {
		return customCode;
	}
	public String getCustomName() {
		return customName;
	}

}
