package shop.server.shop.jms;

import java.util.ArrayList;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.MessageConsumer;

import shop.core.bootstrap.CoreJMSService;
import shop.server.shop.OpenShop;

public class SetupJMS extends CoreJMSService{
	
	
	private static BuyRequestProcessor buyRequestProcessor = new BuyRequestProcessor();
	private static List<MessageConsumer> consumers = new ArrayList <>();
	private static int numberOfConsumers = 1;
	
	public static void initJMS() throws JMSException{
		CoreJMSService.initJMS(null);
		createConsumers();
		connection.start();
		
	}
	
	public static void processBuyRequest() throws JMSException{
		buyRequestProcessor.setShopInterface(OpenShop.shopInterface);
		for(MessageConsumer consumer: consumers){
			consumer.setMessageListener(buyRequestProcessor);
		}
		
	}
	
	
	private static void createConsumers() throws JMSException {
		for(int i=1; i <= numberOfConsumers; i++){
			consumers.add(session.createConsumer(buyTopic));
		}
		
	}
}
