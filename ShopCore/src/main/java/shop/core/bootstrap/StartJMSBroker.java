package shop.core.bootstrap;



import javax.jms.Connection;
import javax.jms.JMSException;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;

public class StartJMSBroker {
	
	
	public static void startBroker(boolean isShop, String brokerURL){
		try{
			
			if(isBrokerAlreadyRunning(brokerURL)){
				if(!isShop){
					System.out.println("shop is open..");
				}
				return;
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
			broker.addConnector(brokerURL);
			broker.start();
		}catch(Exception e){
			System.out.println("broker was not started properly.. please check");
			e.printStackTrace();
		}
	}
	
	private static boolean isBrokerAlreadyRunning(String brokerURL) throws JMSException{
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
