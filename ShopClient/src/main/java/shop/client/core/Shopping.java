package shop.client.core;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.jms.JMSException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import shop.client.config.ApplicationConfig;
import shop.client.jms.JMSSetup;
import shop.core.bootstrap.StartJMSBroker;
import shop.core.core.Shop;
import shop.core.enums.OrderSource;

public class Shopping {
	public static boolean isShopClosed = false;
	public static boolean isShoppingClosed = false;
	private final LocalTime shoppingStartTime = LocalTime.now();
	private Random shopChannelChoice = new Random();
	private ExecutorService executor;
	private final int maxCustomers = new Random().nextInt(Shop.getProperties().getMaxCustomers());
	private int rmiOrders = 0;
	private int jmsOrders = 0;
	private int webOrders = 0;
	
	public static void main(String args[]){
		Shopping shopping = new Shopping();
		shopping.bootStraping();
		shopping.startShopping();
	}
	
	private void startShopping(){
		ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
		executor = Executors.newFixedThreadPool(6);
		
		
		try{
			int customerNumber = 0;
			System.out.println("start shopping...");
			
			//keep shopping until - 
			//1. all customers are not finished (all executor threads are not dead) or 
			//2. shop is not closed or 
			//3. customer numbers exceeds allowed max customer limit
			
			while(!executor.isTerminated() && !isShopClosed && customerNumber < maxCustomers){
				for(int i=1; i < 100; i++) { //allow customers in batch of 100
					
					if(isShopClosed){
						stopShopping(customerNumber);
						break;
					}
					else if(customerNumber >= maxCustomers){
						
						noMoreCustomersPlease(customerNumber);
						isShoppingClosed = true;
						break;
					}
					else{
						ShopCustomer customer = context.getBean(ShopCustomer.class);
						customer.setShopChannel(this.getShopChannel(shopChannelChoice.nextInt(OrderSource.values().length)));
						incrementOrderCount(customer.getShopChannel());
						customer.setCustomerNumber(++customerNumber);
						executor.submit(customer);
					}
				}
			}
			
			System.out.println("RMI orders - " + rmiOrders + ", WEB orders - " + webOrders + ", JMS orders - " + jmsOrders);
			if(executor.isTerminated()){
				System.out.println("All customers are served successfully");
				closure();
				System.exit(0); //main thread does not die on its own due to open RMI thread - no way to catch it and close
			}
				
		}catch(Exception e){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}finally{
			executor.shutdown();
		}
		
	}
	
	private void noMoreCustomersPlease(int customerNumber) {
		System.out.println("max number of customers reached..");
		shutDown(customerNumber);
		
	}

	private void stopShopping(int customerNumber) {
		System.out.println("shop is closed...");
		shutDown(customerNumber);
		
	}
	
	private void incrementOrderCount(String shopChannel){
		if(OrderSource.JMS.name().equals(shopChannel)){
			jmsOrders++;
		}
		if(OrderSource.WEB.name().equals(shopChannel)){
			webOrders++;
		}
		if(OrderSource.RMI.name().equals(shopChannel)){
			rmiOrders++;
		}
	}
	
	private void shutDown(int customerNumber){
		System.out.println("total shop open time - " + Duration.between(shoppingStartTime, LocalTime.now()));
		System.out.println("total customers shopped today - " + customerNumber + " out of " + maxCustomers);
		System.out.println("Awaiting shopping shutdown and clean up");
		
		executor.shutdown();
		try {
			executor.awaitTermination(1, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	 

	private void bootStraping(){
		StartJMSBroker.startBroker(false);
		try {
			JMSSetup.initJMS();
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	
	private String getShopChannel(int channelNumber){
		switch(channelNumber){
		
		case 0:
			return OrderSource.JMS.name();
		
		case 1:
			return OrderSource.RMI.name();
			
		case 2:
			return OrderSource.WEB.name();
		}
		
		return "";
	}
	
	private void closure(){
		JMSSetup.destroyJMS();
	}
}
