package com.etnetera.hr.repository;

import com.etnetera.hr.data.JavaScriptFramework;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of custom repository
 *
 * @author hlavja
 *
 */
public class JavaScriptFrameworkRepositoryImpl implements JavaScriptFrameworkRepositoryInterface {

    @PersistenceContext
    private EntityManager entityManager;

    public Iterable<JavaScriptFramework> searchJavaScriptFrameworkByCriteria(Long id, String name, String version, Integer hypeLevel,
                                                                             LocalDate deprecationDateGt, LocalDate deprecationDateLt) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<JavaScriptFramework> criteriaQuery = criteriaBuilder.createQuery(JavaScriptFramework.class);
        Root<JavaScriptFramework> javaScriptFrameworkRoot = criteriaQuery.from(JavaScriptFramework.class);
        List<Predicate> predicateList = new ArrayList<>();

        if (id != null) {
            predicateList.add(criteriaBuilder.equal(javaScriptFrameworkRoot.get("id"), id));
        } else {
            if (name != null) predicateList.add(criteriaBuilder.equal(javaScriptFrameworkRoot.get("name"), name));

            if (version != null) predicateList.add(criteriaBuilder.equal(javaScriptFrameworkRoot.get("version"), version));

            if (hypeLevel != null) predicateList.add(criteriaBuilder.equal(javaScriptFrameworkRoot.get("hypeLevel"), hypeLevel));

            if (deprecationDateGt != null) predicateList.add(criteriaBuilder.greaterThan(javaScriptFrameworkRoot.get("deprecationDate"), deprecationDateGt));

            if (deprecationDateLt != null) predicateList.add(criteriaBuilder.lessThan(javaScriptFrameworkRoot.get("deprecationDate"), deprecationDateLt));
        }


        criteriaQuery.where(predicateList.toArray(new Predicate[0]));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
