package org.sistcoop.iso3166.models.utils;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.sistcoop.iso3166.models.CountryCodeModel;
import org.sistcoop.iso3166.models.CountryCodeProvider;
import org.sistcoop.iso3166.representations.idm.CountryCodeRepresentation;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class RepresentationToModel {	
		
	public CountryCodeModel createCountryCode(
			CountryCodeRepresentation rep, 		
			CountryCodeProvider countryCodeProvider) {		

		CountryCodeModel model = countryCodeProvider.addCountryCode(
				rep.getAlpha2Code(), 
				rep.getAlpha3Code(), 
				rep.getNumericCode(), 
				rep.getIndependent(), 
				rep.getStatus(), 
				rep.getShortNameEn(),
				rep.getShortNameUppercaseEn(),
				rep.getFullNameEn());		
		
		return model;
	}	

}
