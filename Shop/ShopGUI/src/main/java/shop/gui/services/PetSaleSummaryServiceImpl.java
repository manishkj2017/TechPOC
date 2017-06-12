package shop.gui.services;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.stereotype.Service;

import shop.core.domain.PetSaleSummaryData;
import shop.gui.core.ShopGUIProperties;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class PetSaleSummaryServiceImpl implements PetSaleSummaryService {

	private static boolean isConnectionClosed = false;

	@Override
	public List<PetSaleSummaryData> getPetSaleSummary() {
		Client client = ClientBuilder.newClient();
		List<PetSaleSummaryData> petSummaries = new ArrayList<>();
		/*if(isConnectionClosed){
			return petSummaries;
		}*/
		
		WebTarget target = client.target(ShopGUIProperties.getPetSaleSummaryWebServiceURL());
		
		try{
			Response response = target.request().get();
			String petSummariesJson = response.readEntity(String.class);
			System.out.println(response.getStatus() + ":" + petSummariesJson);
			if(response.getStatus() == Status.NO_CONTENT.getStatusCode() 
					|| response.getStatus() == Status.INTERNAL_SERVER_ERROR.getStatusCode())
				return petSummaries;
			
			petSummaries = (List<PetSaleSummaryData>) new ObjectMapper().readValue(petSummariesJson, List.class);
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("web service request failed - shop server is closed");
			//isConnectionClosed = true;
		}
		return petSummaries;
	}
	@Override
	public boolean isShopClosed(){
	
		try{
			Client client  = ClientBuilder.newClient();
			WebTarget target = client.target(ShopGUIProperties.getShopCloseWebServiceURL());
			Response response = target.request().get();
			if(response.getStatusInfo().getStatusCode() == Status.SERVICE_UNAVAILABLE.getStatusCode()){
				//isConnectionClosed = true;
				return true;
			}else{
				//isConnectionClosed = false;
				return false;
			}
		}catch(Exception e){
			//isConnectionClosed = true;
			return true;
		}
	}
}

