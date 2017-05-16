package shop.server.shop.jms;

import java.io.IOException;
import java.rmi.RemoteException;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import shop.core.core.Shop;
import shop.core.domain.Pet;
import shop.core.domain.PetOrder;
import shop.core.enums.PetTypes;
import shop.core.remote.ShopInterface;

import com.fasterxml.jackson.databind.ObjectMapper;

public class BuyRequestProcessor implements MessageListener{

	private ShopInterface shopInterface;
	@Override
	public void onMessage(Message message) {
		TextMessage buyRequest = (TextMessage)message;
		try{
			Shop.getJMSLog().debug(buyRequest.getText());
			ObjectMapper mapper  = new ObjectMapper();
			PetOrder order = mapper.readValue(buyRequest.getText(), PetOrder.class);
			PetTypes type = PetTypes.getTypeByName(order.getPetType());
			
			Pet pet = shopInterface.buyRequest(type.name(), order.getCustomerName(), 
					order.getBidPrice(), order.getCustomerNumber(), order.getOrderSource(), order.getOrderNumber());
			
			
			
		}catch (JMSException | IOException  e){
			e.printStackTrace();
		}
		
	}
	
	public void setShopInterface(ShopInterface shopInterface) {
		this.shopInterface = shopInterface;
	}
	
}
