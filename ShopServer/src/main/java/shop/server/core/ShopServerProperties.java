package shop.server.core;

import org.apache.log4j.Logger;

import shop.core.bootstrap.SystemProperties;

public class ShopServerProperties {
	private static SystemProperties properties = new SystemProperties(ShopServerProperties.class.getClassLoader().getResourceAsStream("application.properties"));
	private static Logger shopLog  = Logger.getLogger("SHOP");
	private static Logger jmsLog = Logger.getLogger("JMS");
	private static Logger webLog = Logger.getLogger("WEB");
	private static Logger rmiLog = Logger.getLogger("RMI");
	
	public static SystemProperties getProperties() {
		return properties;
	}

	public static void setProperties(SystemProperties properties) {
		ShopServerProperties.properties = properties;
	}

	public static Logger getShopLog() {
		return shopLog;
	}
	public static Logger getJMSLog() {
		return jmsLog;
	}
	public static Logger getWEBLog() {
		return webLog;
	}
	public static Logger getRMILog() {
		return rmiLog;
	}
	
}
