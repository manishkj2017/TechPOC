package shop.client.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import shop.client.services.CustomerService;
import shop.core.domain.Pet;
import shop.core.domain.PetOrder;
import shop.core.enums.OrderStatus;


@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ShopCustomer extends Thread{
	
	private CustomerService customerService;
	private int customerNumber;
	private String shopChannel;
	
	private PetOrder createOrder(){
		PetOrder order = new PetOrder();
		order.setBidPrice(this.getCustomerService().getBidPrice());
		order.setCustomerName("Customer" + this.getCustomerNumber());
		order.setPetType(this.getCustomerService().getPetChoice().name());
		order.setStatus(OrderStatus.Pending.name());
		order.setCustomerNumber(this.getCustomerNumber());
		order.setOrderNumber(this.getCustomerNumber());
		return order;
		
	}
	
	@Override
	public void run(){
		
		try {
			ShopChannel shopChannel = this.getCustomerService().getCustomerInterfaceForPets(this.getShopChannel());
			
			if(shopChannel.isShopClosed()){
				ShopClientProperties.getShopLog().debug("[Customer " + this.getCustomerNumber() + "] : returning as shop is closed");
				Shopping.isShopClosed = true;
				return;
			}
			
			//raise order
			shopChannel.order(createOrder());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public CustomerService getCustomerService() {
		return customerService;
	}

	@Autowired
	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	public int getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(int customerNumber) {
		this.customerNumber = customerNumber;
	}

	public String getShopChannel() {
		return shopChannel;
	}

	public void setShopChannel(String shopChannel) {
		this.shopChannel = shopChannel;
	}
}
