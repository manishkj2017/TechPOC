package shop.client.jms;

import javax.jms.JMSException;
import javax.jms.Message;

import shop.client.core.ShopChannel;
import shop.client.core.Shopping;
import shop.core.domain.Pet;
import shop.core.domain.PetOrder;
import shop.core.enums.OrderSource;

public class JMSShopChannel implements ShopChannel<Pet> {

	private static boolean isShopClosed = false;
	
	public JMSShopChannel() {
		new ShopMon().start(); //need to find shop closure in asynchronous way for JMS
	}
	
	@Override
	public void order(PetOrder order) {
		try {
			order.setOrderSource(OrderSource.JMS.name());
			JMSSetup.buyRequest(order);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean isShopClosed() {
		return isShopClosed;
	}
	
	private class ShopMon extends Thread{
		
		@Override
		public void run(){
			System.out.println("JMS shop monitor thread started to keep cheking for close..");
			while (true){
				try {
					if(Shopping.isShoppingClosed)
						return;
					
					Message msg = JMSSetup.getShopCloseMessage();
					if(msg != null){
						System.out.println("JMS - shop close indicator arrived..");
						isShopClosed = true;
						break;
					}
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			System.out.println("JMS Shop Monitor is stopping..");
		}
	}

}
