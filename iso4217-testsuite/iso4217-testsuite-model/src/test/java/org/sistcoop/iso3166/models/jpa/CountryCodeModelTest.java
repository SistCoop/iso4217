package org.sistcoop.iso3166.models.jpa;


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
public class CountryCodeModelTest {

	Logger log = LoggerFactory.getLogger(CountryCodeModelTest.class);

	@PersistenceContext
	private EntityManager em;

	@Resource           
	private UserTransaction utx; 
		
	@Inject
	private CountryCodeProvider countryCodeProvider;
	
	@Deployment
	public static WebArchive createDeployment() {
		File[] dependencies = Maven.resolver()
				.resolve("org.slf4j:slf4j-simple:1.7.10")
				.withoutTransitivity().asFile();

		WebArchive war = ShrinkWrap.create(WebArchive.class, "test.war")
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
		CountryCodeModel model1 = countryCodeProvider.addCountryCode("PE", "PER", "051", true, true, "Peru", "PERU", "Republic of Peru");				
		
		String alpha2Code = model1.getAlpha2Code();
		String newShortNameEn = "peru arriba";
		String newShortNameUppercaseEn = "PERU ARRIBA";
		
		model1.setShortNameEn(newShortNameEn);
		model1.setShortNameUppercaseEn(newShortNameUppercaseEn);
		model1.commit();	

		CountryCodeModel model2 = countryCodeProvider.getCountryCodeByAlpha2Code(alpha2Code);;
				
		assertThat(model2.getShortNameEn(), is(equalTo(newShortNameEn)));
		assertThat(model2.getShortNameUppercaseEn(), is(equalTo(newShortNameUppercaseEn)));
	}	

	@Test
	public void testAttributes() {
		CountryCodeModel model = countryCodeProvider.addCountryCode("PE", "PER", "051", true, true, "Peru", "PERU", "Republic of Peru");						
		
		assertThat(model.getId(), is(notNullValue()));
		assertThat(model.getAlpha2Code(), is(notNullValue()));
		assertThat(model.getAlpha3Code(), is(notNullValue()));
		assertThat(model.getNumericCode(), is(notNullValue()));
		assertThat(model.isIndependent(), is(true));
		assertThat(model.isStatus(), is(true));
		assertThat(model.getShortNameEn(), is(notNullValue()));
		assertThat(model.getShortNameUppercaseEn(), is(notNullValue()));
		assertThat(model.getFullNameEn(), is(notNullValue()));	
	}
		
}
