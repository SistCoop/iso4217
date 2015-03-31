package org.sistcoop.iso3166.models.utils;

import org.sistcoop.iso3166.models.CountryCodeModel;
import org.sistcoop.iso3166.representations.idm.CountryCodeRepresentation;

public class ModelToRepresentation {

	public static CountryCodeRepresentation toRepresentation(CountryCodeModel model) {
		if (model == null)
			return null;
		CountryCodeRepresentation rep = new CountryCodeRepresentation();

		rep.setAlpha2Code(model.getAlpha2Code());
		rep.setAlpha3Code(model.getAlpha3Code());
		rep.setNumericCode(model.getNumericCode());
		rep.setIndependent(model.isIndependent());
		rep.setStatus(model.isStatus());
		rep.setShortNameEn(model.getShortNameEn());
		rep.setShortNameUppercaseEn(model.getShortNameUppercaseEn());
		rep.setFullNameEn(model.getFullNameEn());

		return rep;
	}	

}
