package org.sistcoop.iso3166.models.utils.jpa;

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
import org.sistcoop.iso3166.models.CountryCodeModel;
import org.sistcoop.iso3166.models.CountryCodeProvider;
import org.sistcoop.iso3166.models.jpa.CountryCodeAdapter;
import org.sistcoop.iso3166.models.jpa.JpaCountryCodeProvider;
import org.sistcoop.iso3166.models.jpa.entities.CountryCodeEntity;
import org.sistcoop.iso3166.models.utils.RepresentationToModel;
import org.sistcoop.iso3166.provider.Provider;
import org.sistcoop.iso3166.representations.idm.CountryCodeRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(Arquillian.class)
@UsingDataSet("empty.xml")
public class RepresentationToModelTest {

	Logger log = LoggerFactory.getLogger(RepresentationToModelTest.class);

	@PersistenceContext
	private EntityManager em;
		
	@Inject
	private CountryCodeProvider countryCodeProvider;
	
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
				.addClass(CountryCodeRepresentation.class)
				
				/**persona-model-api**/
				.addClass(Provider.class)										
				.addClass(CountryCodeProvider.class)				
				
				.addPackage(CountryCodeModel.class.getPackage())				
												
				/**persona-model-jpa**/				
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
	public void commit() {
		CountryCodeRepresentation rep = new CountryCodeRepresentation();
		rep.setAlpha2Code("PE");
		rep.setAlpha3Code("PER");
		rep.setNumericCode("051");
		rep.setIndependent(true);
		rep.setStatus(true);
		rep.setShortNameEn("Peru");
		rep.setShortNameUppercaseEn("PERU");
		rep.setFullNameEn("Republic of Peru");
		
		CountryCodeModel model = representationToModel.createCountryCode(rep, countryCodeProvider);
					
		assertThat(model, is(notNullValue()));
		assertThat(model.getAlpha2Code(), is(notNullValue()));
		assertThat(model.getAlpha3Code(), is(notNullValue()));
		assertThat(model.getNumericCode(), is(notNullValue()));
		assertThat(model.isIndependent(), is(notNullValue()));
		assertThat(model.isStatus(), is(notNullValue()));
		assertThat(model.getShortNameEn(), is(notNullValue()));
		assertThat(model.getShortNameUppercaseEn(), is(notNullValue()));
		assertThat(model.getFullNameEn(), is(notNullValue()));
		
	}
	
}
