package org.sistcoop.iso3166.models;

import java.util.List;

import javax.ejb.Local;

import org.sistcoop.iso3166.provider.Provider;

@Local
public interface CountryCodeProvider extends Provider {

	CountryCodeModel addCountryCode(
			String alpha2Code,
			String alpha3Code,
			String numericCode,
		    boolean independent,
		    boolean status,
		    String shortNameEn, 
		    String shortNameUppercaseEn, 
		    String fullNameEn);
		
	boolean removeCountryCode(CountryCodeModel countryCodeModel);

	CountryCodeModel getCountryCodeByAlpha2Code(String  alpha2Code);
	
	CountryCodeModel getCountryCodeByAlpha3Code(String  alpha3Code);
	
	CountryCodeModel getCountryCodeByNumericCode(String  numericCode);
	
	int getCountryCodesCount();
	
	List<CountryCodeModel> getCountryCodes();	

	List<CountryCodeModel> getCountryCodes(String filterText);
	
	List<CountryCodeModel> getCountryCodes(int firstResult, int maxResults);		

	List<CountryCodeModel> getCountryCodes(String filterText, int firstResult, int maxResults);

}
