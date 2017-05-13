package shop.core.services;

import java.math.BigDecimal;

import shop.core.domain.Pet;
import shop.core.enums.PetTypes;

public interface PetService {
	public Pet getPet(int petNumber, String name, BigDecimal bidPrice, int customerNumber);
	public boolean isPriceValid(BigDecimal bidPrice, PetTypes type);
}
