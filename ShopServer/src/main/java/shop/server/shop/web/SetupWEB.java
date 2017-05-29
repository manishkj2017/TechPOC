package shop.server.shop.web;

import org.apache.cxf.transport.servlet.CXFServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.session.HashSessionIdManager;
import org.eclipse.jetty.server.session.HashSessionManager;
import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoaderListener;

import shop.server.core.ShopServerProperties;

public class SetupWEB {
	public static void startServer(ApplicationContext context){
		Server jettyWeb = new Server(ShopServerProperties.getProperties().getWEBPort());
		ServletContextHandler servletContext = new ServletContextHandler();
		
		servletContext.setInitParameter("contextConfigLocation", "classpath:/cxf-servlet.xml");
		servletContext.addEventListener(new ContextLoaderListener());
		
		ServletHolder cxf = new ServletHolder(new CXFServlet());
		cxf.setInitOrder(0);
		servletContext.addServlet(cxf, "/pet-services/*");
		
		HashSessionManager sessionManager = new HashSessionManager();
		SessionHandler sessionHandler = new SessionHandler(sessionManager);
		servletContext.setSessionHandler(sessionHandler);
		
		jettyWeb.setHandler(servletContext);
		
		HashSessionIdManager sessionIdManager = new HashSessionIdManager();
		jettyWeb.setSessionIdManager(sessionIdManager);
		
		try {
			jettyWeb.start();
			jettyWeb.join();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			jettyWeb.destroy();
		}
		
		
		
	}
}
