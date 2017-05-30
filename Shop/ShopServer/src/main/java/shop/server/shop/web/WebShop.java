package shop.server.shop.web;

import org.springframework.context.ApplicationContext;

public class WebShop extends Thread{
	
	private ApplicationContext rootContext;
	
	public WebShop(ApplicationContext context) {
		this.rootContext = context;
	}
	
	@Override
	public void run(){
		try{
			System.out.println("Web Shop is open - ready to accept buy requests for pets");
			SetupWEB.startServer(rootContext);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
