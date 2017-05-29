package shop.server.services;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import shop.core.domain.PetOrder;
import shop.core.domain.PetOrderSummaryData;

public interface OrderService {
	public void registerOrder(PetOrder order);
	public LinkedBlockingQueue<PetOrder> returnOrderData();
	public void printOrderSummary();
	public boolean isDupeOrder(int orderNumber);
	
	
	
}
