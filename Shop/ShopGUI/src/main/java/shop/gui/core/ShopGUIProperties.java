package shop.gui.core;

import org.apache.log4j.Logger;

import shop.core.bootstrap.SystemProperties;

public class ShopGUIProperties {
	private static SystemProperties properties = new SystemProperties(ShopGUIProperties.class.getClassLoader().getResourceAsStream("application.properties"));
	private static Logger shopLog  = Logger.getLogger("SHOP");
	
	public static SystemProperties getProperties() {
		return properties;
	}

	public static void setProperties(SystemProperties properties) {
		ShopGUIProperties.properties = properties;
	}

	public static Logger getShopLog() {
		return shopLog;
	}
	
}
