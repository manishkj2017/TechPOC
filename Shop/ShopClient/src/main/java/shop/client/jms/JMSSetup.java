package shop.client.jms;

import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;

import shop.client.core.ShopClientProperties;
import shop.core.bootstrap.CoreJMSService;
import shop.core.domain.PetOrder;


public class JMSSetup extends CoreJMSService{
	
	private static MessageProducer petBuyProducer;
	private static MessageConsumer shopMonConsumer;
	
	public static void buyRequest(PetOrder order) throws JMSException {
		
		if(!isShopClosed){
			petBuyProducer.send(buyTopic, createMessage(order));
		}
	}

	public static void initJMS() throws JMSException {
		CoreJMSService.initJMS(new JSONTransformer(), ShopClientProperties.getJMSBrokerUrl());
		createProducers();
		createConsumers();
		
		connection.start();
	}
	
	public static Message getShopCloseMessage() throws JMSException{
		return shopMonConsumer.receive();
	}
	
	private static void createConsumers() throws JMSException {
		shopMonConsumer = session.createConsumer(shopMonitorTopic);
		
	}

	private static void createProducers() throws JMSException{
		petBuyProducer = session.createProducer(buyTopic);
		petBuyProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
	}

	private static Message createMessage(PetOrder order) throws JMSException{
		ObjectMessage message = session.createObjectMessage();
		message.setObject(order);
		ShopClientProperties.getJMSLog().debug("JMS order - " + order.toString());
		return message;
		
	}
	
	
	

}
