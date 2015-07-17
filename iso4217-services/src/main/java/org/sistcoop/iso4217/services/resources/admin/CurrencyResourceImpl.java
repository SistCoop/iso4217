package org.sistcoop.iso4217.services.resources.admin;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.PathParam;

import org.sistcoop.iso4217.admin.client.resource.CurrencyResource;
import org.sistcoop.iso4217.admin.client.resource.DenominationsResource;
import org.sistcoop.iso4217.models.CurrencyModel;
import org.sistcoop.iso4217.models.CurrencyProvider;
import org.sistcoop.iso4217.models.utils.ModelToRepresentation;
import org.sistcoop.iso4217.representations.idm.CurrencyRepresentation;

@Stateless
public class CurrencyResourceImpl implements CurrencyResource {

    @PathParam("currency")
    private String currency;

    @Inject
    private CurrencyProvider currencyProvider;
    
    @Inject
    private DenominationsResource denominationsResource;

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
    public CurrencyRepresentation currency() {
        CurrencyModel model = getCurrencyModel();
        if (model != null) {
            return ModelToRepresentation.toRepresentation(model);
        } else {
            throw new NotFoundException();
        }
    }

    @Override
    public void update(CurrencyRepresentation representation) {
        CurrencyModel model = getCurrencyModel();
        model.setCurrency(representation.getCurrency());
        model.setEntity(representation.getEntity());
        model.setMinorUnit(representation.getMinorUnit());

        model.commit();
    }

    @Override
    public void disable() {
        throw new NotFoundException();
    }

    @Override
    public void remove() {
        CurrencyModel model = getCurrencyModel();
        boolean result = currencyProvider.remove(model);
        if (!result) {
            throw new InternalServerErrorException();
        }
    }

    @Override
    public DenominationsResource denomination() {
       return denominationsResource;
    }

}
