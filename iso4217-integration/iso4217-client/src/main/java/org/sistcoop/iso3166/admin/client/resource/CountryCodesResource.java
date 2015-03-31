package org.sistcoop.iso3166.admin.client.resource;

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
import org.sistcoop.iso3166.representations.idm.CountryCodeRepresentation;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/countryCodes")
public interface CountryCodesResource {

	@GET
	@Path("/alpha2Code/{alpha2Code}")
	public CountryCodeRepresentation findByAlpha2Code(
			@PathParam("alpha2Code") 
			@NotNull 
			@NotBlank
			@Size(min = 2, max = 2) String alpha2Code);

	@GET
	@Path("/alpha3Code/{alpha3Code}")
	public CountryCodeRepresentation findByAlpha3Code(
			@PathParam("alpha3Code") 
			@NotNull 
			@NotBlank
			@Size(min = 3, max = 3) String alpha3Code);
	
	@GET
	@Path("/numericCode/{numericCode}")
	public CountryCodeRepresentation findByNumericCode(
			@PathParam("numericCode") 
			@NotNull 
			@NotBlank
			@Size(min = 3, max = 3) String numericCode);	        

	@POST
	public Response create(
			@NotNull
			@Valid CountryCodeRepresentation countryCodeRepresentation);

	@PUT
	@Path("/alpha2Code/{alpha2Code}")
	public void updateByAlpha2Code(
			@PathParam("alpha2Code") 
			@NotNull 
			@NotBlank
			@Size(min = 2, max = 2) String alpha2Code, 
			
			@NotNull
			@Valid CountryCodeRepresentation countryCodeRepresentation);

	@PUT
	@Path("/alpha3Code/{alpha3Code}")
	public void updateByAlpha3Code(
			@PathParam("alpha3Code") 
			@NotNull 
			@NotBlank
			@Size(min = 3, max = 3) String alpha3Code, 
			
			@NotNull
			@Valid CountryCodeRepresentation countryCodeRepresentation);
	
	@PUT
	@Path("/numericCode/{numericCode}")
	public void updateByNumericCode(
			@PathParam("numericCode") 
			@NotNull 
			@NotBlank
			@Size(min = 3, max = 3) String numericCode, 
			
			@NotNull
			@Valid CountryCodeRepresentation countryCodeRepresentation);
	
	@DELETE
	@Path("/alpha2Code/{alpha2Code}")
	public void removeByAlpha2Code(
			@PathParam("alpha2Code") 
			@NotNull 
			@NotBlank
			@Size(min = 2, max = 2) String alpha2Code);

	@DELETE
	@Path("/alpha3Code/{alpha3Code}")
	public void removeByAlpha3Code(
			@PathParam("alpha3Code") 
			@NotNull 
			@NotBlank
			@Size(min = 3, max = 3) String alpha3Code);
	
	@DELETE
	@Path("/numericCode/{numericCode}")
	public void removeByNumericCode(
			@PathParam("numericCode") 
			@NotNull 
			@NotBlank
			@Size(min = 3, max = 3) String numericCode);
	
	@GET	
	public List<CountryCodeRepresentation> listAll(
			@QueryParam("filterText")
			@Size(min = 1, max = 100) String filterText, 
			
			@QueryParam("firstResult") 
			@Min(value = 0) Integer firstResult, 
			
			@QueryParam("maxResults") 
			@Min(value = 1) Integer maxResults);

	@GET
	@Path("/count")	
	public int countAll();
	
}