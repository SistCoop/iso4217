package org.sistcoop.iso4217.models.jpa.search.filters;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Named;

import org.sistcoop.iso4217.models.search.filters.CurrencyFilterProvider;

@Named
@Stateless
@Local(CurrencyFilterProvider.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class JpaCurrencyFilterProvider implements CurrencyFilterProvider {

    private final String id = "id";
    private final String alphabeticCode = "alphabeticCode";
    private final String numericCode = "numericCode";
    private final String currency = "currency";
    private final String entity = "entity";

    @Override
    public void close() {
        // TODO Auto-generated method stub

    }

    @Override
    public String getIdFilter() {
        return this.id;
    }

    @Override
    public String getAlphabeticCodeFilter() {
        return this.alphabeticCode;
    }

    @Override
    public String getNumericCodeFilter() {
        return this.numericCode;
    }

    @Override
    public String getEntityFilter() {
        return this.entity;
    }

    @Override
    public String getCurrencyFilter() {
        return this.currency;
    }

}
