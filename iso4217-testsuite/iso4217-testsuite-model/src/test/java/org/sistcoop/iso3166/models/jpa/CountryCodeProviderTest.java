package org.sistcoop.iso3166.models.jpa;

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
import org.sistcoop.iso3166.models.CountryCodeModel;
import org.sistcoop.iso3166.models.CountryCodeProvider;
import org.sistcoop.iso3166.models.jpa.CountryCodeAdapter;
import org.sistcoop.iso3166.models.jpa.JpaCountryCodeProvider;
import org.sistcoop.iso3166.models.jpa.entities.CountryCodeEntity;
import org.sistcoop.iso3166.provider.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(Arquillian.class)
@UsingDataSet("empty.xml")
public class CountryCodeProviderTest {

	Logger log = LoggerFactory.getLogger(CountryCodeProviderTest.class);

	@Inject
	private CountryCodeProvider countryCodeProvider;

	@Deployment
	public static WebArchive createDeployment() {
		File[] dependencies = Maven.resolver()
				.resolve("org.slf4j:slf4j-simple:1.7.10").withoutTransitivity()
				.asFile();

		WebArchive war = ShrinkWrap
				.create(WebArchive.class, "test.war")
				/** persona-model-api **/
				.addClass(Provider.class)
				.addClass(CountryCodeProvider.class)

				.addPackage(CountryCodeModel.class.getPackage())

				/** persona-model-jpa **/
				.addClass(JpaCountryCodeProvider.class)
				.addClass(CountryCodeAdapter.class)

				.addPackage(CountryCodeEntity.class.getPackage())

				.addAsResource("META-INF/jpaTest-persistence.xml", "META-INF/persistence.xml")
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
				.addAsWebInfResource("jpaTest-ds.xml");

		war.addAsLibraries(dependencies);

		return war;
	}

	@Test
	public void addCountryCode() {
		CountryCodeModel model = countryCodeProvider.addCountryCode("PE", "PER", "051", true, true, "Peru", "PERU", "Republic of Peru");

		assertThat(model, is(notNullValue()));
		assertThat(model.getId(), is(notNullValue()));
	}

	@Test
	public void getCountryCodeByAlpha2Code() {
		CountryCodeModel model1 = countryCodeProvider.addCountryCode("PE", "PER", "051", true, true, "Peru", "PERU", "Republic of Peru");		
		
		String alpha2Code = model1.getAlpha2Code();
		
		CountryCodeModel model2 = countryCodeProvider.getCountryCodeByAlpha2Code(alpha2Code);

		assertThat(model1, is(equalTo(model2)));
	}

	@Test
	public void getCountryCodeByAlpha3Code() {
		CountryCodeModel model1 = countryCodeProvider.addCountryCode("PE", "PER", "051", true, true, "Peru", "PERU", "Republic of Peru");		
		
		String alpha3Code = model1.getAlpha3Code();
		
		CountryCodeModel model2 = countryCodeProvider.getCountryCodeByAlpha3Code(alpha3Code);

		assertThat(model1, is(equalTo(model2)));
	}
	
	@Test
	public void getCountryCodeByNumericCode() {
		CountryCodeModel model1 = countryCodeProvider.addCountryCode("PE", "PER", "051", true, true, "Peru", "PERU", "Republic of Peru");		
		
		String numericCode = model1.getNumericCode();
		
		CountryCodeModel model2 = countryCodeProvider.getCountryCodeByNumericCode(numericCode);

		assertThat(model1, is(equalTo(model2)));
	}
		
	@Test
	public void getCountryCodes() {
		countryCodeProvider.addCountryCode("PE", "PER", "051", true, true, "Peru", "PERU", "Republic of Peru");
		
		List<CountryCodeModel> models = countryCodeProvider.getCountryCodes();
		for (CountryCodeModel model : models) {
			assertThat(model, is(notNullValue()));
		}

		assertThat(models.size(), is(1));
	}

	@Test
	public void getCountryCodesFirstResulAndLimit() {
		countryCodeProvider.addCountryCode("PE", "PER", "051", true, true, "Peru", "PERU", "Republic of Peru");
				
		List<CountryCodeModel> models = countryCodeProvider.getCountryCodes(0, 10);
		for (CountryCodeModel model : models) {
			assertThat(model, is(notNullValue()));
		}

		assertThat(models.size(), is(1));
	}

	@Test
	public void getCountryCodesForFilterText() {
		countryCodeProvider.addCountryCode("PE", "PER", "051", true, true, "Peru", "PERU", "Republic of Peru");
		
		List<CountryCodeModel> models = countryCodeProvider.getCountryCodes("P");
		for (CountryCodeModel model : models) {
			assertThat(model, is(notNullValue()));
		}

		assertThat(models.size(), is(1));
	}

	@Test
	public void getCountryCodesForFilterTextFirstResulAndLimit() {
		countryCodeProvider.addCountryCode("PE", "PER", "051", true, true, "Peru", "PERU", "Republic of Peru");
		
		List<CountryCodeModel> models = countryCodeProvider.getCountryCodes("P", 0, 10);
		for (CountryCodeModel model : models) {
			assertThat(model, is(notNullValue()));
		}

		assertThat(models.size(), is(1));
	}

	@Test
	public void removeCountryCode() {
		CountryCodeModel model1 = countryCodeProvider.addCountryCode("PE", "PER", "051", true, true, "Peru", "PERU", "Republic of Peru");
		
		String alpha2Code = model1.getAlpha2Code();
		boolean result = countryCodeProvider.removeCountryCode(model1);

		CountryCodeModel model2 = countryCodeProvider.getCountryCodeByAlpha2Code(alpha2Code);

		assertThat(result, is(true));
		assertThat(model2, is(nullValue()));
	}

}
