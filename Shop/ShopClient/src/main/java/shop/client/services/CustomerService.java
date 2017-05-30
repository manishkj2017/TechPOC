package shop.client.services;

import java.math.BigDecimal;

import shop.client.core.ShopChannel;
import shop.core.domain.Pet;
import shop.core.enums.PetTypes;

public interface CustomerService {
	public BigDecimal getBidPrice();
	public PetTypes getPetChoice();
	public ShopChannel<Pet> getCustomerInterfaceForPets(String shopChannel);
}
