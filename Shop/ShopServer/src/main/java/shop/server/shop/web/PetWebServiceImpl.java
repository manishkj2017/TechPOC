package shop.server.shop.web;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.stereotype.Service;

import shop.core.domain.Pet;
import shop.core.domain.PetInventory;
import shop.core.domain.PetOrder;
import shop.core.domain.PetOrderSummaryData;
import shop.core.domain.PetSaleSummaryData;
import shop.core.enums.OrderStatus;
import shop.core.enums.PetTypes;
import shop.server.core.ShopServerProperties;
import shop.server.shop.OpenShop;

import com.fasterxml.jackson.core.JsonProcessingException;
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
				ShopServerProperties.getWEBLog().debug("caught dupe web service request for order - " + order.getOrderNumber());
				return Response.ok().build(); //just return ok as it is not impacting original buy request
				
			}
			
			ShopServerProperties.getWEBLog().debug("Processing web request for order - " + order.getOrderNumber());
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

	@Override
	public Response getSaleSummary() {
		String petSummariesJson = "no sale data found";
		try{
			List<PetSaleSummaryData> petSummaries = OpenShop.shopInterface.getPetSaleSummary();
			List<PetOrderSummaryData> orderSummaries = OpenShop.shopInterface.getPetOrderSummary();
			List<PetInventory> petInventory = OpenShop.shopInterface.getPetsInventorySummary();
			
			Map<String, Integer> petAvailableStock = new HashMap<String, Integer> ();
			Map<String, Integer> petRejectedOrders = new HashMap<String, Integer> ();
			
			Map<String, Integer> petTotalSold = new HashMap<String, Integer> ();
			
			for(PetInventory i: petInventory){
				petAvailableStock.put(i.getPetType(), i.getStockAvailable());
			}
			
			for(PetOrderSummaryData o:orderSummaries){
				String key = o.getPetType() + "-" + o.getOrderSource();
				if(OrderStatus.Rejected.name().equals(o.getStatus())){
					if(petRejectedOrders.get(key) == null){
						petRejectedOrders.put(key, o.getNoOfOrders());
					}else{
						int rejectedOrders = petRejectedOrders.get(key) + o.getNoOfOrders();
						petRejectedOrders.put(key, rejectedOrders);
					}
				}
				if(OrderStatus.Accepted.name().equals(o.getStatus())){
					if(petTotalSold.get(o.getPetType()) == null){
						petTotalSold.put(o.getPetType(), o.getNoOfOrders());
					}else{
						int acceptedOrders = petTotalSold.get(o.getPetType()) + o.getNoOfOrders();
						petTotalSold.put(o.getPetType(), acceptedOrders);
					}
				}
			}
			
			
			
			for(PetSaleSummaryData saleSummary : petSummaries){
				
				String key = saleSummary.getPetType() + "-" + saleSummary.getOrderSource();
				
				saleSummary.setAvailable(petAvailableStock.get(saleSummary.getPetType()) - petTotalSold.get(saleSummary.getPetType()));
				if(petRejectedOrders.get(key) != null)
					saleSummary.setNoOfRejectedOrders(petRejectedOrders.get(key));
				else
					saleSummary.setNoOfRejectedOrders(0);
				
				
				
				BigDecimal petSellingPrice = PetTypes.getTypeByName(saleSummary.getPetType()).getPrice();
				BigDecimal cost = petSellingPrice.multiply(new BigDecimal(saleSummary.getTotalSold()));
				
				cost = cost.add(petSellingPrice.multiply(new BigDecimal(saleSummary.getAvailable())));
				saleSummary.setBuyCost(cost.setScale(2, RoundingMode.HALF_UP));
				
				//add rejected orders penalty also to the cost - 5% penalty of pet price
				BigDecimal rejectedOrderPenalty = petSellingPrice.multiply(new BigDecimal(saleSummary.getNoOfRejectedOrders()).multiply(new BigDecimal(0.05)));
				saleSummary.setRejectionPenalty(rejectedOrderPenalty.setScale(2, RoundingMode.HALF_UP));
				
				cost = cost.add(rejectedOrderPenalty);
				
				saleSummary.setProfitLoss( (saleSummary.getRevenue().subtract(cost)).setScale(2, RoundingMode.HALF_UP));
			}
			
			Collections.sort(petSummaries);
			
			ObjectMapper mapper = new ObjectMapper();
			if(petSummaries.size() > 0)
				petSummariesJson = mapper.writeValueAsString(petSummaries);
			else
				return Response.status(Status.NO_CONTENT).build();
			
		}catch (JsonProcessingException e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}catch(IOException e){
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		
		return Response.ok(petSummariesJson).build();
	}

}
