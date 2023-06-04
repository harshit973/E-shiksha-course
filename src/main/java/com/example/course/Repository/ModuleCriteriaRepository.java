package com.example.course.Repository;

import com.example.course.Entity.Module;
import com.example.course.Entity.Page.ModulePage;
import com.example.course.Entity.SearchCriteria.ModuleSearchCriteria;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class ModuleCriteriaRepository {
    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;
    public ModuleCriteriaRepository(EntityManager entityManager){
        this.entityManager=entityManager;
        this.criteriaBuilder= entityManager.getCriteriaBuilder();
    }
    public Page<Module> filter(ModulePage ModulePage, ModuleSearchCriteria ModuleSearchCriteria){
        CriteriaQuery<Module> criteriaQuery=criteriaBuilder.createQuery(Module.class);
        Root<Module> ModuleRoot=criteriaQuery.from(Module.class);
        Predicate predicate=getPredicate(ModuleSearchCriteria,ModuleRoot);
        criteriaQuery.where(predicate);
        sortModule(ModulePage,criteriaQuery,ModuleRoot);
        TypedQuery<Module> typedQuery=entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(ModulePage.getPageNumber()*ModulePage.getPageSize());
        typedQuery.setMaxResults(ModulePage.getPageSize());
        Pageable pageable=getPageable(ModulePage);
        long count=getModuleCount(predicate);
        return new PageImpl<>(typedQuery.getResultList(),pageable,count);
    }
    public long getModuleCount(Predicate predicate){
        CriteriaQuery<Long> countQuery= criteriaBuilder.createQuery(Long.class);
        Root<Module> ModuleRoot =countQuery.from(Module.class);
        countQuery.select(criteriaBuilder.count(ModuleRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }
    private Pageable getPageable(ModulePage modulePage) {
        return PageRequest.of(modulePage.getPageNumber(), modulePage.getPageSize(), Sort.by(modulePage.getSortDirection(), modulePage.getSortBy()));
    }

    private void sortModule(ModulePage modulePage,CriteriaQuery<Module> moduleCriteriaQuery,Root<Module> moduleRoot){
        if(modulePage.getSortDirection().isAscending()){
            moduleCriteriaQuery.orderBy(criteriaBuilder.asc(moduleRoot.get(modulePage.getSortBy())));
        }else{
            moduleCriteriaQuery.orderBy(criteriaBuilder.desc(moduleRoot.get(modulePage.getSortBy())));
        }
    }
    private Predicate getPredicate(ModuleSearchCriteria moduleSearchCriteria,Root<Module> moduleRoot){
        List<Predicate> predicates=new ArrayList<>();
        if(Objects.nonNull(moduleSearchCriteria.getName())){
            predicates.add(criteriaBuilder.like(moduleRoot.get("name"),"%"+moduleSearchCriteria.getName()+"%"));
        }
        if(Objects.nonNull(moduleSearchCriteria.getCourse())){
            predicates.add(criteriaBuilder.equal(moduleRoot.get("course"),moduleSearchCriteria.getCourse()));
        }
        predicates.add(criteriaBuilder.equal(moduleRoot.get("deleted"),false));
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
