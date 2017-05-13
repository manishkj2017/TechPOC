package shop.core.writers;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.concurrent.TimeUnit;

import shop.core.core.ServiceManager;
import shop.core.core.Shop;
import shop.core.domain.PetOrder;

public class OrderWriter implements Runnable{

	private ServiceManager serviceManager;
	
	

	public OrderWriter(ServiceManager sm) {
		this.serviceManager = sm;
	}
	
	@Override
	public void run() {
		System.out.println("starting order update thread..");
		try(OutputStreamWriter out = new OutputStreamWriter(new BufferedOutputStream(
				new FileOutputStream(Shop.getProperties().getPetStorePath()+Shop.getProperties().getPetOrderFileName())))){
			
			out.write(buildHeader());
			while (true){
				PetOrder order = getServiceManager().getOrderService().returnOrderData().poll(1, TimeUnit.MINUTES);
				if(order == null && !getServiceManager().getInventoryService().isAnyStockAvailable()){
					System.out.println("Exiting order updater");
					out.flush();
					getServiceManager().getOrderService().printOrderSummary();
					return;
				}
				
				if(order != null){
					out.write(buildLineEntry(order));
					getServiceManager().getDao().insertOrder(order);
				}
			}
			
		}catch(IOException | InterruptedException e){
			System.out.println("order updater failed - " + e.getMessage());
		}
		
	}
	
	public ServiceManager getServiceManager() {
		return serviceManager;
	}
	
	private String buildHeader(){
		StringBuffer sb = new StringBuffer();
		sb.append("Source,PetType,OrderStatus,StatusReason,Price,CustomerNo,OrderNo,PetTagNo");
		return sb.toString();
	}
	
	private String buildLineEntry(PetOrder order){
		StringBuffer sb = new StringBuffer();
		sb.append(order.getOrderSource() + ",");
		sb.append(order.getPetType() + ",");
		sb.append(order.getStatus() + ",");
		sb.append(order.getStatusReason() + ",");
		sb.append(order.getBidPrice() + ",");
		sb.append(order.getCustomerNumber() + ",");
		sb.append(order.getOrderNumber() + ",");
		sb.append(order.getPetTag() + "\n");
		return sb.toString();
		
	}

}
