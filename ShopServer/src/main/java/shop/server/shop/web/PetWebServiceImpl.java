package shop.server.shop.web;

import java.io.IOException;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.stereotype.Service;

import shop.core.core.Shop;
import shop.core.domain.Pet;
import shop.core.domain.PetOrder;
import shop.server.shop.OpenShop;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class PetWebServiceImpl implements PetWebService {

	@Override
	public Response buyRequest(String request) {
		
		ObjectMapper mapper = new ObjectMapper();
		
		String petJson = "Pet Could not be bought";
		
		try {
			PetOrder order = mapper.readValue(request, PetOrder.class);
			
			//check for dupe requests - CXF sometimes picking same request twice..
			//TODO:try using Jersey or Spring web service provider and see
			if(OpenShop.shopInterface.isOrderDupe(order.getOrderNumber())){
				Shop.getWEBLog().debug("caught dupe web service request for order - " + order.getOrderNumber());
				return Response.ok().build(); //just return ok as it is not impacting original buy request
				
			}
			
			Shop.getWEBLog().debug("Processing web request for order - " + order.getOrderNumber());
			Pet pet = OpenShop.shopInterface.buyRequest(order.getPetType(), order.getCustomerName(), 
					order.getBidPrice(), order.getCustomerNumber(), order.getOrderSource(), order.getOrderNumber());
			
			if(pet != null){
				petJson = mapper.writeValueAsString(pet);
				
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return Response.ok(petJson).build();
	}

	@Override
	public Response getPet(int tag) {
		Pet pet = null;
		try{
			pet = OpenShop.shopInterface.getPetByTag(tag);
			if(pet != null)
				return Response.ok(new ObjectMapper().writeValueAsString(pet)).build();
			else{
				return Response.status(Status.NOT_FOUND).build();
			}
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
		return Response.serverError().build();
	}

	@Override
	public Response isShopClosed() {
		try{
			if(OpenShop.shopInterface.isShopClosed()){
				return Response.status(Status.SERVICE_UNAVAILABLE).build();
			}
			
		}catch(IOException e){
			e.printStackTrace();
		}
		
		return Response.ok().build();
	}

}
