package org.sistcoop.iso4217.models.utils;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.sistcoop.iso4217.models.CurrencyModel;
import org.sistcoop.iso4217.models.CurrencyProvider;
import org.sistcoop.iso4217.representations.idm.CurrencyRepresentation;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class RepresentationToModel {	
		
	public CurrencyModel createCurrency(
			CurrencyRepresentation rep, 		
			CurrencyProvider currencyProvider) {		

		CurrencyModel model = currencyProvider.addCurrency(
				rep.getEntity(),
				rep.getCurrency(),
				rep.getAlphabeticCode(),
				rep.getNumericCode(), 
				rep.getMinorUnit());		
		
		return model;
	}	

}
