package org.sistcoop.iso3166.models.mongo.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@NamedQueries({ 
	@NamedQuery(name = CountryCodeEntity.findAll, query = "SELECT c FROM CountryCodeEntity c"), 
	@NamedQuery(name = CountryCodeEntity.findByAlpha2Code, query = "SELECT c FROM CountryCodeEntity c WHERE c.alpha2Code = :alpha2Code"),
	@NamedQuery(name = CountryCodeEntity.findByAlpha3Code, query = "SELECT c FROM CountryCodeEntity c WHERE c.alpha3Code = :alpha3Code"),
	@NamedQuery(name = CountryCodeEntity.findByNumericCode, query = "SELECT c FROM CountryCodeEntity c WHERE c.numericCode = :numericCode"),
	@NamedQuery(name = CountryCodeEntity.findByFilterText, query = "SELECT c FROM CountryCodeEntity c WHERE c.alpha2Code LIKE :filterText OR c.alpha3Code LIKE :filterText OR c.numericCode LIKE :filterText OR c.shortNameEn LIKE :filterText OR c.shortNameUppercaseEn LIKE :filterText OR c.fullNameEn LIKE :filterText"),	
	@NamedQuery(name = CountryCodeEntity.count, query = "select count(u) from CountryCodeEntity u") })
public class CountryCodeEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public final static String base = "org.sistcoop.iso3166.models.mongo.entities.CountryCodeEntity.";
	public final static String findAll = base + "findAll";
	public final static String findByAlpha2Code = base + "findByAlpha2Code";
	public final static String findByAlpha3Code = base + "findByAlpha3Code";
	public final static String findByNumericCode = base + "findByNumericCode";
	public final static String findByFilterText = base + "findByFilterText";
	public final static String count = base + "count";

	private Integer id;
	private String alpha2Code;
	private String alpha3Code;
	private String numericCode;

	private boolean independent;
	private boolean status;

	private String shortNameEn;
	private String shortNameUppercaseEn;
	private String fullNameEn;

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
	@Size(min = 2, max = 2)
	public String getAlpha2Code() {
		return alpha2Code;
	}

	public void setAlpha2Code(String alpha2Code) {
		this.alpha2Code = alpha2Code;
	}

	@NotNull
	@NotBlank
	@Size(min = 3, max = 3)
	public String getAlpha3Code() {
		return alpha3Code;
	}

	public void setAlpha3Code(String alpha3Code) {
		this.alpha3Code = alpha3Code;
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
	@Type(type = "org.hibernate.type.TrueFalseType")
	public boolean isIndependent() {
		return independent;
	}

	public void setIndependent(boolean independent) {
		this.independent = independent;
	}

	@NotNull
	@Type(type = "org.hibernate.type.TrueFalseType")
	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	@NotNull
	@NotBlank
	@Size(min = 1, max = 200)
	public String getShortNameEn() {
		return shortNameEn;
	}

	public void setShortNameEn(String shortNameEn) {
		this.shortNameEn = shortNameEn;
	}

	@NotNull
	@NotBlank
	@Size(min = 1, max = 200)
	public String getShortNameUppercaseEn() {
		return shortNameUppercaseEn;
	}

	public void setShortNameUppercaseEn(String shortNameUppercaseEn) {
		this.shortNameUppercaseEn = shortNameUppercaseEn;
	}

	@NotNull
	@NotBlank
	@Size(min = 1, max = 200)
	public String getFullNameEn() {
		return fullNameEn;
	}

	public void setFullNameEn(String fullNameEn) {
		this.fullNameEn = fullNameEn;
	}

}
