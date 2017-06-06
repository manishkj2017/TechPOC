package shop.server.services;

import java.util.Collection;
import java.util.concurrent.LinkedBlockingQueue;

import shop.core.domain.PetOrder;

public interface OrderService {
	public void registerOrder(PetOrder order);
	public LinkedBlockingQueue<PetOrder> returnOrderData();
	public void printOrderSummary();
	public boolean isDupeOrder(int orderNumber);
	public Collection<PetOrder> getInMemoryStoreOrdersData();
	
	
}
