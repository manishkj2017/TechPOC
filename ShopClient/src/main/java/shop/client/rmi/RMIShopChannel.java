package shop.client.rmi;

import java.rmi.RemoteException;

import shop.client.core.ShopChannel;
import shop.core.core.Shop;
import shop.core.domain.Pet;
import shop.core.domain.PetOrder;
import shop.core.enums.OrderSource;

public class RMIShopChannel implements ShopChannel<Pet> {

	private static boolean isShopClosed = false;
	@Override
	public void order(PetOrder order) {
		try {
			order.setOrderSource(OrderSource.RMI.name());
			RMISetup.getRemoteShop().buyRequest(order.getPetType(), order.getCustomerName(), order.getBidPrice(), 
					order.getCustomerNumber(), order.getOrderSource(), order.getOrderNumber());
			Shop.getRMILog().debug("RMI order - " + order.toString());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public boolean isShopClosed() {
		try{
			if(isShopClosed){ //check this first in case previous thread already set this..
				return true;
			}
			return RMISetup.getRemoteShop().isShopClosed(); //try remote call
		}catch(RemoteException e){
			Shop.getRMILog().debug("Remote request failed - shop has closed probably");
			isShopClosed = true;
			
		}
		return true;
	}

}
