package org.sistcoop.iso4217.admin.client.resource;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.sistcoop.iso4217.representations.idm.CurrencyRepresentation;
import org.sistcoop.iso4217.representations.idm.search.SearchResultsRepresentation;

import com.jayway.restassured.http.ContentType;

public class CountryCodeProviderTest extends AbstractTest {

    private final String baseURI = "http://127.0.0.1:8080/test/rest/currencies";

    @Test
    public void create() {
        CurrencyRepresentation rep = new CurrencyRepresentation();
        rep.setEntity("Peru");
        rep.setCurrency("Nuevo sol");
        rep.setAlphabeticCode("PEN");
        rep.setNumericCode("604");
        rep.setMinorUnit(2);

        CurrencyRepresentation repCreated = given().contentType(ContentType.JSON).body(rep).log()
                .everything().expect().statusCode(201).log().ifError().when().post(baseURI)
                .as(CurrencyRepresentation.class);

        assertThat(repCreated, is(notNullValue()));
        assertThat(repCreated.getAlphabeticCode(), is(notNullValue()));
        assertThat(repCreated.getNumericCode(), is(notNullValue()));
    }

    @Test
    public void search() {
        CurrencyRepresentation rep1 = new CurrencyRepresentation();
        rep1.setEntity("Peru");
        rep1.setCurrency("Nuevo sol");
        rep1.setAlphabeticCode("PEN");
        rep1.setNumericCode("604");
        rep1.setMinorUnit(2);

        CurrencyRepresentation rep2 = new CurrencyRepresentation();
        rep2.setEntity("Estado Unidos");
        rep2.setCurrency("Dollar americano");
        rep2.setAlphabeticCode("USR");
        rep2.setNumericCode("600");
        rep2.setMinorUnit(2);

        @SuppressWarnings("unused")
        CurrencyRepresentation repCreated1 = given().contentType(ContentType.JSON).body(rep1).log()
                .everything().expect().statusCode(201).log().ifError().when().post(baseURI)
                .as(CurrencyRepresentation.class);
        @SuppressWarnings("unused")
        CurrencyRepresentation repCreated2 = given().contentType(ContentType.JSON).body(rep2).log()
                .everything().expect().statusCode(201).log().ifError().when().post(baseURI)
                .as(CurrencyRepresentation.class);

        @SuppressWarnings("rawtypes")
        SearchResultsRepresentation result = given().contentType(ContentType.JSON).log().everything()
                .expect().statusCode(200).log().ifError().when().get(baseURI)
                .as(SearchResultsRepresentation.class);

        // assert
        assertThat(result, is(notNullValue()));
        assertThat(result.getTotalSize(), is(2));
        assertThat(result.getItems().size(), is(2));
    }

    @Test
    public void searchFiltertext() {
        CurrencyRepresentation rep1 = new CurrencyRepresentation();
        rep1.setEntity("Peru");
        rep1.setCurrency("Nuevo sol");
        rep1.setAlphabeticCode("PEN");
        rep1.setNumericCode("604");
        rep1.setMinorUnit(2);

        CurrencyRepresentation rep2 = new CurrencyRepresentation();
        rep2.setEntity("Estado Unidos");
        rep2.setCurrency("Dollar americano");
        rep2.setAlphabeticCode("USR");
        rep2.setNumericCode("600");
        rep2.setMinorUnit(2);

        @SuppressWarnings("unused")
        CurrencyRepresentation repCreated1 = given().contentType(ContentType.JSON).body(rep1).log()
                .everything().expect().statusCode(201).log().ifError().when().post(baseURI)
                .as(CurrencyRepresentation.class);
        @SuppressWarnings("unused")
        CurrencyRepresentation repCreated2 = given().contentType(ContentType.JSON).body(rep2).log()
                .everything().expect().statusCode(201).log().ifError().when().post(baseURI)
                .as(CurrencyRepresentation.class);

        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("filterText", "peru");
        parameters.put("page", "1");
        parameters.put("pageSize", "10");

        @SuppressWarnings("rawtypes")
        SearchResultsRepresentation result = given().parameters(parameters).contentType(ContentType.JSON)
                .log().everything().expect().statusCode(200).log().ifError().when().get(baseURI)
                .as(SearchResultsRepresentation.class);

        // assert
        assertThat(result, is(notNullValue()));
        assertThat(result.getItems().size(), greaterThan(1));
        assertThat(result.getTotalSize(), greaterThan(1));
    }
}
