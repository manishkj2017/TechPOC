package shop.server.services;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import junit.framework.TestCase;

import org.junit.Ignore;
import org.junit.Test;

import shop.core.domain.Pet;
import shop.core.domain.PetOrder;
import shop.core.domain.PetOrderSummaryData;
import shop.core.domain.PetSaleSummaryData;
import shop.core.enums.OrderSource;
import shop.core.enums.OrderStatus;
import shop.core.enums.PetTypes;
import shop.server.core.ServiceManager;
import shop.server.core.Shop;
import shop.server.shop.OpenShop;

public class ShopDataServiceImplTest extends TestCase{

	SaleServiceImpl saleService;
	OrderServiceImpl orderService;
	InventoryServiceImpl inventryService;
	ShopDataServiceImpl shopDataService = new ShopDataServiceImpl();
	OpenShop openshop;
	ServiceManager serviceManager;
	Shop shop;
	
	@Override
	public void setUp() throws Exception{
		super.setUp();
		saleService = mock(SaleServiceImpl.class);
		orderService = mock(OrderServiceImpl.class);
		inventryService = mock(InventoryServiceImpl.class);
		serviceManager = mock(ServiceManager.class);
		openshop = mock(OpenShop.class);
		shop = mock(Shop.class);
		OpenShop.shop = shop;
		
		
		
	}
	
	//sale summary grouping
			//select p.name, o.orderSource, count(p.tag), 0, sum(p.price), 0, 0"
					//+ " from Pet p, PetOrder o where p.tag = o.petTag group by p.name, o.orderSource"
	
	@Test
	public void testPetSaleSummaryFromCache(){
	
		LinkedBlockingQueue<Pet> saleInmemoryData = new LinkedBlockingQueue<Pet>();
		LinkedBlockingQueue<PetOrder> orderInmemoryData = new LinkedBlockingQueue<PetOrder>();
		
		preparePetSaleData(saleInmemoryData, orderInmemoryData);
		
		when(shop.getServiceManager()).thenReturn(serviceManager);
		when(serviceManager.getSaleService()).thenReturn(saleService);
		when(serviceManager.getOrderService()).thenReturn(orderService);
		when(saleService.returnSaleData()).thenReturn(saleInmemoryData);
		when(orderService.returnOrderData()).thenReturn(orderInmemoryData);
		
		List<PetSaleSummaryData> summaryData = shopDataService.getPetSaleSummary();
		summaryData.forEach(System.out::println);
		System.out.println(summaryData.size());
		assert summaryData.size() == 2;
		
		
			
	}
	
	@Test
	public void testPetOrderSummaryFromCache(){
		
		
		LinkedBlockingQueue<Pet> saleInmemoryData = new LinkedBlockingQueue<Pet>();
		LinkedBlockingQueue<PetOrder> orderInmemoryData = new LinkedBlockingQueue<PetOrder>();
		
		preparePetSaleData(saleInmemoryData, orderInmemoryData);
		
		when(shop.getServiceManager()).thenReturn(serviceManager);
		when(serviceManager.getSaleService()).thenReturn(saleService);
		when(serviceManager.getOrderService()).thenReturn(orderService);
		when(saleService.returnSaleData()).thenReturn(saleInmemoryData);
		when(orderService.returnOrderData()).thenReturn(orderInmemoryData);
		
		List<PetOrderSummaryData> summaryData = shopDataService.getPetOrderSummary();
		
		summaryData.forEach(System.out::println);
		System.out.println(summaryData.size());
		assert summaryData.size() == 5;
	}
		
	
	private  void preparePetSaleData(LinkedBlockingQueue<Pet> saleInmemoryData, LinkedBlockingQueue<PetOrder> orderInmemoryData){
		Pet pet1 = buildPet(1, PetTypes.Dog.name());
		Pet pet2 = buildPet(2, PetTypes.Dog.name());
		Pet pet3 = buildPet(3, PetTypes.Cat.name());
		
		PetOrder order1 = buildPetOrder(1, OrderSource.RMI.name(), PetTypes.Dog.name(), OrderStatus.Pending.name(), new BigDecimal(2600));
		PetOrder order2 = buildPetOrder(2, OrderSource.RMI.name(), PetTypes.Dog.name(), OrderStatus.Pending.name(), new BigDecimal(600));
		PetOrder order3 = buildPetOrder(3, OrderSource.RMI.name(), PetTypes.Cat.name(), OrderStatus.Pending.name(), new BigDecimal(2700));
		PetOrder order4 = buildPetOrder(4, OrderSource.JMS.name(), PetTypes.Dog.name(), OrderStatus.Rejected.name(), new BigDecimal(1700));
		PetOrder order5 = buildPetOrder(5, OrderSource.WEB.name(), PetTypes.Cow.name(), OrderStatus.Rejected.name(), new BigDecimal(4000));
		PetOrder order6 = buildPetOrder(6, OrderSource.RMI.name(), PetTypes.Dog.name(), OrderStatus.Rejected.name(), new BigDecimal(1800));
		
		linkPetAndOrder(pet1, order1);
		linkPetAndOrder(pet2, order3);
		linkPetAndOrder(pet3, order2);
		
		
		saleInmemoryData.offer(pet1);
		saleInmemoryData.offer(pet2);
		saleInmemoryData.offer(pet3);
		
		orderInmemoryData.offer(order1);
		orderInmemoryData.offer(order2);
		orderInmemoryData.offer(order3);
		orderInmemoryData.offer(order4);
		orderInmemoryData.offer(order5);
		orderInmemoryData.offer(order6);
		
	}
	
	private Pet buildPet(int number, String petType){
		Pet pet = new Pet();
		pet.setId(number);
		pet.setName(petType);
		pet.setTag(number);
		return pet;
	}
	
	private PetOrder buildPetOrder(int number, String orderSource, String petType, String status, BigDecimal bidPrice){
		PetOrder order = new PetOrder();
		order.setCustomerNumber(number);
		order.setId(number);
		order.setOrderNumber(number);
		order.setOrderSource(orderSource);
		order.setPetType(petType);
		order.setStatus(status);
		order.setBidPrice(bidPrice);
		return order;
	}
	
	private void linkPetAndOrder(Pet pet, PetOrder order){
		order.setPetTag(pet.getTag());
		order.setStatus(OrderStatus.Accepted.name());
		order.setPetType(pet.getName());
		pet.setPrice(order.getBidPrice());
		pet.setCustomerNumber(order.getCustomerNumber());
		pet.setOrderNumber(order.getOrderNumber());
	}
	
	
}
