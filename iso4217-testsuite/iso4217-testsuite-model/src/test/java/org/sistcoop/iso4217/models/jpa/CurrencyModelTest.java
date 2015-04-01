package org.sistcoop.iso4217.models.jpa;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.File;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

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
public class CurrencyModelTest {

	Logger log = LoggerFactory.getLogger(CurrencyModelTest.class);

	@PersistenceContext
	private EntityManager em;

	@Resource           
	private UserTransaction utx; 
		
	@Inject
	private CurrencyProvider currencyProvider;
	
	@Deployment
	public static WebArchive createDeployment() {
		File[] dependencies = Maven.resolver()
				.resolve("org.slf4j:slf4j-simple:1.7.10")
				.withoutTransitivity().asFile();

		WebArchive war = ShrinkWrap.create(WebArchive.class, "test.war")
				/**persona-model-api**/
				.addClass(Provider.class)										
				.addClass(CurrencyProvider.class)				
				
				.addPackage(CurrencyModel.class.getPackage())				
												
				/**persona-model-jpa**/				
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
	public void commit() {
		CurrencyModel model1 = currencyProvider.addCurrency("PERU", "Nuevo Sol", "PEN", "604", 2);				
		
		String alphabeticCode = model1.getAlphabeticCode();
		String newEntity = "Peru new";
		String newCurrency = "Nuevo Sol new";
		
		model1.setEntity(newEntity);
		model1.setCurrency(newCurrency);		
		model1.commit();	

		CurrencyModel model2 = currencyProvider.getCurrencyByAlphabeticCode(alphabeticCode);
				
		assertThat(model2.getEntity(), is(equalTo(newEntity)));
		assertThat(model2.getCurrency(), is(equalTo(newCurrency)));
	}	

	@Test
	public void testAttributes() {
		CurrencyModel model = currencyProvider.addCurrency("PERU", "Nuevo Sol", "PEN", "604", 2);						
		
		assertThat(model.getId(), is(notNullValue()));
		assertThat(model.getEntity(), is(notNullValue()));
		assertThat(model.getCurrency(), is(notNullValue()));
		assertThat(model.getAlphabeticCode(), is(notNullValue()));
		assertThat(model.getNumericCode(), is(notNullValue()));
		assertThat(model.getMinorUnit(), is(notNullValue()));
	}
		
}
