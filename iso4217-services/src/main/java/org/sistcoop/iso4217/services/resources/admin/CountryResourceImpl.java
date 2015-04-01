package org.sistcoop.iso4217.services.resources.admin;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.sistcoop.iso4217.admin.client.resource.CurrencyResource;
import org.sistcoop.iso4217.models.CurrencyModel;
import org.sistcoop.iso4217.models.CurrencyProvider;
import org.sistcoop.iso4217.models.utils.ModelToRepresentation;
import org.sistcoop.iso4217.models.utils.RepresentationToModel;
import org.sistcoop.iso4217.representations.idm.CurrencyRepresentation;

@Stateless
public class CountryResourceImpl implements CurrencyResource {

	@Inject
	private CurrencyProvider currencyProvider;

	@Inject
	private RepresentationToModel representationToModel;

	@Context
	private UriInfo uriInfo;

	@Override
	public CurrencyRepresentation findByAlphabeticCode(String alphabeticCode) {
		CurrencyModel model = currencyProvider.getCurrencyByAlphabeticCode(alphabeticCode);
		return ModelToRepresentation.toRepresentation(model);
	}

	@Override
	public CurrencyRepresentation findByNumericCode(String numericCode) {
		CurrencyModel model = currencyProvider.getCurrencyByNumericCode(numericCode);
		return ModelToRepresentation.toRepresentation(model);
	}

	@Override
	public Response create(CurrencyRepresentation currencyRepresentation) {
		CurrencyModel model = representationToModel.createCurrency(currencyRepresentation, currencyProvider);
		return Response.created(uriInfo.getAbsolutePathBuilder().path(model.getId().toString()).build()).header("Access-Control-Expose-Headers", "Location").entity(model.getId()).build();
	}

	@Override
	public void updateByAlphabeticCode(String alphabeticCode, CurrencyRepresentation currencyRepresentation) {
		CurrencyModel model = currencyProvider.getCurrencyByAlphabeticCode(alphabeticCode);
		model.setEntity(currencyRepresentation.getEntity());
		model.setCurrency(currencyRepresentation.getCurrency());
		model.setMinorUnit(currencyRepresentation.getMinorUnit());

		model.commit();
	}

	@Override
	public void updateByNumericCode(String numericCode, CurrencyRepresentation currencyRepresentation) {
		CurrencyModel model = currencyProvider.getCurrencyByNumericCode(numericCode);
		model.setEntity(currencyRepresentation.getEntity());
		model.setCurrency(currencyRepresentation.getCurrency());
		model.setMinorUnit(currencyRepresentation.getMinorUnit());

		model.commit();
	}

	@Override
	public void removeByAlphabeticCode(String alphabeticCode) {
		CurrencyModel model = currencyProvider.getCurrencyByAlphabeticCode(alphabeticCode);
		boolean result = currencyProvider.removeCurrency(model);
		if (!result)
			throw new InternalServerErrorException();
	}

	@Override
	public void removeByNumericCode(String numericCode) {
		CurrencyModel model = currencyProvider.getCurrencyByNumericCode(numericCode);
		boolean result = currencyProvider.removeCurrency(model);
		if (!result)
			throw new InternalServerErrorException();
	}

	@Override
	public List<CurrencyRepresentation> listAll(String filterText, Integer firstResult, Integer maxResults) {
		List<CurrencyRepresentation> results = new ArrayList<CurrencyRepresentation>();
		List<CurrencyModel> countryModels;
		if (filterText == null) {
			if (firstResult == null || maxResults == null) {
				countryModels = currencyProvider.getCurrencies();
			} else {
				countryModels = currencyProvider.getCurrencies(firstResult, maxResults);
			}
		} else {
			if (firstResult == null || maxResults == null) {
				countryModels = currencyProvider.getCurrencies(filterText);
			} else {
				countryModels = currencyProvider.getCurrencies(filterText, firstResult, maxResults);
			}
		}
		for (CurrencyModel personaNaturalModel : countryModels) {
			results.add(ModelToRepresentation.toRepresentation(personaNaturalModel));
		}
		return results;
	}

	@Override
	public int countAll() {
		int count = currencyProvider.getCurrencyCount();
		return count;
	}

}
