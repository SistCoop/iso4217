package org.sistcoop.iso4217.models.utils.jpa;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.File;

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
import org.sistcoop.iso4217.models.jpa.CurrencyAdapter;
import org.sistcoop.iso4217.models.jpa.JpaCurrencyProvider;
import org.sistcoop.iso4217.models.jpa.entities.CurrencyEntity;
import org.sistcoop.iso4217.models.utils.RepresentationToModel;
import org.sistcoop.iso4217.provider.Provider;
import org.sistcoop.iso4217.representations.idm.CurrencyRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(Arquillian.class)
@UsingDataSet("empty.xml")
public class RepresentationToModelTest {

	Logger log = LoggerFactory.getLogger(RepresentationToModelTest.class);

	@PersistenceContext
	private EntityManager em;
		
	@Inject
	private CurrencyProvider currencyProvider;
	
	@Inject
	private RepresentationToModel representationToModel;
	
	@Deployment
	public static WebArchive createDeployment() {
		File[] dependencies = Maven.resolver()
				.resolve("org.slf4j:slf4j-simple:1.7.10")
				.withoutTransitivity().asFile();

		WebArchive war = ShrinkWrap.create(WebArchive.class, "test.war")
				/**Model to representation**/
				.addClass(RepresentationToModel.class)
				.addClass(CurrencyRepresentation.class)
				
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
	public void representatioToModel() {
		CurrencyRepresentation rep = new CurrencyRepresentation();
		rep.setEntity("PERU");;
		rep.setCurrency("Nuevo Sol");
		rep.setAlphabeticCode("PEN");
		rep.setNumericCode("604");	
		rep.setMinorUnit(2);
		
		CurrencyModel model = representationToModel.createCurrency(rep, currencyProvider);
					
		assertThat(model, is(notNullValue()));
		assertThat(model.getEntity(), is(notNullValue()));
		assertThat(model.getCurrency(), is(notNullValue()));
		assertThat(model.getAlphabeticCode(), is(notNullValue()));
		assertThat(model.getNumericCode(), is(notNullValue()));
		assertThat(model.getMinorUnit(), is(notNullValue()));
		
	}
	
}
