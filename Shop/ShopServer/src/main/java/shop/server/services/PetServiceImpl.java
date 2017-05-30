package shop.server.services;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import shop.core.domain.Pet;
import shop.core.enums.PetTypes;
import shop.core.exceptions.InsufficientPriceException;
import shop.server.core.ShopServerProperties;

@Component
public class PetServiceImpl implements PetService {
	
	@Override
	public Pet getPet(int petNumber, String name, BigDecimal bidPrice,
			int customerNumber, int orderNumber) throws InsufficientPriceException{
		
		PetTypes type = PetTypes.getTypeByNumber(petNumber);
		ShopServerProperties.getShopLog().debug("[Customer - " + customerNumber + "][Order - " + orderNumber + "] : purchase request received for : " + type.name() + " for price - " + bidPrice );
		if(!isPriceValid(bidPrice, type)){
			String insufficientPriceLogMsg = "[Customer - " + customerNumber + "][Order - " + orderNumber + "] : order rejected as bid price is not sufficient";
			ShopServerProperties.getShopLog().debug(insufficientPriceLogMsg);
			throw new InsufficientPriceException(insufficientPriceLogMsg);
		}
		
		Pet pet = new Pet();
		pet.setPrice(bidPrice);
		pet.setName(name);
		pet.setOrderNumber(orderNumber);
		pet.setCustomerNumber(customerNumber);
		
		return pet;
	
	}

	@Override
	public boolean isPriceValid(BigDecimal bidPrice, PetTypes type) {
		if(type.getPrice().subtract(bidPrice).intValue() > 0)
			return false;
		
		return true;
	}

}
