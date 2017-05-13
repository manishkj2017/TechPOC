package shop.core.bootstrap;



import javax.jms.Connection;
import javax.jms.JMSException;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;

import shop.core.core.Shop;

public class StartJMSBroker {
	
	public static void startBroker(boolean isShop){
		try{
			
			if(isBrokerAlreadyRunning()){
				if(!isShop){
					System.out.println("shop is open..");
				}
			}else{
				if(!isShop){
					System.out.println("shop is not open yet - raising orders offline");
				}
			}
			
		}catch(JMSException j){
			j.printStackTrace();
		}
		
		BrokerService broker = new BrokerService();
		try{
			broker.addConnector(Shop.getProperties().getJMSBrokerUrl());
			broker.start();
		}catch(Exception e){
			System.out.println("broker was not started properly.. please check");
			e.printStackTrace();
		}
	}
	
	private static boolean isBrokerAlreadyRunning() throws JMSException{
		boolean isBrokerAlreadyRunning = true;
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(Shop.getProperties().getJMSBrokerUrl());
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
