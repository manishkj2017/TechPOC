package shop.server.remote;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.concurrent.Semaphore;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import shop.core.domain.Pet;
import shop.core.domain.PetInventory;
import shop.core.domain.PetOrder;
import shop.core.domain.PetOrderSummaryData;
import shop.core.domain.PetSaleSummaryData;
import shop.core.enums.OrderStatus;
import shop.core.enums.PetTypes;
import shop.core.exceptions.InsufficientPriceException;
import shop.core.remote.ShopInterface;
import shop.server.core.ServiceManager;

@Component
public class ShopInterfaceImpl extends UnicastRemoteObject implements ShopInterface {
	
		
	private static final long serialVersionUID = 1L;
	private ServiceManager serviceManager;
	

	private static Logger shopLog = Logger.getLogger("SHOP");
	private static Logger genericLog = Logger.getLogger(ShopInterface.class);
	
	private Semaphore maxClients = new Semaphore(30);

	public ShopInterfaceImpl() throws RemoteException{
		super();
	}
	
	@Autowired
	public ShopInterfaceImpl(ServiceManager sm) throws RemoteException{
		this.serviceManager = sm;
	}
	
	public ServiceManager getServiceManager() {
		return serviceManager;
	}
	
	@Override
	public Pet buyRequest(String petType, String customerName, BigDecimal bidPrice,
			int customerNumber, String orderSource, int orderNumber)
			throws RemoteException {
		
		try{
			maxClients.acquire();
			
			PetOrder order = new PetOrder();
			order.setBidPrice(bidPrice);
			order.setPetType(petType);
			order.setCustomerNumber(customerNumber);
			order.setCustomerName(customerName);
			order.setOrderNumber(orderNumber);
			order.setPetTag(0);
			order.setOrderSource(orderSource);
			
			PetTypes type = PetTypes.getTypeByName(petType);
			
			
			if(type == null){
				order.setDetailStatus(OrderStatus.Invalid.name(), "Pet type is not valid stock");
				
			}
			else if(isShopClosed()){
				order.setDetailStatus(OrderStatus.Rejected.name(), "Shop closed");
				
			}
			else if(!this.getServiceManager().getPetService().isPriceValid(bidPrice, type)){
				order.setDetailStatus(OrderStatus.Rejected.name(), "Price rejected");
				
				this.getServiceManager().getOrderService().registerOrder(order);
				throw new InsufficientPriceException(order.logPrefix() + "price - " + bidPrice + " was not accepted for - " + type.name());
			}
			else if(this.getServiceManager().getInventoryService().isStockAvailable(petType)){
				Pet pet = this.getServiceManager().getPetService().getPet(type.getPetNumber(), type.name(), bidPrice, customerNumber, orderNumber);
				
				this.getServiceManager().getSaleService().updateSale(pet);
				shopLog.debug(order.logPrefix()+ "Pet - " + pet.getName() + " was successfully bought in price - " + bidPrice);
				order.setDetailStatus(OrderStatus.Accepted.name(), "Pet Bought" );
				
				order.setPetTag(pet.getTag());
				this.getServiceManager().getOrderService().registerOrder(order);
				return pet;
			}else{
				shopLog.debug(order.logPrefix() + type.name() + " is out of stock now");
				order.setDetailStatus(OrderStatus.Rejected.name(), "Out of stock");
			}
			this.getServiceManager().getOrderService().registerOrder(order);
			
		}catch(InsufficientPriceException i){
			shopLog.debug(i.getMessage());
		}catch(InterruptedException e){
			e.printStackTrace();
		}finally{
			maxClients.release();
		}
		
		return null;
	}

	@Override
	public boolean isShopClosed() throws RemoteException {
		return !this.getServiceManager().getInventoryService().isAnyStockAvailable();
	}

	@Override
	public Pet getPetByTag(int tag) throws RemoteException {
		return this.getServiceManager().getSaleService().getPetByTag(tag);
	}

	@Override
	public boolean isOrderDupe(int orderNumber) throws RemoteException {
		return this.getServiceManager().getOrderService().isDupeOrder(orderNumber);
	}

	@Override
	public List<PetSaleSummaryData> getPetSaleSummary() throws RemoteException{
		return this.getServiceManager().getDao().getPetSaleSummary();
	}

	@Override
	public List<PetOrderSummaryData> getPetOrderSummary() throws RemoteException{
		return this.getServiceManager().getDao().getPetOrderSummary();
	}

	@Override
	public List<PetInventory> getPetsInventorySummary() throws RemoteException{
		return this.getServiceManager().getDao().getPetsInventory();
	}
	
	

}
