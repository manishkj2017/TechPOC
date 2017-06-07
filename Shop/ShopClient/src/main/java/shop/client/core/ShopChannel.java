package shop.client.core;

import shop.core.domain.PetOrder;

public interface ShopChannel {
	public void order(PetOrder order);
	public boolean isShopClosed();
	
}
