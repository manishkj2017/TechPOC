package shop.client.services;

import java.math.BigDecimal;
import java.util.Random;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import shop.client.core.ShopChannel;
import shop.client.core.ShopClientProperties;
import shop.client.jms.JMSShopChannel;
import shop.client.rmi.RMIShopChannel;
import shop.client.web.WEBShopChannel;
import shop.core.domain.Pet;
import shop.core.enums.PetTypes;

@Component
public class CustomeServiceImpl implements CustomerService {

	private Random bidPriceGenerator = new Random();
	private Random petChoiceGenerator = new Random();
	
	private  JMSShopChannel jmsCustomer = new JMSShopChannel();
	private RMIShopChannel rmiCustomer = new RMIShopChannel();
	private WEBShopChannel webCustomer = new WEBShopChannel();
	
	private static final Logger genericLog = Logger.getLogger(CustomerService.class);
	
	@Override
	public BigDecimal getBidPrice() {
		int price = bidPriceGenerator.nextInt(PetTypes.getMaxSellingPrice() + ShopClientProperties.getProperties().getMaxBidPriceOffset());
		return new BigDecimal(price);
	}

	@Override
	public PetTypes getPetChoice() {
		return PetTypes.getTypeByNumber(petChoiceGenerator.nextInt(PetTypes.values().length));
	}

	@Override
	public ShopChannel<Pet> getCustomerInterfaceForPets(
			String shopChannel) {
		switch(shopChannel){
			case "RMI":
				return rmiCustomer;
	
			case "JMS":
				return jmsCustomer;
				
			case "WEB":
				return webCustomer;
				
			default:
				return null;
		}
	}

}
