package shop.core.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
/*@NamedQueries({
	@NamedQuery(name=PetInventory.ALL_PETS_INVENTORY, 
			query="select i from PetInventory i")
					
})*/
public class PetInventory {
	
	public static final String ALL_PETS_INVENTORY = "allPetsInventory";
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
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
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
}
