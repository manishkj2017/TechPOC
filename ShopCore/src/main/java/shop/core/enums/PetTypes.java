package shop.core.enums;

import java.math.BigDecimal;

public enum PetTypes {
	
	Dog(new BigDecimal(1000), 0),
	Cat(new BigDecimal(400), 1),
	Cow(new BigDecimal(6000), 2),
	Donkey(new BigDecimal(3000), 3);
	
	private BigDecimal price;
	private int petNumber;
	
	PetTypes(BigDecimal price, int petNumber){
		this.price = price;
		this.petNumber = petNumber;
	}
	
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public int getPetNumber() {
		return petNumber;
	}
	public void setPetNumber(int petNumber) {
		this.petNumber = petNumber;
	}
	
	public static PetTypes getTypeByNumber(int petNumber){
		for(PetTypes type: PetTypes.values()){
			if(petNumber == type.getPetNumber())
				return type;
		}
		return null;
	}
	
	public static PetTypes getTypeByName(String name){
		for(PetTypes type: PetTypes.values()){
			if(type.name().equalsIgnoreCase(name))
				return type;
		}
		return null;
	}
	
}
