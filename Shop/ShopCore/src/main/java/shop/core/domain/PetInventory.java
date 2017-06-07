package shop.core.domain;


public class PetInventory {
	
	private String petType;
	private Integer stockAvailable;
	
	public String getPetType() {
		return petType;
	}
	public void setPetType(String petType) {
		this.petType = petType;
	}
	public Integer getStockAvailable() {
		return stockAvailable;
	}
	public void setStockAvailable(Integer stockAvailable) {
		this.stockAvailable = stockAvailable;
	}
	
}
