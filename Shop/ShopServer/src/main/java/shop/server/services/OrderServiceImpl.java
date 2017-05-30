package shop.server.services;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.stereotype.Component;

import shop.core.domain.PetOrder;

@Component
public class OrderServiceImpl implements OrderService {

	private LinkedBlockingQueue<PetOrder> orderData = new LinkedBlockingQueue<PetOrder>();
	private Map<Integer, PetOrder> ordersInMemoryStore = new TreeMap<Integer,PetOrder>();
	private Object lock = new Object();
	
	@Override
	public void registerOrder(PetOrder order) {
		synchronized (lock) {
			ordersInMemoryStore.put(order.getOrderNumber(), order);
		}
		
		orderData.offer(order);

	}

	@Override
	public LinkedBlockingQueue<PetOrder> returnOrderData() {
		return orderData;
	}

	@Override
	public void printOrderSummary() {
		System.out.println("total orders received - " + ordersInMemoryStore.values().size());
		Map<String, Integer> orderCount = new HashMap<>();
		for(PetOrder order : ordersInMemoryStore.values()){
			String key = order.getOrderSource() + "-" + order.getStatus() + "-" + order.getStatusReason();
			if(orderCount.get(key) == null){
				orderCount.put(key, 1);
			}else{
				int count = orderCount.get(key);
				orderCount.put(key, ++count);
			}
		}
		orderCount.forEach((k,v) -> System.out.println(k + ":" + v));

	}

	@Override
	public boolean isDupeOrder(int orderNumber) {
		return ordersInMemoryStore.containsKey(orderNumber);
	}
	
}
