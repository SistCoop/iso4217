package org.sistcoop.iso4217.representations.idm;

import java.io.Serializable;

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

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAlphabeticCode() {
        return alphabeticCode;
    }

    public void setAlphabeticCode(String alphabeticCode) {
        this.alphabeticCode = alphabeticCode;
    }

    public String getNumericCode() {
        return numericCode;
    }

    public void setNumericCode(String numericCode) {
        this.numericCode = numericCode;
    }

    public int getMinorUnit() {
        return minorUnit;
    }

    public void setMinorUnit(int minorUnit) {
        this.minorUnit = minorUnit;
    }

}
