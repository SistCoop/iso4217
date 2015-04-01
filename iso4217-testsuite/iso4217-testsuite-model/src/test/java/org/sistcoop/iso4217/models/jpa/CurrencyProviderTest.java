package org.sistcoop.iso4217.models.jpa;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sistcoop.iso4217.models.CurrencyModel;
import org.sistcoop.iso4217.models.CurrencyProvider;
import org.sistcoop.iso4217.models.jpa.CurrencyAdapter;
import org.sistcoop.iso4217.models.jpa.JpaCurrencyProvider;
import org.sistcoop.iso4217.models.jpa.entities.CurrencyEntity;
import org.sistcoop.iso4217.provider.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(Arquillian.class)
@UsingDataSet("empty.xml")
public class CurrencyProviderTest {

	Logger log = LoggerFactory.getLogger(CurrencyProviderTest.class);

	@Inject
	private CurrencyProvider currencyProvider;

	@Deployment
	public static WebArchive createDeployment() {
		File[] dependencies = Maven.resolver()
				.resolve("org.slf4j:slf4j-simple:1.7.10").withoutTransitivity()
				.asFile();

		WebArchive war = ShrinkWrap
				.create(WebArchive.class, "test.war")
				/** persona-model-api **/
				.addClass(Provider.class)
				.addClass(CurrencyProvider.class)

				.addPackage(CurrencyModel.class.getPackage())

				/** persona-model-jpa **/
				.addClass(JpaCurrencyProvider.class)
				.addClass(CurrencyAdapter.class)

				.addPackage(CurrencyEntity.class.getPackage())

				.addAsResource("META-INF/jpaTest-persistence.xml", "META-INF/persistence.xml")
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
				.addAsWebInfResource("jpaTest-ds.xml");

		war.addAsLibraries(dependencies);

		return war;
	}

	@Test
	public void addCurrency() {
		CurrencyModel model = currencyProvider.addCurrency("PERU", "Nuevo Sol", "PEN", "604", 2);

		assertThat(model, is(notNullValue()));
		assertThat(model.getId(), is(notNullValue()));
	}

	@Test
	public void getCurrencyByAlphabeticCode() {
		CurrencyModel model1 = currencyProvider.addCurrency("PERU", "Nuevo Sol", "PEN", "604", 2);		
		
		String alphabeticCode = model1.getAlphabeticCode();
		
		CurrencyModel model2 = currencyProvider.getCurrencyByAlphabeticCode(alphabeticCode);

		assertThat(model1, is(equalTo(model2)));
	}
	
	@Test
	public void getCurrencyByNumericCode() {
		CurrencyModel model1 = currencyProvider.addCurrency("PERU", "Nuevo Sol", "PEN", "604", 2);		
		
		String numericCode = model1.getNumericCode();
		
		CurrencyModel model2 = currencyProvider.getCurrencyByNumericCode(numericCode);

		assertThat(model1, is(equalTo(model2)));
	}
		
	@Test
	public void getCurrencies() {
		currencyProvider.addCurrency("PERU", "Nuevo Sol", "PEN", "604", 2);
		
		List<CurrencyModel> models = currencyProvider.getCurrencies();
		for (CurrencyModel model : models) {
			assertThat(model, is(notNullValue()));
		}

		assertThat(models.size(), is(1));
	}

	@Test
	public void getCurrenciesFirstResulAndLimit() {
		currencyProvider.addCurrency("PERU", "Nuevo Sol", "PEN", "604", 2);
				
		List<CurrencyModel> models = currencyProvider.getCurrencies(0, 10);
		for (CurrencyModel model : models) {
			assertThat(model, is(notNullValue()));
		}

		assertThat(models.size(), is(1));
	}

	@Test
	public void getCurrenciesForFilterText() {
		currencyProvider.addCurrency("PERU", "Nuevo Sol", "PEN", "604", 2);
		
		List<CurrencyModel> models = currencyProvider.getCurrencies("P");
		for (CurrencyModel model : models) {
			assertThat(model, is(notNullValue()));
		}

		assertThat(models.size(), is(1));
	}

	@Test
	public void getCurrenciesForFilterTextFirstResulAndLimit() {
		currencyProvider.addCurrency("PERU", "Nuevo Sol", "PEN", "604", 2);
		
		List<CurrencyModel> models = currencyProvider.getCurrencies("P", 0, 10);
		for (CurrencyModel model : models) {
			assertThat(model, is(notNullValue()));
		}

		assertThat(models.size(), is(1));
	}

	@Test
	public void removeCurrency() {
		CurrencyModel model1 = currencyProvider.addCurrency("PERU", "Nuevo Sol", "PEN", "604", 2);
		
		String alphabeticCode = model1.getAlphabeticCode();
		boolean result = currencyProvider.removeCurrency(model1);

		CurrencyModel model2 = currencyProvider.getCurrencyByAlphabeticCode(alphabeticCode);

		assertThat(result, is(true));
		assertThat(model2, is(nullValue()));
	}

}
