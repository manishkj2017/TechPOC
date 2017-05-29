package shop.server.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ComponentScan("shop")
@ImportResource({"classpath:/jpaContext.xml"})
public class ApplicationConfig {

}
