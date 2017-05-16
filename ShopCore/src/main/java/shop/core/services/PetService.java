package shop.core.services;

import java.math.BigDecimal;

import shop.core.domain.Pet;
import shop.core.enums.PetTypes;
import shop.core.exceptions.InsufficientPriceException;

public interface PetService {
	public Pet getPet(int petNumber, String name, BigDecimal bidPrice, int customerNumber, int orderNumber) throws InsufficientPriceException;
	public boolean isPriceValid(BigDecimal bidPrice, PetTypes type);
}
