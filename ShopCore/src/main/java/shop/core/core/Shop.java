package shop.core.core;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import shop.core.bootstrap.SystemProperties;
import shop.core.writers.OrderWriter;
import shop.core.writers.SaleWriter;

@Component
public class Shop {
	
	private static SystemProperties properties = new SystemProperties();
	private static Logger shopLog  = Logger.getLogger("SHOP");
	private static Logger jmsLog = Logger.getLogger("JMS");
	private static Logger webLog = Logger.getLogger("WEB");
	private static Logger rmiLog = Logger.getLogger("RMI");
	
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

	public static SystemProperties getProperties() {
		return properties;
	}

	public static void setProperties(SystemProperties properties) {
		Shop.properties = properties;
	}

	public static Logger getShopLog() {
		return shopLog;
	}
	public static Logger getJMSLog() {
		return jmsLog;
	}
	public static Logger getWEBLog() {
		return webLog;
	}
	public static Logger getRMILog() {
		return rmiLog;
	}
	
	
	
}
