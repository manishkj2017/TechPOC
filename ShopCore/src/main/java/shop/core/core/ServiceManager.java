package shop.core.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import shop.core.dao.PetDBInterface;
import shop.core.services.InventoryService;
import shop.core.services.OrderService;
import shop.core.services.PetService;
import shop.core.services.SaleService;

@Component
public class ServiceManager {
	
	private PetService petService;
	private OrderService orderService;
	private InventoryService inventoryService;
	private SaleService saleService;
	
	private PetDBInterface dao;
	
	private Thread updateSaleThread;
	private Thread updateOrderThread;
	
	public PetService getPetService() {
		return petService;
	}
	
	@Autowired
	public void setPetService(PetService petService) {
		this.petService = petService;
	}
	public OrderService getOrderService() {
		return orderService;
	}
	
	@Autowired
	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}
	public InventoryService getInventoryService() {
		return inventoryService;
	}
	
	@Autowired
	public void setInventoryService(InventoryService inventoryService) {
		this.inventoryService = inventoryService;
	}
	
	public SaleService getSaleService() {
		return saleService;
	}
	
	@Autowired
	public void setSaleService(SaleService saleService) {
		this.saleService = saleService;
	}
	public PetDBInterface getDao() {
		return dao;
	}
	
	@Autowired
	public void setDao(PetDBInterface dao) {
		this.dao = dao;
	}
	
	public Thread getUpdateSaleThread() {
		return updateSaleThread;
	}
	
	@Autowired
	public void setUpdateSaleThread(Thread updateSaleThread) {
		this.updateSaleThread = updateSaleThread;
	}
	
	public Thread getUpdateOrderThread() {
		return updateOrderThread;
	}
	
	@Autowired
	public void setUpdateOrderThread(Thread updateOrderThread) {
		this.updateOrderThread = updateOrderThread;
	}
	
	
	
}
