package shop.gui.core;

import org.apache.log4j.Logger;

import shop.core.bootstrap.SystemProperties;

public class ShopGUIProperties {
	private static SystemProperties properties = new SystemProperties(ShopGUIProperties.class.getClassLoader().getResourceAsStream("application.properties"));
	private static Logger shopLog  = Logger.getLogger("SHOP");
	
	private static String webhostname = "localhost"; //default
	
	public static SystemProperties getProperties() {
		return properties;
	}

	public static void setProperties(SystemProperties properties) {
		ShopGUIProperties.properties = properties;
	}

	public static Logger getShopLog() {
		return shopLog;
	}
	
	public static String getPetSaleSummaryWebServiceURL(){
		return "http://"+ getWebhostname() + ":" + properties.getWEBPort() + properties.getPetSaleSummaryWebServiceURL();
	}
	
	public static String getShopCloseWebServiceURL(){
		return "http://" + getWebhostname() + ":" + properties.getWEBPort() + properties.getShopCloseWebServiceURL();
	}
	
	public static String getWebhostname() {
		return webhostname;
	}

	public static void setWebhostname(String webhostname) {
		ShopGUIProperties.webhostname = webhostname;
	}
	
}
