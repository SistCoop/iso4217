package org.sistcoop.iso4217.models.jpa;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import javax.inject.Inject;

import org.junit.Test;
import org.sistcoop.iso4217.models.CurrencyModel;
import org.sistcoop.iso4217.models.CurrencyProvider;
import org.sistcoop.iso4217.models.search.SearchResultsModel;

public class CurrencyProviderTest extends AbstractTest {

    @Inject
    private CurrencyProvider countryCodeProvider;

    @Test
    public void create() {
        CurrencyModel model = countryCodeProvider.create("PERU", "Nuevo Sol", "PEN", "604", 2);

        assertThat(model, is(notNullValue()));
        assertThat(model.getId(), is(notNullValue()));
    }

    @Test
    public void getCurrencyByAlphabeticCode() {
        CurrencyModel model1 = countryCodeProvider.create("PERU", "Nuevo Sol", "PEN", "604", 2);

        String alphabeticCode = model1.getAlphabeticCode();

        CurrencyModel model2 = countryCodeProvider.findByAlphabeticCode(alphabeticCode);

        assertThat(model1, is(equalTo(model2)));
    }

    @Test
    public void getCurrencyByNumericCode() {
        CurrencyModel model1 = countryCodeProvider.create("PERU", "Nuevo Sol", "PEN", "604", 2);

        String numericCode = model1.getNumericCode();

        CurrencyModel model2 = countryCodeProvider.findByNumericCode(numericCode);

        assertThat(model1, is(equalTo(model2)));
    }

    @Test
    public void getCurrencies() {
        countryCodeProvider.create("PERU", "Nuevo Sol", "PEN", "604", 2);

        SearchResultsModel<CurrencyModel> results = countryCodeProvider.search();
        for (CurrencyModel model : results.getModels()) {
            assertThat(model, is(notNullValue()));
        }

        assertThat(results.getTotalSize(), is(1));
        assertThat(results.getModels().size(), is(1));
    }

    @Test
    public void remove() {
        CurrencyModel model1 = countryCodeProvider.create("PERU", "Nuevo Sol", "PEN", "604", 2);

        String alphabeticCode = model1.getAlphabeticCode();
        boolean result = countryCodeProvider.remove(model1);

        CurrencyModel model2 = countryCodeProvider.findByAlphabeticCode(alphabeticCode);

        assertThat(result, is(true));
        assertThat(model2, is(nullValue()));
    }

}
