package shop.core.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import shop.server.config.ApplicationConfig;
import shop.server.core.ServiceManager;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfig.class)
public class ConfigurationComponentsScanningTest {
	
	@Autowired
	private ServiceManager serviceManager;
	
	
	@Test
	public void testDependencyInjectionShouldWork(){
		System.out.println("inside test");
		assert(serviceManager != null);
	}
}
