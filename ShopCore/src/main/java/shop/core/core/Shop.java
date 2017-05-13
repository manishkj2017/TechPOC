package shop.core.core;

import org.springframework.beans.factory.annotation.Autowired;

import shop.core.bootstrap.SystemProperties;


public class Shop {
	
	private static SystemProperties properties;
	
	public void init(){

	}

	public static SystemProperties getProperties() {
		return properties;
	}

	@Autowired
	public static void setProperties(SystemProperties properties) {
		Shop.properties = properties;
	}
	
	
	
	
}
