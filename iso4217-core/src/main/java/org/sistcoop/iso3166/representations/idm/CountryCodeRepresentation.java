package org.sistcoop.iso3166.representations.idm;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "countryCode")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class CountryCodeRepresentation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String alpha2Code;
	private String alpha3Code;
	private String numericCode;

	private Boolean independent;
	private Boolean status;

	private String shortNameEn;
	private String shortNameUppercaseEn;
	private String fullNameEn;

	@XmlAttribute
	public String getAlpha2Code() {
		return alpha2Code;
	}

	public void setAlpha2Code(String alpha2Code) {
		this.alpha2Code = alpha2Code;
	}

	@XmlAttribute
	public String getAlpha3Code() {
		return alpha3Code;
	}

	public void setAlpha3Code(String alpha3Code) {
		this.alpha3Code = alpha3Code;
	}

	@XmlAttribute
	public String getNumericCode() {
		return numericCode;
	}

	public void setNumericCode(String numericCode) {
		this.numericCode = numericCode;
	}

	@XmlAttribute
	public Boolean getIndependent() {
		return independent;
	}

	public void setIndependent(Boolean independent) {
		this.independent = independent;
	}

	@XmlAttribute
	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	@XmlAttribute
	public String getShortNameEn() {
		return shortNameEn;
	}

	public void setShortNameEn(String shortNameEn) {
		this.shortNameEn = shortNameEn;
	}

	@XmlAttribute
	public String getShortNameUppercaseEn() {
		return shortNameUppercaseEn;
	}

	public void setShortNameUppercaseEn(String shortNameUppercaseEn) {
		this.shortNameUppercaseEn = shortNameUppercaseEn;
	}

	@XmlAttribute
	public String getFullNameEn() {
		return fullNameEn;
	}

	public void setFullNameEn(String fullNameEn) {
		this.fullNameEn = fullNameEn;
	}

}
