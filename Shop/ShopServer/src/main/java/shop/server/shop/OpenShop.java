package shop.server.shop;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import shop.core.bootstrap.SystemProperties;
import shop.core.enums.DatabaseTypes;
import shop.core.remote.ShopInterface;
import shop.server.bootstrap.StartH2DB;
import shop.server.config.ApplicationConfig;
import shop.server.core.Shop;
import shop.server.core.ShopServerProperties;
import shop.server.remote.ShopInterfaceImpl;
import shop.server.shop.jms.JMSShop;
import shop.server.shop.rmi.RMIShop;
import shop.server.shop.web.WebShop;

public class OpenShop {
	public static ShopInterface shopInterface;
	public static Shop shop;
	
	public static void main(String args[]){
		
		setProperties();
		
		if(DatabaseTypes.H2MEM.name().equalsIgnoreCase(System.getProperty(SystemProperties.database))){
			//start H2 DB
			new StartH2DB().start();
		}
		
		//initialize spring application context
		ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
		
		//create shop interface for buyers to raise orders
		shopInterface = context.getBean(ShopInterfaceImpl.class);
		
		//start shop services
		shop = context.getBean(Shop.class);
		shop.init();
		
		//open selling desks
		RMIShop rmiShop = new RMIShop();
		rmiShop.start();

		JMSShop jmsShop = new JMSShop();
		jmsShop.start();
		
		WebShop webShop = new WebShop(context);
		webShop.start();
		
	}
	
	private static void setProperties(){
		if(System.getProperty(SystemProperties.WEBHostName) != null){
			ShopServerProperties.setWebhostname(System.getProperty(SystemProperties.WEBHostName));
			
		}
		if(System.getProperty(SystemProperties.JMSHostName) != null){
			ShopServerProperties.setJmshostname(System.getProperty(SystemProperties.JMSHostName));
			System.out.println(ShopServerProperties.getJMSBrokerUrl());
		}
	}
}
