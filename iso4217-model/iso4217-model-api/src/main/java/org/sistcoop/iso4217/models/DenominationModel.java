package org.sistcoop.iso4217.models;

import java.math.BigDecimal;

public interface DenominationModel extends Model {

	Integer getId();

	CurrencyModel getCurrency();

	BigDecimal getValue();

	void setValue(BigDecimal value);

}