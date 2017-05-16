package shop.client.web;

import shop.client.core.ShopChannel;
import shop.core.core.Shop;
import shop.core.domain.Pet;
import shop.core.domain.PetOrder;
import shop.core.enums.OrderSource;

public class WEBShopChannel implements ShopChannel<Pet> {

	private static boolean isShopClosed = false;
	@Override
	public void order(PetOrder order) {
		
		try {
			order.setOrderSource(OrderSource.WEB.name());
			Shop.getWEBLog().debug("web order - " + order.toString());
			if(!isShopClosed()){
				WebSetup.buyRequest(order);
			}else{
				Shop.getWEBLog().debug("Web - Shop is closed..");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	@Override
	public boolean isShopClosed() {
		if(isShopClosed)
			return true;
		
		if(WebSetup.isShopClosed()){
			isShopClosed = true;
		}
		
		return isShopClosed;
	}

}
