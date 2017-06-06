package shop.server.shop.web;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import shop.core.domain.PetSaleSummaryData;

@Produces({"application/json"})
public interface PetWebService {
	
	@POST
	@Path("/pet/")
	public Response buyRequest(String request);
	
	@GET
	@Path("/pet/{id}")
	public Response getPet(@PathParam("id") int tag);
	
	@GET
	@Path("/pet/isClosed/")
	public Response isShopClosed();
	
	@GET
	@Path("/pet/salesummary/")
	public Response getSaleSummary();
	
	@GET
	@Path("/pet/salesummarycache")
	public Response getPetSaleSummaryFromCache();
}
