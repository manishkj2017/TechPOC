package shop.server.shop;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import shop.core.bootstrap.StartH2DB;
import shop.core.core.Shop;
import shop.core.remote.ShopInterface;
import shop.core.remote.ShopInterfaceImpl;
import shop.server.config.ApplicationConfig;
import shop.server.shop.jms.JMSShop;
import shop.server.shop.rmi.RMIShop;
import shop.server.shop.web.WebShop;

public class OpenShop {
	public static ShopInterface shopInterface;
	
	public static void main(String args[]){
		
		//start H2 DB
		new StartH2DB().start();
		
		//initialize spring application context
		ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
		
		//create shop interface for buyers to raise orders
		shopInterface = context.getBean(ShopInterfaceImpl.class);
		
		//start shop services
		Shop shop = context.getBean(Shop.class);
		shop.init();
		
		//open selling desks
		RMIShop rmiShop = new RMIShop();
		rmiShop.start();

		JMSShop jmsShop = new JMSShop();
		jmsShop.start();
		
		WebShop webShop = new WebShop(context);
		webShop.start();
		
	}
}
