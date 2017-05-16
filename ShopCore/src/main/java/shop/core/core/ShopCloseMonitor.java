package shop.core.core;

import javax.jms.JMSException;

import shop.core.bootstrap.CoreJMSService;
import javassist.tools.rmi.RemoteException;

public class ShopCloseMonitor extends Thread{
	private ServiceManager serviceManager;
	
	public ShopCloseMonitor(ServiceManager sm) {
		this.serviceManager = sm;
	}
	
	@Override
	public void run(){
		System.out.println("starting shop close monitor..");
		
		while(true){
			if(!this.getServiceManager().getInventoryService().isAnyStockAvailable()){
				try{
					performShopClosure();
				}catch(RemoteException r){
					r.printStackTrace();
				}
			}
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	

	public ServiceManager getServiceManager() {
		return serviceManager;
	}
	
	private void performShopClosure() {
		System.out.println("All stock finished - closing the shop..");
		
		try {
			CoreJMSService.produceShopCloseMessage();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			
			System.out.println("Pending sale writes - " + this.getServiceManager().getSaleService().returnSaleData().size());
			System.out.println("Pending order writes - " + this.getServiceManager().getOrderService().returnOrderData().size());
			
			this.getServiceManager().getSaleWriter().join();
			this.getServiceManager().getOrderWriter().join();
			
			this.getServiceManager().getSaleService().printSaleSummary();
			this.getServiceManager().getInventoryService().printInventory();
			this.getServiceManager().getOrderService().printOrderSummary();
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
