package org.sistcoop.iso4217.services.resources.admin;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.sistcoop.iso4217.admin.client.resource.CurrenciesResource;
import org.sistcoop.iso4217.admin.client.resource.CurrencyResource;
import org.sistcoop.iso4217.models.CurrencyModel;
import org.sistcoop.iso4217.models.CurrencyProvider;
import org.sistcoop.iso4217.models.search.PagingModel;
import org.sistcoop.iso4217.models.search.SearchCriteriaModel;
import org.sistcoop.iso4217.models.search.SearchResultsModel;
import org.sistcoop.iso4217.models.search.filters.CurrencyFilterProvider;
import org.sistcoop.iso4217.models.utils.ModelToRepresentation;
import org.sistcoop.iso4217.models.utils.RepresentationToModel;
import org.sistcoop.iso4217.representations.idm.CurrencyRepresentation;
import org.sistcoop.iso4217.representations.idm.search.SearchResultsRepresentation;

@Stateless
public class CurrenciesResourceImpl implements CurrenciesResource {

    @Inject
    private CurrencyProvider currencyProvider;

    @Inject
    private RepresentationToModel representationToModel;

    @Context
    private UriInfo uriInfo;

    @Inject
    private CurrencyResource currencyResource;

    @Inject
    private CurrencyFilterProvider filterProvider;

    @Override
    public CurrencyResource currency(String countryCode) {
        return currencyResource;
    }

    @Override
    public Response create(CurrencyRepresentation representation) {
        CurrencyModel model = representationToModel.createCurrency(representation, currencyProvider);
        return Response.created(uriInfo.getAbsolutePathBuilder().build())
                .header("Access-Control-Expose-Headers", "Location")
                .entity(ModelToRepresentation.toRepresentation(model)).build();
    }

    @Override
    public SearchResultsRepresentation<CurrencyRepresentation> search() {
        SearchCriteriaModel searchCriteriaBean = new SearchCriteriaModel();
        searchCriteriaBean.addOrder(filterProvider.getEntityFilter(), true);

        // search
        SearchResultsModel<CurrencyModel> results = currencyProvider.search(searchCriteriaBean);
        SearchResultsRepresentation<CurrencyRepresentation> rep = new SearchResultsRepresentation<>();
        List<CurrencyRepresentation> representations = new ArrayList<>();
        for (CurrencyModel model : results.getModels()) {
            representations.add(ModelToRepresentation.toRepresentation(model));
        }
        rep.setTotalSize(results.getTotalSize());
        rep.setItems(representations);
        return rep;
    }

    @Override
    public SearchResultsRepresentation<CurrencyRepresentation> search(String filterText, int page,
            int pageSize) {

        PagingModel paging = new PagingModel();
        paging.setPage(page);
        paging.setPageSize(pageSize);

        SearchCriteriaModel searchCriteriaBean = new SearchCriteriaModel();
        searchCriteriaBean.setPaging(paging);
        searchCriteriaBean.addOrder(filterProvider.getEntityFilter(), true);

        // search
        SearchResultsModel<CurrencyModel> results = currencyProvider.search(searchCriteriaBean, filterText);
        SearchResultsRepresentation<CurrencyRepresentation> rep = new SearchResultsRepresentation<>();
        List<CurrencyRepresentation> representations = new ArrayList<>();
        for (CurrencyModel model : results.getModels()) {
            representations.add(ModelToRepresentation.toRepresentation(model));
        }
        rep.setTotalSize(results.getTotalSize());
        rep.setItems(representations);
        return rep;
    }

}