package org.sistcoop.iso4217.admin.client.resource;

import javax.ejb.EJBException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.sistcoop.iso4217.representations.idm.CurrencyRepresentation;

public interface CurrencyResource {

    /**
     * Obtener un Currency segun id.
     * 
     * @summary Obtener Currency
     * @statuscode 200 Si el objeto fue encontrado.
     * @return CountryCodeRepresentation encontrado.
     * @throws EJBException
     *             datos validos pero ocurrio un error interno.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public CurrencyRepresentation currency();

    /**
     * Actualizar Currency segun los datos enviados.
     * 
     * @summary Actualizar Currency
     * @param representation
     *            Datos nuevos del objeto.
     * @statuscode 200 Si el objeto fue actualizado satisfactoriamente.
     * @throws EJBException
     *             datos validos pero ocurrio un error interno.
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void update(CurrencyRepresentation representation);

    /**
     * Deshabilitar el Currency.
     * 
     * @summary Deshabilitar Currency
     * @statuscode 200 Si el objeto fue desactivado satisfactoriamente.
     * @throws EJBException
     *             datos validos pero ocurrio un error interno.
     */
    @POST
    @Path("/disable")
    public void disable();

    /**
     * Eliminar el Currency.
     * 
     * @summary Eliminar CountryCode
     * @statuscode 200 Si el objeto fue elimado satisfactoriamente.
     * @throws EJBException
     *             datos validos pero ocurrio un error interno.
     */
    @DELETE
    public void remove();

}