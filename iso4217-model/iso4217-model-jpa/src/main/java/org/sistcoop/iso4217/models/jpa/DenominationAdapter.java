package org.sistcoop.iso4217.models.jpa;

import java.math.BigDecimal;

import javax.persistence.EntityManager;

import org.sistcoop.iso4217.models.CurrencyModel;
import org.sistcoop.iso4217.models.DenominationModel;
import org.sistcoop.iso4217.models.jpa.entities.DenominationEntity;

public class DenominationAdapter implements DenominationModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected DenominationEntity denominationEntity;
	protected EntityManager em;

	public DenominationAdapter(EntityManager em, DenominationEntity denominationEntity) {
		this.em = em;
		this.denominationEntity = denominationEntity;
	}

	public DenominationEntity getDenominationEntity() {
		return this.denominationEntity;
	}

	public static DenominationEntity toDenominationEntity(DenominationModel model, EntityManager em) {
		if (model instanceof DenominationAdapter) {
			return ((DenominationAdapter) model).getDenominationEntity();
		}
		return em.getReference(DenominationEntity.class, model.getId());
	}

	@Override
	public void commit() {
		em.merge(denominationEntity);
	}

	@Override
	public String getId() {
		return denominationEntity.getId();
	}

	@Override
	public CurrencyModel getCurrency() {
		return new CurrencyAdapter(em, denominationEntity.getCurrency());
	}

	@Override
	public BigDecimal getValue() {
		return denominationEntity.getValue();
	}

	@Override
	public void setValue(BigDecimal value) {
		denominationEntity.setValue(value);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getCurrency() == null) ? 0 : getCurrency().hashCode());
		result = prime * result + ((getValue() == null) ? 0 : getValue().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof DenominationModel))
			return false;
		DenominationModel other = (DenominationModel) obj;
		if (getCurrency() == null) {
			if (other.getCurrency() != null)
				return false;
		} else if (!getCurrency().equals(other.getCurrency()))
			return false;
		if (getValue() == null) {
			if (other.getValue() != null)
				return false;
		} else if (!getValue().equals(other.getValue()))
			return false;
		return true;
	}

}
