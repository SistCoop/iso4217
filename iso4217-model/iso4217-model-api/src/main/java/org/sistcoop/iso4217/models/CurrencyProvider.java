package org.sistcoop.iso4217.models;

import javax.ejb.Local;

import org.sistcoop.iso4217.models.search.SearchCriteriaModel;
import org.sistcoop.iso4217.models.search.SearchResultsModel;
import org.sistcoop.iso4217.provider.Provider;

@Local
public interface CurrencyProvider extends Provider {

    CurrencyModel findById(String id);

    CurrencyModel findByAlphabeticCode(String alpha2Code);

    CurrencyModel findByNumericCode(String numericCode);

    CurrencyModel create(String entity, String currency, String alphabeticCode, String numericCode,
            int minorUnit);

    boolean remove(CurrencyModel CurrencyModel);

    SearchResultsModel<CurrencyModel> search();

    SearchResultsModel<CurrencyModel> search(SearchCriteriaModel criteria);

    SearchResultsModel<CurrencyModel> search(SearchCriteriaModel criteria, String filterText);

}
