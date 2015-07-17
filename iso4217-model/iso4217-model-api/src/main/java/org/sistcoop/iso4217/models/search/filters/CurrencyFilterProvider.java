package org.sistcoop.iso4217.models.search.filters;

import javax.ejb.Local;

import org.sistcoop.iso4217.provider.Provider;

@Local
public interface CurrencyFilterProvider extends Provider {

    String getIdFilter();

    String getAlphabeticCodeFilter();

    String getNumericCodeFilter();

    String getEntityFilter();

    String getCurrencyFilter();

}
