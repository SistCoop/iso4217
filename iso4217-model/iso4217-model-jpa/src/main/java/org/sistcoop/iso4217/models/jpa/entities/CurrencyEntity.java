package org.sistcoop.iso4217.models.jpa.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "CURRENCY")
@NamedQueries({ 
	@NamedQuery(name = CurrencyEntity.findAll, query = "SELECT c FROM CurrencyEntity c"), 
	@NamedQuery(name = CurrencyEntity.findByAlphabeticCode, query = "SELECT c FROM CurrencyEntity c WHERE c.alphabeticCode = :alphabeticCode"), @NamedQuery(name = CurrencyEntity.findByNumericCode, query = "SELECT c FROM CurrencyEntity c WHERE c.numericCode = :numericCode"),		
	@NamedQuery(name = CurrencyEntity.findByFilterText, query = "SELECT c FROM CurrencyEntity c WHERE c.alphabeticCode LIKE :filterText OR c.numericCode LIKE :filterText OR c.entity LIKE :filterText OR c.currency LIKE :filterText"), 
	@NamedQuery(name = CurrencyEntity.count, query = "select count(u) from CurrencyEntity u") })
public class CurrencyEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public final static String base = "org.sistcoop.iso4217.models.jpa.entities.CurrencyEntity.";
	public final static String findAll = base + "findAll";
	public final static String findByAlphabeticCode = base + "findByAlphabeticCode";
	public final static String findByNumericCode = base + "findByNumericCode";
	public final static String findByFilterText = base + "findByFilterText";
	public final static String count = base + "count";

	private Integer id;
	private String entity;
	private String currency;
	private String alphabeticCode;
	private String numericCode;
	private int minorUnit;

	public CurrencyEntity() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(generator = "SgGenericGenerator")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@NotNull
	@NotBlank
	@Size(min = 1, max = 200)
	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	@NotNull
	@NotBlank
	@Size(min = 1, max = 200)
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@NotNull
	@NotBlank
	@Size(min = 3, max = 3)
	public String getAlphabeticCode() {
		return alphabeticCode;
	}

	public void setAlphabeticCode(String alphabeticCode) {
		this.alphabeticCode = alphabeticCode;
	}

	@NotNull
	@NotBlank
	@Size(min = 3, max = 3)
	public String getNumericCode() {
		return numericCode;
	}

	public void setNumericCode(String numericCode) {
		this.numericCode = numericCode;
	}

	@NotNull
	@Min(value = 1)
	public int getMinorUnit() {
		return minorUnit;
	}

	public void setMinorUnit(int minorUnit) {
		this.minorUnit = minorUnit;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((alphabeticCode == null) ? 0 : alphabeticCode.hashCode());
		result = prime * result + ((numericCode == null) ? 0 : numericCode.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof CurrencyEntity))
			return false;
		CurrencyEntity other = (CurrencyEntity) obj;
		if (alphabeticCode == null) {
			if (other.alphabeticCode != null)
				return false;
		} else if (!alphabeticCode.equals(other.alphabeticCode))
			return false;
		if (numericCode == null) {
			if (other.numericCode != null)
				return false;
		} else if (!numericCode.equals(other.numericCode))
			return false;
		return true;
	}

}
