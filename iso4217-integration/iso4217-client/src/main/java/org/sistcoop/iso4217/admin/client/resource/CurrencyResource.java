package org.sistcoop.iso4217.admin.client.resource;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.hibernate.validator.constraints.NotBlank;
import org.sistcoop.iso4217.representations.idm.CurrencyRepresentation;
import org.sistcoop.iso4217.representations.idm.DenominationRepresentation;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/currency")
public interface CurrencyResource {

	@GET
	@Path("/alphabeticCode/{alphabeticCode}")
	public CurrencyRepresentation findByAlphabeticCode(
			@PathParam("alphabeticCode") 
			@NotNull 
			@NotBlank
			@Size(min = 3, max = 3) String alphabeticCode);
	
	@GET
	@Path("/numericCode/{numericCode}")
	public CurrencyRepresentation findByNumericCode(
			@PathParam("numericCode") 
			@NotNull 
			@NotBlank
			@Size(min = 3, max = 3) String numericCode);	        

	@POST
	public Response create(
			@NotNull
			@Valid CurrencyRepresentation currencyRepresentation);

	@PUT
	@Path("/alphabeticCode/{alphabeticCode}")
	public void updateByAlphabeticCode(
			@PathParam("alphabeticCode") 
			@NotNull 
			@NotBlank
			@Size(min = 3, max = 3) String alphabeticCode, 
			
			@NotNull
			@Valid CurrencyRepresentation currencyRepresentation);
	
	@PUT
	@Path("/numericCode/{numericCode}")
	public void updateByNumericCode(
			@PathParam("numericCode") 
			@NotNull 
			@NotBlank
			@Size(min = 3, max = 3) String numericCode, 
			
			@NotNull
			@Valid CurrencyRepresentation currencyRepresentation);
	
	@DELETE
	@Path("/alphabeticCode/{alphabeticCode}")	
	public void removeByAlphabeticCode(
			@PathParam("alphabeticCode") 
			@NotNull 
			@NotBlank
			@Size(min = 3, max = 3) String alphabeticCode);
	
	@DELETE
	@Path("/numericCode/{numericCode}")
	public void removeByNumericCode(
			@PathParam("numericCode") 
			@NotNull 
			@NotBlank
			@Size(min = 3, max = 3) String numericCode);
	
	@GET	
	public List<CurrencyRepresentation> listAll(
			@QueryParam("filterText")
			@Size(min = 1, max = 100) String filterText, 
			
			@QueryParam("firstResult") 
			@Min(value = 0) Integer firstResult, 
			
			@QueryParam("maxResults") 
			@Min(value = 1) Integer maxResults);

	@GET
	@Path("/count")	
	public int countAll();
	
	/**
	 * Denominations*/
	
	@GET	
	@Path("/alphabeticCode/{alphabeticCode}/denominations")	
	public List<DenominationRepresentation> getDenominationsByAlphabeticCode(
			@PathParam("alphabeticCode") 
			@NotNull 
			@NotBlank
			@Size(min = 3, max = 3) String alphabeticCode);
	
	@GET		
	@Path("/numericCode/{numericCode}/denominations")
	public List<DenominationRepresentation> getDenominationsByNumericCode(
			@PathParam("numericCode") 
			@NotNull 
			@NotBlank
			@Size(min = 3, max = 3) String numericCode);
	
}