package org.sistcoop.iso4217.admin.client.resource;

import javax.ejb.EJBException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.hibernate.validator.constraints.NotBlank;
import org.sistcoop.iso4217.representations.idm.CurrencyRepresentation;
import org.sistcoop.iso4217.representations.idm.search.SearchResultsRepresentation;

@Path("/currencies")
@Consumes(MediaType.APPLICATION_JSON)
public interface CurrenciesResource {

    /**
     * @param currency
     *            Id del Currency.
     */
    @Path("/{currency}")
    public CurrencyResource currency(@PathParam("currency") String currency);

    /**
     * Crea un Currency segun los datos enviados
     * 
     * @summary Crea un Currency
     * @param representation
     *            El detalle del objeto a enviar.
     * @statuscode 201 Si el objeto fue creado satisfactoriamente.
     * @return Currency creado.
     * @throws EJBException
     *             datos validos pero ocurrio un error interno
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(CurrencyRepresentation representation);

    /**
     * Buscar Currency segun los parametros enviados.
     * 
     * @summary Buscar uno o varios Currency
     * @param filterText
     *            Palabra filtro.
     * @param page
     *            Numero de pagina.
     * @param pageSize
     *            Tamano de pagina.
     * @statuscode 200 Si la busqueda fue exitosa.
     * @return SearchResultsRepresentation resultado de busqueda.
     * @throws EJBException
     *             datos validos pero ocurrio un error interno
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public SearchResultsRepresentation<CurrencyRepresentation> search(
            @QueryParam("filterText") @NotBlank String filterText,
            @QueryParam("page") @DefaultValue("1") int page,
            @QueryParam("pageSize") @DefaultValue("20") int pageSize);

    /**
     * Buscar Currency segun los parametros enviados.
     * 
     * @summary Buscar todos los CountryCode
     * @statuscode 200 Si la busqueda fue exitosa.
     * @return SearchResultsRepresentation resultado de busqueda.
     * @throws EJBException
     *             datos validos pero ocurrio un error interno
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public SearchResultsRepresentation<CurrencyRepresentation> search();

}