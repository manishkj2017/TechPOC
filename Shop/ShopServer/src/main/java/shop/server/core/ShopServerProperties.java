package shop.server.core;

import org.apache.log4j.Logger;

import shop.core.bootstrap.SystemProperties;

public class ShopServerProperties {
	private static SystemProperties properties = new SystemProperties(ShopServerProperties.class.getClassLoader().getResourceAsStream("application.properties"));
	private static Logger shopLog  = Logger.getLogger("SHOP");
	private static Logger jmsLog = Logger.getLogger("JMS");
	private static Logger webLog = Logger.getLogger("WEB");
	private static Logger rmiLog = Logger.getLogger("RMI");
	
	private static String webhostname = "localhost"; //default
	private static String jmshostname = "localhost"; //default
	private static String rmihostname = "localhost"; //default
	
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
	
	public static String getWebhostname() {
		return webhostname;
	}

	public static void setWebhostname(String webhostname) {
		ShopServerProperties.webhostname = webhostname;
	}

	public static String getJmshostname() {
		return jmshostname;
	}

	public static void setJmshostname(String jmshostname) {
		ShopServerProperties.jmshostname = jmshostname;
	}

	public static String getRmihostname() {
		return rmihostname;
	}

	public static void setRmihostname(String rmihostname) {
		ShopServerProperties.rmihostname = rmihostname;
	}
	
	public static String getJMSBrokerUrl(){
		return "tcp://" + getJmshostname() + ":" + properties.getJMSPort();
	}

	
}
