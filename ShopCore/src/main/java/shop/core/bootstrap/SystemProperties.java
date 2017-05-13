package shop.core.bootstrap;

import java.io.IOException;
import java.util.Properties;

import org.springframework.stereotype.Component;

import shop.core.core.Shop;

@Component
public class SystemProperties {
	public Properties props;
	private static final String JMSBrokerUrl = "JMSBrokerUrl";
	private static final String PetStorePath = "PetStore";
	private static final String PetOrdeFile = "PetOrderFile";
	private static final String PetSaleFile = "PetSaleFile";
	
	public SystemProperties(){
		loadProperties();
	}
	
	private void loadProperties(){
		//load properties
		try {
			props = new Properties();
			props.load(Shop.class.getClassLoader().getResourceAsStream("application.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getJMSBrokerUrl(){
		return props.getProperty(JMSBrokerUrl);
	}
	
	public String getPetStorePath(){
		return props.getProperty(PetStorePath);
	}
	
	public String getPetOrderFileName(){
		return props.getProperty(PetOrdeFile);
	}
	
	public String getPetSaleFileName(){
		return props.getProperty(PetSaleFile);
	}
}
