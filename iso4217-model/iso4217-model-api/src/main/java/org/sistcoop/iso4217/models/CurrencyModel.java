package org.sistcoop.iso4217.models;

public interface CurrencyModel extends Model {

	Integer getId();

	String getEntity();

	void setEntity(String entity);

	String getCurrency();

	void setCurrency(String currency);

	String getAlphabeticCode();

	String getNumericCode();

	int getMinorUnit();

	void setMinorUnit(int minorUnit);

}