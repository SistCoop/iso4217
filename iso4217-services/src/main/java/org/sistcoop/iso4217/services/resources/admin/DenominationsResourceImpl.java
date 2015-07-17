package org.sistcoop.iso4217.services.resources.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.sistcoop.iso4217.admin.client.resource.DenominationsResource;
import org.sistcoop.iso4217.models.CurrencyModel;
import org.sistcoop.iso4217.models.CurrencyProvider;
import org.sistcoop.iso4217.models.DenominationModel;
import org.sistcoop.iso4217.models.utils.ModelToRepresentation;
import org.sistcoop.iso4217.representations.idm.DenominationRepresentation;
import org.sistcoop.iso4217.representations.idm.search.SearchResultsRepresentation;

@Stateless
public class DenominationsResourceImpl implements DenominationsResource {

    @PathParam("currency")
    private String currency;

    @Inject
    private CurrencyProvider currencyProvider;

    private CurrencyModel getCurrencyModel() {
        if (currency.length() == 3) {
            Pattern pattern = Pattern.compile("^[0-9]+$");
            Matcher matcher = pattern.matcher(currency);
            if (matcher.matches()) {
                return currencyProvider.findByNumericCode(currency);
            } else {
                return currencyProvider.findByAlphabeticCode(currency);
            }
        } else {
            return null;
        }
    }

    @Override
    public Response create(DenominationRepresentation representation) {
        throw new InternalServerErrorException();
    }

    @Override
    public SearchResultsRepresentation<DenominationRepresentation> search() {
        CurrencyModel model = getCurrencyModel();
        List<DenominationModel> denominationModels = model.getDenominations();
        List<DenominationRepresentation> representations = new ArrayList<DenominationRepresentation>();
        for (DenominationModel denominationModel : denominationModels) {
            representations.add(ModelToRepresentation.toRepresentation(denominationModel));
        }

        SearchResultsRepresentation<DenominationRepresentation> result = new SearchResultsRepresentation<>();
        result.setItems(representations);
        result.setTotalSize(representations.size());

        return result;
    }

}