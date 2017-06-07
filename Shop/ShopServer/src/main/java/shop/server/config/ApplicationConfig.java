package shop.server.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ComponentScan("shop")
@ImportResource({"jpaContext.xml"})
public class ApplicationConfig {

}
