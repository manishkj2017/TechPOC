package shop.server.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import shop.core.dao.PetDBInterface;
import shop.server.services.InventoryService;
import shop.server.services.OrderService;
import shop.server.services.PetService;
import shop.server.services.SaleService;

@Component
public class ServiceManager {
	
	private PetService petService;
	private OrderService orderService;
	private InventoryService inventoryService;
	private SaleService saleService;
	
	private PetDBInterface dao;
	
	private Thread saleWriter;
	private Thread orderWriter;
	
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

	public Thread getOrderWriter() {
		return orderWriter;
	}

	//@Autowired
	public void setOrderWriter(Thread orderWriter) {
		this.orderWriter = orderWriter;
	}

	public Thread getSaleWriter() {
		return saleWriter;
	}

	//@Autowired
	public void setSaleWriter(Thread saleWriter) {
		this.saleWriter = saleWriter;
	}
	
	
	
}
