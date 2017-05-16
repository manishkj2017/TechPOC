package shop.server.shop.jms;

import javax.jms.JMSException;

import shop.core.bootstrap.StartJMSBroker;

public class JMSShop extends Thread{
	
	@Override
	public void run(){
		StartJMSBroker.startBroker(true);
		
		try {
			SetupJMS.initJMS();
			System.out.println("JMS Shop is open - ready to accept buy requests for pets");
			SetupJMS.processBuyRequest();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
