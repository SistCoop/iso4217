package org.sistcoop.iso4217.models.jpa;

import java.util.List;

import javax.persistence.EntityManager;

import org.apache.lucene.search.Query;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.sistcoop.iso4217.models.search.OrderByModel;
import org.sistcoop.iso4217.models.search.PagingModel;
import org.sistcoop.iso4217.models.search.SearchCriteriaFilterModel;
import org.sistcoop.iso4217.models.search.SearchCriteriaFilterOperator;
import org.sistcoop.iso4217.models.search.SearchCriteriaModel;
import org.sistcoop.iso4217.models.search.SearchResultsModel;

public abstract class AbstractJpaStorage {

    public AbstractJpaStorage() {

    }

    protected abstract EntityManager getEntityManager();

    protected Session getSession() {
        return getEntityManager().unwrap(Session.class);
    }

    protected <T> SearchResultsModel<T> find(SearchCriteriaModel searchCriteriaModel, Class<T> type) {
        SearchResultsModel<T> results = new SearchResultsModel<>();
        Session session = getSession();

        Criteria criteria = session.createCriteria(type);
        applySearchCriteriaToQuery(searchCriteriaModel, criteria, false);

        // Set paging
        PagingModel paging = searchCriteriaModel.getPaging();
        // int page = 0;
        // int pageSize = 0;
        // int start = 0;
        // boolean hasMore = false;
        if (paging != null) {
            int page = paging.getPage();
            int pageSize = paging.getPageSize();
            int start = (page - 1) * pageSize;

            criteria.setFirstResult(start);
            criteria.setMaxResults(pageSize + 1);
        }

        // Now query for the actual results
        @SuppressWarnings("unchecked")
        List<T> resultList = criteria.list();

        // Check if we got back more than we actually needed.
        // if (resultList.size() > pageSize) {
        // resultList.remove(resultList.size() - 1);
        // hasMore = true;
        // }

        // If there are more results than we needed, then we will need to do
        // another
        // query to determine how many rows there are in total
        // long totalSize = start + resultList.size();
        // if (hasMore) {
        int totalSize = executeCountQuery(searchCriteriaModel, session, type);
        // }
        results.setTotalSize(totalSize);
        results.setModels(resultList);
        return results;
    }

    protected <T> SearchResultsModel<T> find(SearchCriteriaModel searchCriteriaModel, Class<T> type,
            String filterText, String... field) {

        SearchResultsModel<T> results = new SearchResultsModel<>();
        EntityManager entityManager = getEntityManager();
        Session session = getSession();

        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
        QueryBuilder qb = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(type).get();
        Query query = qb.keyword().onFields(field).matching(filterText).createQuery();
        FullTextQuery fullTextQuery = fullTextEntityManager.createFullTextQuery(query, type);

        Criteria criteria = session.createCriteria(type);
        applySearchCriteriaToQuery(searchCriteriaModel, criteria, false);
        fullTextQuery.setCriteriaQuery(criteria);

        // Set paging
        PagingModel paging = searchCriteriaModel.getPaging();
        int page = 0;
        int pageSize = 0;
        int start = 0;
        boolean hasMore = false;
        if (paging != null) {
            page = paging.getPage();
            pageSize = paging.getPageSize();
            start = (page - 1) * pageSize;

            fullTextQuery.setFirstResult(start);
            fullTextQuery.setMaxResults(pageSize + 1);
        }

        // Now query for the actual results
        @SuppressWarnings("unchecked")
        List<T> resultList = fullTextQuery.getResultList();

        // Check if we got back more than we actually needed.
        if (resultList.size() > pageSize) {
            resultList.remove(resultList.size() - 1);
            hasMore = true;
        }

        // If there are more results than we needed, then we will need to do
        // another
        // query to determine how many rows there are in total
        int totalSize = start + resultList.size();
        if (hasMore) {
            totalSize = fullTextQuery.getResultSize();
        }
        results.setTotalSize(totalSize);
        results.setModels(resultList);

        return results;
    }

    protected <T> int executeCountQuery(SearchCriteriaModel searchCriteriaModel, Session session,
            Class<T> type) {
        Criteria criteria = session.createCriteria(type);
        applySearchCriteriaToQuery(searchCriteriaModel, criteria, true);
        criteria.setProjection(Projections.rowCount());
        return ((Long) criteria.uniqueResult()).intValue();
    }

    protected <T> void applySearchCriteriaToQuery(SearchCriteriaModel searchCriteriaModel, Criteria criteria,
            boolean countOnly) {

        List<SearchCriteriaFilterModel> filters = searchCriteriaModel.getFilters();
        if (filters != null && !filters.isEmpty()) {
            for (SearchCriteriaFilterModel filter : filters) {
                if (filter.getOperator() == SearchCriteriaFilterOperator.eq) {
                    criteria.add(Restrictions.eq(filter.getName(), filter.getValue()));
                } else if (filter.getOperator() == SearchCriteriaFilterOperator.bool_eq) {
                    criteria.add(Restrictions.eq(filter.getName(), filter.getValue()));
                } else if (filter.getOperator() == SearchCriteriaFilterOperator.gt) {
                    criteria.add(Restrictions.gt(filter.getName(), filter.getValue()));
                } else if (filter.getOperator() == SearchCriteriaFilterOperator.gte) {
                    criteria.add(Restrictions.ge(filter.getName(), filter.getValue()));
                } else if (filter.getOperator() == SearchCriteriaFilterOperator.lt) {
                    criteria.add(Restrictions.lt(filter.getName(), filter.getValue()));
                } else if (filter.getOperator() == SearchCriteriaFilterOperator.lte) {
                    criteria.add(Restrictions.le(filter.getName(), filter.getValue()));
                } else if (filter.getOperator() == SearchCriteriaFilterOperator.neq) {
                    criteria.add(Restrictions.ne(filter.getName(), filter.getValue()));
                } else if (filter.getOperator() == SearchCriteriaFilterOperator.like) {
                    criteria.add(Restrictions.like(filter.getName(), (String) filter.getValue(),
                            MatchMode.ANYWHERE));
                }
            }
        }

        List<OrderByModel> orders = searchCriteriaModel.getOrders();
        if (orders != null && !orders.isEmpty() && !countOnly) {
            for (OrderByModel orderBy : orders) {
                if (orderBy.isAscending()) {
                    criteria.addOrder(Order.asc(orderBy.getName()));
                } else {
                    criteria.addOrder(Order.desc(orderBy.getName()));
                }
            }
        }
    }

}