package org.sistcoop.iso3166.models.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.sistcoop.iso3166.models.CountryCodeModel;
import org.sistcoop.iso3166.models.CountryCodeProvider;
import org.sistcoop.iso3166.models.jpa.entities.CountryCodeEntity;

@Named
@Stateless
@Local(CountryCodeProvider.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class JpaCountryCodeProvider implements CountryCodeProvider {

	@PersistenceContext
	protected EntityManager em;

	@Override
	public void close() {
		// TODO Auto-generated method stub
	}

	@Override
	public CountryCodeModel addCountryCode(String alpha2Code,
			String alpha3Code, String numericCode, boolean independent,
			boolean status, String shortNameEn, String shortNameUppercaseEn,
			String fullNameEn) {
		CountryCodeEntity entity = new CountryCodeEntity();
		entity.setAlpha2Code(alpha2Code);
		entity.setAlpha3Code(alpha3Code);
		entity.setNumericCode(numericCode);
		entity.setIndependent(independent);
		entity.setStatus(status);
		entity.setShortNameEn(shortNameEn);
		entity.setShortNameUppercaseEn(shortNameUppercaseEn);
		entity.setFullNameEn(fullNameEn);
		em.persist(entity);
		return new CountryCodeAdapter(em, entity);
	}

	@Override
	public boolean removeCountryCode(CountryCodeModel countryCodeModel) {			
		CountryCodeEntity countryCodeEntity = em.find(CountryCodeEntity.class, countryCodeModel.getId());
        if (countryCodeEntity == null) return false;
        em.remove(countryCodeEntity);
        return true;      
	}

	@Override
	public CountryCodeModel getCountryCodeByAlpha2Code(String alpha2Code) {
		TypedQuery<CountryCodeEntity> query = em.createNamedQuery(CountryCodeEntity.findByAlpha2Code, CountryCodeEntity.class);
		query.setParameter("alpha2Code", alpha2Code);		
		List<CountryCodeEntity> results = query.getResultList();
		if (results.size() == 0)
			return null;
		return new CountryCodeAdapter(em, results.get(0));
	}

	@Override
	public CountryCodeModel getCountryCodeByAlpha3Code(String alpha3Code) {
		TypedQuery<CountryCodeEntity> query = em.createNamedQuery(CountryCodeEntity.findByAlpha3Code, CountryCodeEntity.class);
		query.setParameter("alpha3Code", alpha3Code);		
		List<CountryCodeEntity> results = query.getResultList();
		if (results.size() == 0)
			return null;
		return new CountryCodeAdapter(em, results.get(0));
	}

	@Override
	public CountryCodeModel getCountryCodeByNumericCode(String numericCode) {
		TypedQuery<CountryCodeEntity> query = em.createNamedQuery(CountryCodeEntity.findByNumericCode, CountryCodeEntity.class);
		query.setParameter("numericCode", numericCode);		
		List<CountryCodeEntity> results = query.getResultList();
		if (results.size() == 0)
			return null;
		return new CountryCodeAdapter(em, results.get(0));
	}

	@Override
	public int getCountryCodesCount() {
		Object count = em.createNamedQuery(CountryCodeEntity.count).getSingleResult();		
		return (Integer) count;
	}
	
	@Override
	public List<CountryCodeModel> getCountryCodes() {
		return getCountryCodes(-1, -1);
	}

	@Override
	public List<CountryCodeModel> getCountryCodes(String filterText) {
		return getCountryCodes(filterText, -1, -1);
	}
	
	@Override
	public List<CountryCodeModel> getCountryCodes(int firstResult, int maxResults) {
		TypedQuery<CountryCodeEntity> query = em.createNamedQuery(CountryCodeEntity.findAll, CountryCodeEntity.class);
		if (firstResult != -1) {
			query.setFirstResult(firstResult);
		}
		if (maxResults != -1) {
			query.setMaxResults(maxResults);
		}
		List<CountryCodeEntity> results = query.getResultList();
		List<CountryCodeModel> users = new ArrayList<CountryCodeModel>();
		for (CountryCodeEntity entity : results)
			users.add(new CountryCodeAdapter(em, entity));
		return users;
	}

	@Override
	public List<CountryCodeModel> getCountryCodes(String filterText, int firstResult, int maxResults) {
		if (filterText == null)
			filterText = "";

		TypedQuery<CountryCodeEntity> query = em.createNamedQuery(CountryCodeEntity.findByFilterText, CountryCodeEntity.class);
		query.setParameter("filterText", "%" + filterText.toUpperCase() + "%");
		if (firstResult != -1) {
			query.setFirstResult(firstResult);
		}
		if (maxResults != -1) {
			query.setMaxResults(maxResults);
		}
		List<CountryCodeEntity> results = query.getResultList();
		List<CountryCodeModel> users = new ArrayList<CountryCodeModel>();
		for (CountryCodeEntity entity : results)
			users.add(new CountryCodeAdapter(em, entity));
		return users;
	}

}
