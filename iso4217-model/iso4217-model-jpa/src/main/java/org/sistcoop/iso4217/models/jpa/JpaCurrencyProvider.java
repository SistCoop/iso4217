package org.sistcoop.iso4217.models.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.sistcoop.iso4217.models.CurrencyModel;
import org.sistcoop.iso4217.models.CurrencyProvider;
import org.sistcoop.iso4217.models.jpa.entities.CurrencyEntity;
import org.sistcoop.iso4217.models.search.SearchCriteriaModel;
import org.sistcoop.iso4217.models.search.SearchResultsModel;
import org.sistcoop.iso4217.models.search.filters.CurrencyFilterProvider;

@Named
@Stateless
@Local(CurrencyProvider.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class JpaCurrencyProvider extends AbstractHibernateStorage implements CurrencyProvider {

    @PersistenceContext
    protected EntityManager em;

    @Inject
    private CurrencyFilterProvider filterProvider;

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    @Override
    public void close() {
        // TODO Auto-generated method stub
    }

    @Override
    public CurrencyModel create(String entity, String currency, String alphabeticCode, String numericCode,
            int minorUnit) {
        CurrencyEntity currencyEntity = new CurrencyEntity();
        currencyEntity.setEntity(entity);
        currencyEntity.setCurrency(currency);
        currencyEntity.setAlphabeticCode(alphabeticCode);
        currencyEntity.setNumericCode(numericCode);
        currencyEntity.setMinorUnit(minorUnit);
        em.persist(currencyEntity);
        return new CurrencyAdapter(em, currencyEntity);
    }

    @Override
    public boolean remove(CurrencyModel currencyModel) {
        CurrencyEntity countryCodeEntity = em.find(CurrencyEntity.class, currencyModel.getId());
        if (countryCodeEntity == null) {
            return false;
        }
        em.remove(countryCodeEntity);
        return true;
    }

    @Override
    public CurrencyModel findById(String id) {
        CurrencyEntity currencyEntity = this.em.find(CurrencyEntity.class, id);
        return currencyEntity != null ? new CurrencyAdapter(em, currencyEntity) : null;
    }

    @Override
    public CurrencyModel findByAlphabeticCode(String alphabeticCode) {
        TypedQuery<CurrencyEntity> query = em.createNamedQuery("CurrencyEntity.findByAlphabeticCode",
                CurrencyEntity.class);
        query.setParameter("alphabeticCode", alphabeticCode);
        List<CurrencyEntity> results = query.getResultList();
        if (results.size() == 0)
            return null;
        return new CurrencyAdapter(em, results.get(0));
    }

    @Override
    public CurrencyModel findByNumericCode(String numericCode) {
        TypedQuery<CurrencyEntity> query = em.createNamedQuery("CurrencyEntity.findByNumericCode",
                CurrencyEntity.class);
        query.setParameter("numericCode", numericCode);
        List<CurrencyEntity> results = query.getResultList();
        if (results.size() == 0)
            return null;
        return new CurrencyAdapter(em, results.get(0));
    }

    @Override
    public SearchResultsModel<CurrencyModel> search() {
        TypedQuery<CurrencyEntity> query = em
                .createNamedQuery("CurrencyEntity.findAll", CurrencyEntity.class);

        List<CurrencyEntity> entities = query.getResultList();
        List<CurrencyModel> models = new ArrayList<CurrencyModel>();
        for (CurrencyEntity countryCodeEntity : entities) {
            models.add(new CurrencyAdapter(em, countryCodeEntity));
        }

        SearchResultsModel<CurrencyModel> result = new SearchResultsModel<>();
        result.setModels(models);
        result.setTotalSize(models.size());
        return result;
    }

    @Override
    public SearchResultsModel<CurrencyModel> search(SearchCriteriaModel criteria) {
        SearchResultsModel<CurrencyEntity> entityResult = find(criteria, CurrencyEntity.class);

        SearchResultsModel<CurrencyModel> modelResult = new SearchResultsModel<>();
        List<CurrencyModel> list = new ArrayList<>();
        for (CurrencyEntity entity : entityResult.getModels()) {
            list.add(new CurrencyAdapter(em, entity));
        }
        modelResult.setTotalSize(entityResult.getTotalSize());
        modelResult.setModels(list);
        return modelResult;
    }

    @Override
    public SearchResultsModel<CurrencyModel> search(SearchCriteriaModel criteria, String filterText) {
        SearchResultsModel<CurrencyEntity> entityResult = findFullText(criteria, CurrencyEntity.class, filterText,
                filterProvider.getAlphabeticCodeFilter(), filterProvider.getNumericCodeFilter(),
                filterProvider.getEntityFilter(), filterProvider.getCurrencyFilter());

        SearchResultsModel<CurrencyModel> modelResult = new SearchResultsModel<>();
        List<CurrencyModel> list = new ArrayList<>();
        for (CurrencyEntity entity : entityResult.getModels()) {
            list.add(new CurrencyAdapter(em, entity));
        }
        modelResult.setTotalSize(entityResult.getTotalSize());
        modelResult.setModels(list);
        return modelResult;
    }

}
