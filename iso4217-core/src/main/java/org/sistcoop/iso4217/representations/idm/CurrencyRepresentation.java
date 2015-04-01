package org.sistcoop.iso4217.representations.idm;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "currency")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class CurrencyRepresentation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String entity;
	private String currency;
	private String alphabeticCode;
	private String numericCode;
	private int minorUnit;

	@XmlAttribute
	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	@XmlAttribute
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@XmlAttribute
	public String getAlphabeticCode() {
		return alphabeticCode;
	}

	public void setAlphabeticCode(String alphabeticCode) {
		this.alphabeticCode = alphabeticCode;
	}

	@XmlAttribute
	public String getNumericCode() {
		return numericCode;
	}

	public void setNumericCode(String numericCode) {
		this.numericCode = numericCode;
	}

	@XmlAttribute
	public int getMinorUnit() {
		return minorUnit;
	}

	public void setMinorUnit(int minorUnit) {
		this.minorUnit = minorUnit;
	}

}
