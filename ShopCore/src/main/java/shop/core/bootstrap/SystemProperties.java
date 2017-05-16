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
	private static final String PetInventoryFile = "PetInventoryFile";
	private static final String PetMaxInventorySize = "PetMaxInventorySize";
	private static final String RMIPort = "RMIPort";
	private static final String WEBPort = "WEBPort";
	private static final String ShopRMIName = "ShopRMIName";
	private static final String PetWebServiceURL = "PetWebServiceURL";
	private static final String ShopCloseWebServiceURL = "ShopCloseWebServiceURL";
	private static final String MaxBidPriceOffset = "MaxBidPriceOffset";
	private static final String MaxCustomers = "MaxCustomers";
	
	
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
	
	public String getPetInventoryFileName(){
		return props.getProperty(PetInventoryFile);
	}
	
	public int getPetMaxInventorySize(){
		return Integer.valueOf(props.getProperty(PetMaxInventorySize));
	}
	
	public int getRMIPort(){
		return Integer.valueOf(props.getProperty(RMIPort));
	}
	
	public int getWEBPort(){
		return Integer.valueOf(props.getProperty(WEBPort));
	}
	
	public String getShopRMIName(){
		return props.getProperty(ShopRMIName);
	}
	public String getPetWebServiceURL(){
		return props.getProperty(PetWebServiceURL);
	}
	public String getShopCloseWebServiceURL(){
		return props.getProperty(ShopCloseWebServiceURL);
	}
	
	public int getMaxBidPriceOffset(){
		return Integer.valueOf(props.getProperty(MaxBidPriceOffset));
	}
	
	public int getMaxCustomers(){
		return Integer.valueOf(props.getProperty(MaxCustomers));
	}
}
