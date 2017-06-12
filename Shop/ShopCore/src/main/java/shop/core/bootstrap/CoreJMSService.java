package shop.core.bootstrap;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.MessageTransformer;

public class CoreJMSService {
	
	public static final String JMSBuyTopic = "PET_BUY";
	public static final String JMSShopMonTopic = "PET_SHOP_MON";
	
	public static boolean isShopClosed = false;
	
	
	
	public static ActiveMQConnectionFactory activeMQFactory;
	public static Connection connection;
	public static Session session;
	public static Destination buyTopic;
	public static Destination shopMonitorTopic;
	
	public static MessageProducer shopMonProducer;
	
	private static String brokerURL;
	private static void initFactory(MessageTransformer transformer){
		activeMQFactory = new ActiveMQConnectionFactory(brokerURL);
		activeMQFactory.setTrustAllPackages(true);
		if(transformer != null)
			activeMQFactory.setTransformer(transformer);
		
		activeMQFactory.setAlwaysSessionAsync(false);
	}
	
	private static void initConnection() throws JMSException{
		connection = activeMQFactory.createConnection();
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		//main JMS topic for buy requests
		buyTopic = session.createTopic(JMSBuyTopic);
		
		//shop monitor topic and producer
		shopMonitorTopic = session.createTopic(JMSShopMonTopic);
		shopMonProducer = session.createProducer(shopMonitorTopic);
		
	}
	
	public static void initJMS(MessageTransformer transformer, String brokerURL) throws JMSException{
		CoreJMSService.brokerURL = brokerURL;
		if(session == null){
			System.out.println("initializing JMS...");
			initFactory(transformer);
			initConnection();
			
		}
		
	}
	
	public static void destroyJMS(){
		
		System.out.println("destroying JMS...");
		try{
			if(session != null)
				session.close();
			
			if(connection != null)
				connection.close();
			
		}catch(JMSException j){
			j.printStackTrace();
		}
	}
	
	public static void produceShopCloseMessage() throws JMSException{
		TextMessage msg = session.createTextMessage("shop closed now");
		shopMonProducer.send(shopMonitorTopic, msg);
		System.out.println("JMS shop close message sent");
		
	}
	
	public static boolean isBrokerAlreadyRunning(String brokerURL) throws JMSException{
		boolean isBrokerAlreadyRunning = true;
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerURL);
		Connection connection = null;
		
		try{
			connection = connectionFactory.createConnection();
		}catch(JMSException j){
			isBrokerAlreadyRunning = false;
		}finally{
			if(connection != null)
				connection.close();
		}
		
		return isBrokerAlreadyRunning;
	}
	
}
