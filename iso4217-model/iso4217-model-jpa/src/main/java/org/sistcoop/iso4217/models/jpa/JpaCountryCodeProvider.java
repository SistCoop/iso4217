package org.sistcoop.iso4217.models.jpa;

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

import org.sistcoop.iso4217.models.CountryCodeModel;
import org.sistcoop.iso4217.models.CountryCodeProvider;
import org.sistcoop.iso4217.models.jpa.entities.CurrencyEntity;

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
		CurrencyEntity entity = new CurrencyEntity();
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
		CurrencyEntity countryCodeEntity = em.find(CurrencyEntity.class, countryCodeModel.getId());
        if (countryCodeEntity == null) return false;
        em.remove(countryCodeEntity);
        return true;      
	}

	@Override
	public CountryCodeModel getCountryCodeByAlpha2Code(String alpha2Code) {
		TypedQuery<CurrencyEntity> query = em.createNamedQuery(CurrencyEntity.findByAlpha2Code, CurrencyEntity.class);
		query.setParameter("alpha2Code", alpha2Code);		
		List<CurrencyEntity> results = query.getResultList();
		if (results.size() == 0)
			return null;
		return new CountryCodeAdapter(em, results.get(0));
	}

	@Override
	public CountryCodeModel getCountryCodeByAlpha3Code(String alpha3Code) {
		TypedQuery<CurrencyEntity> query = em.createNamedQuery(CurrencyEntity.findByAlpha3Code, CurrencyEntity.class);
		query.setParameter("alpha3Code", alpha3Code);		
		List<CurrencyEntity> results = query.getResultList();
		if (results.size() == 0)
			return null;
		return new CountryCodeAdapter(em, results.get(0));
	}

	@Override
	public CountryCodeModel getCountryCodeByNumericCode(String numericCode) {
		TypedQuery<CurrencyEntity> query = em.createNamedQuery(CurrencyEntity.findByNumericCode, CurrencyEntity.class);
		query.setParameter("numericCode", numericCode);		
		List<CurrencyEntity> results = query.getResultList();
		if (results.size() == 0)
			return null;
		return new CountryCodeAdapter(em, results.get(0));
	}

	@Override
	public int getCountryCodesCount() {
		Object count = em.createNamedQuery(CurrencyEntity.count).getSingleResult();		
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
		TypedQuery<CurrencyEntity> query = em.createNamedQuery(CurrencyEntity.findAll, CurrencyEntity.class);
		if (firstResult != -1) {
			query.setFirstResult(firstResult);
		}
		if (maxResults != -1) {
			query.setMaxResults(maxResults);
		}
		List<CurrencyEntity> results = query.getResultList();
		List<CountryCodeModel> users = new ArrayList<CountryCodeModel>();
		for (CurrencyEntity entity : results)
			users.add(new CountryCodeAdapter(em, entity));
		return users;
	}

	@Override
	public List<CountryCodeModel> getCountryCodes(String filterText, int firstResult, int maxResults) {
		if (filterText == null)
			filterText = "";

		TypedQuery<CurrencyEntity> query = em.createNamedQuery(CurrencyEntity.findByFilterText, CurrencyEntity.class);
		query.setParameter("filterText", "%" + filterText.toUpperCase() + "%");
		if (firstResult != -1) {
			query.setFirstResult(firstResult);
		}
		if (maxResults != -1) {
			query.setMaxResults(maxResults);
		}
		List<CurrencyEntity> results = query.getResultList();
		List<CountryCodeModel> users = new ArrayList<CountryCodeModel>();
		for (CurrencyEntity entity : results)
			users.add(new CountryCodeAdapter(em, entity));
		return users;
	}

}
