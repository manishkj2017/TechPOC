package shop.server.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import shop.server.writers.OrderWriter;
import shop.server.writers.SaleWriter;

@Component
public class Shop {
	
	
	
	private ServiceManager serviceManager;
	
	@Autowired
	Shop(ServiceManager sm){
		this.serviceManager = sm;
	}
	
	public void init(){
		SaleWriter saleWriter = new SaleWriter(getServiceManager());
		OrderWriter orderWriter = new OrderWriter(getServiceManager());
		ShopCloseMonitor shopCloseMonitor = new ShopCloseMonitor(getServiceManager());
		
		if(getServiceManager() != null){
			System.out.println("service manager is ready");
		}
		
		if(getServiceManager().getOrderService() != null){
			System.out.println("order service is ready");
		}
		
		if(getServiceManager().getSaleService() != null){
			System.out.println("sale service is ready");
		}
		
		if(getServiceManager().getPetService() != null){
			System.out.println("pet service is ready");
		}
		
		if(getServiceManager().getInventoryService() != null){
			System.out.println("inventory service is ready");
		}
		
		if(getServiceManager().getShopDataService() != null){
			System.out.println("shop data service is ready");
		}
		
		//build inventory
		getServiceManager().getInventoryService().buildInventory();
		getServiceManager().getInventoryService().printInventory();
		
		Thread saleWriterThread = new Thread(saleWriter);
		saleWriterThread.start();
		getServiceManager().setSaleWriter(saleWriterThread);
		
		Thread orderWriterThread = new Thread(orderWriter);
		orderWriterThread.start();
		getServiceManager().setOrderWriter(orderWriterThread);
		
		shopCloseMonitor.start();
		
	}

	public ServiceManager getServiceManager() {
		return serviceManager;
	}

	
	
	
}
