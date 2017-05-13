package shop.core.services;

import java.util.concurrent.LinkedBlockingQueue;

import shop.core.domain.PetOrder;

public interface OrderService {
	public void registerOrder(PetOrder order);
	public LinkedBlockingQueue<PetOrder> returnOrderData();
	public void printOrderSummary();
	public boolean isDupeOrder(int orderNumber);
	
}
