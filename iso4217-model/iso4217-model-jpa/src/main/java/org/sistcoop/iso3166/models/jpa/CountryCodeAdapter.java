package org.sistcoop.iso3166.models.jpa;

import javax.persistence.EntityManager;

import org.sistcoop.iso3166.models.CountryCodeModel;
import org.sistcoop.iso3166.models.jpa.entities.CountryCodeEntity;

public class CountryCodeAdapter implements CountryCodeModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected CountryCodeEntity countryCodeEntity;
	protected EntityManager em;

	public CountryCodeAdapter(EntityManager em, CountryCodeEntity countryCodeEntity) {
		this.em = em;
		this.countryCodeEntity = countryCodeEntity;
	}

	public CountryCodeEntity getCountryCodeEntity() {		
		return this.countryCodeEntity;
	}

	public static CountryCodeEntity toCountryCodeEntity(CountryCodeModel model, EntityManager em) {			
		if (model instanceof CountryCodeAdapter) {
			return ((CountryCodeAdapter) model).getCountryCodeEntity();
		}
		return em.getReference(CountryCodeEntity.class, model.getId());						
	}

	@Override
	public void commit() {
		em.merge(countryCodeEntity);
	}

	@Override
	public Integer getId() {
		return countryCodeEntity.getId();
	}
	
	@Override
	public String getAlpha2Code() {
		return countryCodeEntity.getAlpha2Code();
	}

	@Override
	public String getAlpha3Code() {
		return countryCodeEntity.getAlpha3Code();
	}

	@Override
	public String getNumericCode() {
		return countryCodeEntity.getNumericCode();
	}

	@Override
	public boolean isIndependent() {
		return countryCodeEntity.isIndependent();
	}

	@Override
	public void setIndependent(boolean independent) {
		countryCodeEntity.setIndependent(independent);
	}

	@Override
	public boolean isStatus() {
		return countryCodeEntity.isStatus();
	}

	@Override
	public void setStatus(boolean status) {
		countryCodeEntity.setStatus(status);
	}

	@Override
	public String getShortNameEn() {
		return countryCodeEntity.getShortNameEn();
	}

	@Override
	public void setShortNameEn(String shortNameEn) {
		countryCodeEntity.setShortNameEn(shortNameEn);
	}

	@Override
	public String getShortNameUppercaseEn() {
		return countryCodeEntity.getShortNameUppercaseEn();
	}

	@Override
	public void setShortNameUppercaseEn(String shortNameUppercaseEn) {
		countryCodeEntity.setShortNameUppercaseEn(shortNameUppercaseEn);
	}

	@Override
	public String getFullNameEn() {
		return countryCodeEntity.getFullNameEn();
	}

	@Override
	public void setFullNameEn(String fullNameEn) {
		countryCodeEntity.setFullNameEn(fullNameEn);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((getAlpha2Code() == null) ? 0 : getAlpha2Code().hashCode());
		result = prime * result
				+ ((getAlpha3Code() == null) ? 0 : getAlpha3Code().hashCode());
		result = prime * result
				+ ((getNumericCode() == null) ? 0 : getNumericCode().hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if(!(obj instanceof CountryCodeModel))
			return false;
		
		CountryCodeModel other = (CountryCodeModel) obj;
		if (getAlpha2Code() == null) {
			if (other.getAlpha2Code() != null)
				return false;
		} else if (!getAlpha2Code().equals(other.getAlpha2Code()))
			return false;
		if (getAlpha3Code() == null) {
			if (other.getAlpha3Code() != null)
				return false;
		} else if (!getAlpha3Code().equals(other.getAlpha3Code()))
			return false;
		if (getNumericCode() == null) {
			if (other.getNumericCode() != null)
				return false;
		} else if (!getNumericCode().equals(other.getNumericCode()))
			return false;
		return true;
	}
	
}
