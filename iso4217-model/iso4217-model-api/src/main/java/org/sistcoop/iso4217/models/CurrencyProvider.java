package org.sistcoop.iso4217.models;

import java.util.List;

import javax.ejb.Local;

import org.sistcoop.iso4217.provider.Provider;

@Local
public interface CurrencyProvider extends Provider {

	CurrencyModel addCurrency(
			String entity,
			String currency,
			String alphabeticCode,
			String numericCode,
		    int minorUnit);	
		
	boolean removeCurrency(CurrencyModel currencyModel);

	CurrencyModel getCurrencyByAlphabeticCode(String  alphabeticCode);	
	
	CurrencyModel getCurrencyByNumericCode(String  numericCode);
	
	int getCurrencyCount();
	
	List<CurrencyModel> getCurrencies();	

	List<CurrencyModel> getCurrencies(String filterText);
	
	List<CurrencyModel> getCurrencies(int firstResult, int maxResults);		

	List<CurrencyModel> getCurrencies(String filterText, int firstResult, int maxResults);

}
