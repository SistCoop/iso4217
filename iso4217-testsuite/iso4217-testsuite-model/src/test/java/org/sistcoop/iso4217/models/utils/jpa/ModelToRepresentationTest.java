package org.sistcoop.iso4217.models.utils.jpa;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.math.BigDecimal;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
import org.sistcoop.iso4217.models.DenominationModel;
import org.sistcoop.iso4217.models.jpa.CurrencyAdapter;
import org.sistcoop.iso4217.models.jpa.DenominationAdapter;
import org.sistcoop.iso4217.models.jpa.JpaCurrencyProvider;
import org.sistcoop.iso4217.models.jpa.entities.CurrencyEntity;
import org.sistcoop.iso4217.models.utils.ModelToRepresentation;
import org.sistcoop.iso4217.provider.Provider;
import org.sistcoop.iso4217.representations.idm.CurrencyRepresentation;
import org.sistcoop.iso4217.representations.idm.DenominationRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(Arquillian.class)
@UsingDataSet("empty.xml")
public class ModelToRepresentationTest {

	Logger log = LoggerFactory.getLogger(ModelToRepresentationTest.class);

	@PersistenceContext
	private EntityManager em;
		
	@Inject
	private CurrencyProvider currencyProvider;
	
	@Deployment
	public static WebArchive createDeployment() {
		File[] dependencies = Maven.resolver()
				.resolve("org.slf4j:slf4j-simple:1.7.10")
				.withoutTransitivity().asFile();

		WebArchive war = ShrinkWrap.create(WebArchive.class, "test.war")
				/**Model to representation**/
				.addClass(ModelToRepresentation.class)
				.addPackage(CurrencyRepresentation.class.getPackage())
				
				/**persona-model-api**/
				.addClass(Provider.class)										
				.addClass(CurrencyProvider.class)				
				
				.addPackage(CurrencyModel.class.getPackage())				
												
				/**persona-model-jpa**/				
				.addClass(JpaCurrencyProvider.class)
				.addClass(CurrencyAdapter.class)	
				.addClass(DenominationAdapter.class)	
				
				.addPackage(CurrencyEntity.class.getPackage())
				
				.addAsResource("META-INF/jpaTest-persistence.xml", "META-INF/persistence.xml")
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
				.addAsWebInfResource("jpaTest-ds.xml");

		war.addAsLibraries(dependencies);

		return war;
	}				
	
	@Test
	public void toRepresentationCurrency() {
		CurrencyModel model = currencyProvider.addCurrency("PERU", "Nuevo Sol", "PEN", "604", 2);				
		
		CurrencyRepresentation currencyRepresentation = ModelToRepresentation.toRepresentation(model);
				
		assertThat(currencyRepresentation, is(notNullValue()));
		assertThat(currencyRepresentation.getEntity(), is(notNullValue()));
		assertThat(currencyRepresentation.getCurrency(), is(notNullValue()));
		assertThat(currencyRepresentation.getAlphabeticCode(), is(notNullValue()));
		assertThat(currencyRepresentation.getNumericCode(), is(notNullValue()));
		assertThat(currencyRepresentation.getMinorUnit(), is(notNullValue()));				
	}	
	
	@Test
	public void toRepresentationDenomination() {
		CurrencyModel model = currencyProvider.addCurrency("PERU", "Nuevo Sol", "PEN", "604", 2);								
		DenominationModel denominationModel = model.addDenomination(BigDecimal.TEN);
		
		DenominationRepresentation denominationRepresentation = ModelToRepresentation.toRepresentation(denominationModel);
				
		assertThat(denominationRepresentation, is(notNullValue()));
		assertThat(denominationRepresentation.getValue(), is(notNullValue()));			
		
	}
	
}
