package shop.client.web;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import shop.client.core.ShopClientProperties;
import shop.core.domain.PetOrder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class WebSetup {
	
	private static boolean isConnectionClosed = false;
	
	public static void buyRequest(PetOrder order){
		Client client = ClientBuilder.newClient();
		
		if(isConnectionClosed){
			ShopClientProperties.getWEBLog().debug("Web - Shop is closed..");
			return;
		}
		
		WebTarget target = client.target(ShopClientProperties.getPetWebServiceURL());
		ObjectMapper mapper = new ObjectMapper();
		String orderJson = "";
		
		try {
			orderJson = mapper.writeValueAsString(order);
			ShopClientProperties.getWEBLog().debug("Web order - " + orderJson);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		try{
			Response response = target.request().post(Entity.entity(orderJson, MediaType.APPLICATION_JSON));
			ShopClientProperties.getWEBLog().debug(response.getStatus() + ":" + response.readEntity(String.class));
		}catch(Exception e){
			ShopClientProperties.getWEBLog().debug("web request failed - shop closed probably");
			isConnectionClosed = true;
		}
	}
	
	public static boolean isShopClosed(){
		
		Client client  = ClientBuilder.newClient();
		WebTarget target = client.target(ShopClientProperties.getShopCloseWebServiceURL());
		Response response = target.request().get();
		if(response.getStatusInfo().getStatusCode() == Status.SERVICE_UNAVAILABLE.getStatusCode()){
			return true;
		}else
			return false;
	}
}
