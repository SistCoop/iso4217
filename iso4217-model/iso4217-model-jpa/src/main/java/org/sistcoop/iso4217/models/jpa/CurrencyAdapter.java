package org.sistcoop.iso4217.models.jpa;

import javax.persistence.EntityManager;

import org.sistcoop.iso4217.models.CurrencyModel;
import org.sistcoop.iso4217.models.jpa.entities.CurrencyEntity;

public class CurrencyAdapter implements CurrencyModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected CurrencyEntity currencyEntity;
	protected EntityManager em;

	public CurrencyAdapter(EntityManager em, CurrencyEntity currencyEntity) {
		this.em = em;
		this.currencyEntity = currencyEntity;
	}

	public CurrencyEntity getCurrencyEntity() {
		return this.currencyEntity;
	}

	public static CurrencyEntity toCurrencyEntity(CurrencyModel model, EntityManager em) {
		if (model instanceof CurrencyAdapter) {
			return ((CurrencyAdapter) model).getCurrencyEntity();
		}
		return em.getReference(CurrencyEntity.class, model.getId());				
	}

	@Override
	public void commit() {
		em.merge(currencyEntity);
	}

	@Override
	public Integer getId() {
		return currencyEntity.getId();
	}

	@Override
	public String getEntity() {
		return currencyEntity.getEntity();
	}

	@Override
	public void setEntity(String entity) {
		currencyEntity.setEntity(entity);
	}

	@Override
	public String getCurrency() {
		return currencyEntity.getCurrency();
	}

	@Override
	public void setCurrency(String currency) {
		currencyEntity.setCurrency(currency);
	}

	@Override
	public String getAlphabeticCode() {
		return currencyEntity.getAlphabeticCode();
	}

	@Override
	public String getNumericCode() {
		return currencyEntity.getNumericCode();
	}

	@Override
	public int getMinorUnit() {
		return currencyEntity.getMinorUnit();
	}

	@Override
	public void setMinorUnit(int minorUnit) {
		currencyEntity.setMinorUnit(minorUnit);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getAlphabeticCode() == null) ? 0 : getAlphabeticCode().hashCode());
		result = prime * result + ((getNumericCode() == null) ? 0 : getNumericCode().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof CurrencyModel))
			return false;
		CurrencyModel other = (CurrencyModel) obj;		
		if (getAlphabeticCode() == null) {
			if (other.getAlphabeticCode() != null)
				return false;
		} else if (!getAlphabeticCode().equals(other.getAlphabeticCode()))
			return false;
		if (getNumericCode() == null) {
			if (other.getNumericCode() != null)
				return false;
		} else if (!getNumericCode().equals(other.getNumericCode()))
			return false;
		return true;
	}

}
