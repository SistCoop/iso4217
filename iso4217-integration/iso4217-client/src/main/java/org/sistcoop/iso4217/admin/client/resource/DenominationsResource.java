package org.sistcoop.iso4217.admin.client.resource;

import javax.ejb.EJBException;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.sistcoop.iso4217.representations.idm.DenominationRepresentation;
import org.sistcoop.iso4217.representations.idm.search.SearchResultsRepresentation;

public interface DenominationsResource {

    /**
     * Crea un DenominationRepresentation segun los datos enviados
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
    public Response create(DenominationRepresentation representation);

    /**
     * Buscar Denomination segun los parametros enviados.
     * 
     * @summary Buscar todos los CountryCode
     * @statuscode 200 Si la busqueda fue exitosa.
     * @return SearchResultsRepresentation resultado de busqueda.
     * @throws EJBException
     *             datos validos pero ocurrio un error interno
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public SearchResultsRepresentation<DenominationRepresentation> search();

}