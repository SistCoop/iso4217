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

import org.sistcoop.iso4217.models.CurrencyModel;
import org.sistcoop.iso4217.models.CurrencyProvider;
import org.sistcoop.iso4217.models.jpa.entities.CurrencyEntity;

@Named
@Stateless
@Local(CurrencyProvider.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class JpaCurrencyProvider implements CurrencyProvider {

	@PersistenceContext
	protected EntityManager em;

	@Override
	public void close() {
		// TODO Auto-generated method stub
	}

	@Override
	public CurrencyModel addCurrency(String entity, String currency, String alphabeticCode, String numericCode, int minorUnit) {
		CurrencyEntity currencyEntity = new CurrencyEntity();
		currencyEntity.setEntity(entity);
		currencyEntity.setCurrency(currency);
		currencyEntity.setAlphabeticCode(alphabeticCode);
		currencyEntity.setNumericCode(numericCode);
		currencyEntity.setMinorUnit(minorUnit);
		em.persist(currencyEntity);
		return new CurrencyAdapter(em, currencyEntity);
	}

	@Override
	public boolean removeCurrency(CurrencyModel currencyModel) {
		CurrencyEntity countryCodeEntity = em.find(CurrencyEntity.class, currencyModel.getId());
		if (countryCodeEntity == null)
			return false;
		em.remove(countryCodeEntity);
		return true;
	}

	@Override
	public CurrencyModel getCurrencyByAlphabeticCode(String alphabeticCode) {
		TypedQuery<CurrencyEntity> query = em.createNamedQuery(CurrencyEntity.findByAlphabeticCode, CurrencyEntity.class);
		query.setParameter("alphabeticCode", alphabeticCode);
		List<CurrencyEntity> results = query.getResultList();
		if (results.size() == 0)
			return null;
		return new CurrencyAdapter(em, results.get(0));
	}

	@Override
	public CurrencyModel getCurrencyByNumericCode(String numericCode) {
		TypedQuery<CurrencyEntity> query = em.createNamedQuery(CurrencyEntity.findByNumericCode, CurrencyEntity.class);
		query.setParameter("numericCode", numericCode);
		List<CurrencyEntity> results = query.getResultList();
		if (results.size() == 0)
			return null;
		return new CurrencyAdapter(em, results.get(0));
	}

	@Override
	public int getCurrencyCount() {
		Object count = em.createNamedQuery(CurrencyEntity.count).getSingleResult();
		return (Integer) count;
	}

	@Override
	public List<CurrencyModel> getCurrencies() {
		return getCurrencies(-1, -1);
	}

	@Override
	public List<CurrencyModel> getCurrencies(String filterText) {
		return getCurrencies(filterText, -1, -1);
	}

	@Override
	public List<CurrencyModel> getCurrencies(int firstResult, int maxResults) {
		TypedQuery<CurrencyEntity> query = em.createNamedQuery(CurrencyEntity.findAll, CurrencyEntity.class);
		if (firstResult != -1) {
			query.setFirstResult(firstResult);
		}
		if (maxResults != -1) {
			query.setMaxResults(maxResults);
		}
		List<CurrencyEntity> results = query.getResultList();
		List<CurrencyModel> users = new ArrayList<CurrencyModel>();
		for (CurrencyEntity entity : results)
			users.add(new CurrencyAdapter(em, entity));
		return users;
	}

	@Override
	public List<CurrencyModel> getCurrencies(String filterText, int firstResult, int maxResults) {
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
		List<CurrencyModel> users = new ArrayList<CurrencyModel>();
		for (CurrencyEntity entity : results)
			users.add(new CurrencyAdapter(em, entity));
		return users;
	}

}
