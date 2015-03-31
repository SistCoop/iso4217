package org.sistcoop.iso3166.services.resources.admin;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.sistcoop.iso3166.admin.client.resource.CountryCodesResource;
import org.sistcoop.iso3166.models.CountryCodeModel;
import org.sistcoop.iso3166.models.CountryCodeProvider;
import org.sistcoop.iso3166.models.utils.ModelToRepresentation;
import org.sistcoop.iso3166.models.utils.RepresentationToModel;
import org.sistcoop.iso3166.representations.idm.CountryCodeRepresentation;

@Stateless
public class CountryCodesResourceImpl implements CountryCodesResource {

	@Inject
	private CountryCodeProvider countryCodeProvider;

	@Inject
	private RepresentationToModel representationToModel;
	
	@Context
	private UriInfo uriInfo;
	
	@Override
	public CountryCodeRepresentation findByAlpha2Code(String alpha2Code) {
		CountryCodeModel model = countryCodeProvider.getCountryCodeByAlpha2Code(alpha2Code);		
		return ModelToRepresentation.toRepresentation(model);
	}

	@Override
	public CountryCodeRepresentation findByAlpha3Code(String alpha3Code) {
		CountryCodeModel model = countryCodeProvider.getCountryCodeByAlpha3Code(alpha3Code);		
		return ModelToRepresentation.toRepresentation(model);
	}

	@Override
	public CountryCodeRepresentation findByNumericCode(String numericCode) {
		CountryCodeModel model = countryCodeProvider.getCountryCodeByNumericCode(numericCode);		
		return ModelToRepresentation.toRepresentation(model);
	}

	@Override
	public Response create(CountryCodeRepresentation countryCodeRepresentation) {
		CountryCodeModel model = representationToModel.createCountryCode(countryCodeRepresentation, countryCodeProvider);
		return Response.created(uriInfo.getAbsolutePathBuilder().path(model.getId().toString()).build()).header("Access-Control-Expose-Headers", "Location").entity(model.getId()).build();
	}

	@Override
	public void updateByAlpha2Code(String alpha2Code, CountryCodeRepresentation countryCodeRepresentation) {
		CountryCodeModel model = countryCodeProvider.getCountryCodeByAlpha2Code(alpha2Code);
		model.setIndependent(countryCodeRepresentation.getIndependent());
		model.setStatus(countryCodeRepresentation.getStatus());
		model.setShortNameEn(countryCodeRepresentation.getShortNameEn());
		model.setShortNameUppercaseEn(countryCodeRepresentation.getShortNameUppercaseEn());
		model.setFullNameEn(countryCodeRepresentation.getFullNameEn());
		
		model.commit();		
	}

	@Override
	public void updateByAlpha3Code(String alpha3Code, CountryCodeRepresentation countryCodeRepresentation) {
		CountryCodeModel model = countryCodeProvider.getCountryCodeByAlpha3Code(alpha3Code);
		model.setIndependent(countryCodeRepresentation.getIndependent());
		model.setStatus(countryCodeRepresentation.getStatus());
		model.setShortNameEn(countryCodeRepresentation.getShortNameEn());
		model.setShortNameUppercaseEn(countryCodeRepresentation.getShortNameUppercaseEn());
		model.setFullNameEn(countryCodeRepresentation.getFullNameEn());
		
		model.commit();
	}

	@Override
	public void updateByNumericCode(String numericCode, CountryCodeRepresentation countryCodeRepresentation) {
		CountryCodeModel model = countryCodeProvider.getCountryCodeByNumericCode(numericCode);
		model.setIndependent(countryCodeRepresentation.getIndependent());
		model.setStatus(countryCodeRepresentation.getStatus());
		model.setShortNameEn(countryCodeRepresentation.getShortNameEn());
		model.setShortNameUppercaseEn(countryCodeRepresentation.getShortNameUppercaseEn());
		model.setFullNameEn(countryCodeRepresentation.getFullNameEn());
		
		model.commit();
	}

	@Override
	public void removeByAlpha2Code(String alpha2Code) {
		CountryCodeModel model = countryCodeProvider.getCountryCodeByAlpha2Code(alpha2Code);
		boolean result = countryCodeProvider.removeCountryCode(model);
		if(!result)
			throw new InternalServerErrorException();
	}

	@Override
	public void removeByAlpha3Code(String alpha3Code) {
		CountryCodeModel model = countryCodeProvider.getCountryCodeByAlpha3Code(alpha3Code);
		boolean result = countryCodeProvider.removeCountryCode(model);
		if(!result)
			throw new InternalServerErrorException();
	}

	@Override
	public void removeByNumericCode(String numericCode) {
		CountryCodeModel model = countryCodeProvider.getCountryCodeByNumericCode(numericCode);
		boolean result = countryCodeProvider.removeCountryCode(model);
		if(!result)
			throw new InternalServerErrorException();
	}

	@Override
	public List<CountryCodeRepresentation> listAll(String filterText, Integer firstResult, Integer maxResults) {
		List<CountryCodeRepresentation> results = new ArrayList<CountryCodeRepresentation>();
		List<CountryCodeModel> countryModels;
		if (filterText == null) {
			if (firstResult == null || maxResults == null) {
				countryModels = countryCodeProvider.getCountryCodes();
			} else {
				countryModels = countryCodeProvider.getCountryCodes(firstResult, maxResults);
			}
		} else {
			if (firstResult == null || maxResults == null) {
				countryModels = countryCodeProvider.getCountryCodes(filterText);
			} else {
				countryModels = countryCodeProvider.getCountryCodes(filterText, firstResult, maxResults);
			}
		}
		for (CountryCodeModel personaNaturalModel : countryModels) {
			results.add(ModelToRepresentation.toRepresentation(personaNaturalModel));
		}
		return results;
	}

	@Override
	public int countAll() {
		int count = countryCodeProvider.getCountryCodesCount();
		return count;
	}

}
