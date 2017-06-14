package shop.core.bootstrap;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SystemProperties {
	public Properties props;
	private static final String PetStorePath = "PetStore";
	private static final String PetOrdeFile = "PetOrderFile";
	private static final String PetSaleFile = "PetSaleFile";
	private static final String PetInventoryFile = "PetInventoryFile";
	private static final String PetMaxInventorySize = "PetMaxInventorySize";
	
	private static final String JMSPort = "JMSPort";
	private static final String RMIPort = "RMIPort";
	private static final String WEBPort = "WEBPort";
	private static final String ShopRMIName = "ShopRMIName";
	private static final String PetWebServiceURL = "PetWebServiceURL";
	private static final String ShopCloseWebServiceURL = "ShopCloseWebServiceURL";
	private static final String PetSaleSummaryWebServiceURL ="PetSaleSummaryWebServiceURL";
	private static final String ShopReadyCheckURL ="ShopReadyCheckURL";
	
	private static final String MaxBidPriceOffset = "MaxBidPriceOffset";
	private static final String MaxCustomers = "MaxCustomers";
	
	
	//command line properties
	public static final String database = "database";
	public static final String WEBHostName = "WebHostName";
	public static final String JMSHostName = "JmsHostName";
	public static final String RMIHostName = "RmiHostName";
	public static final String AllowJMS = "allowJMS";
	public static final String AllowRMI = "allowRMI";
	
	
	public SystemProperties(InputStream stream){
		loadProperties(stream);
	}
	
	private void loadProperties(InputStream stream){
		//load properties
		try {
			props = new Properties();
			props.load(stream);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getPetStorePath(){
		return System.getProperty("user.home")+props.getProperty(PetStorePath);
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
	
	public String getShopReadyCheckURL(){
		return props.getProperty(ShopReadyCheckURL);
	}
	
	public int getMaxBidPriceOffset(){
		return Integer.valueOf(props.getProperty(MaxBidPriceOffset));
	}
	
	public int getMaxCustomers(){
		return Integer.valueOf(props.getProperty(MaxCustomers));
	}
	
	public String getPetSaleSummaryWebServiceURL(){
		return props.getProperty(PetSaleSummaryWebServiceURL);
	}
	
	public String getJMSPort(){
		return props.getProperty(JMSPort);
	}
}
