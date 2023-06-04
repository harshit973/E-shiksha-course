package com.example.course.Repository;

import com.example.course.Entity.Course;
import com.example.course.Entity.Page.CoursePage;
import com.example.course.Entity.SearchCriteria.CourseSearchCriteria;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

@Repository
public class CourseCriteriaRepository {
    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;
    public CourseCriteriaRepository(EntityManager entityManager){
        this.entityManager=entityManager;
        this.criteriaBuilder= entityManager.getCriteriaBuilder();
    }
    public Page<Course> filter(CoursePage coursePage, CourseSearchCriteria courseSearchCriteria){
        CriteriaQuery<Course> criteriaQuery=criteriaBuilder.createQuery(Course.class);
        Root<Course> courseRoot=criteriaQuery.from(Course.class);
        Predicate predicate=getPredicate(courseSearchCriteria,courseRoot);
        criteriaQuery.where(predicate);
        sortCourse(coursePage,criteriaQuery,courseRoot);
        TypedQuery<Course> typedQuery=entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(coursePage.getPageNumber()*coursePage.getPageSize());
        typedQuery.setMaxResults(coursePage.getPageSize());
        Pageable pageable=getPageable(coursePage);
        long count=getCourseCount(predicate);
        return new PageImpl<>(typedQuery.getResultList(),pageable,count);
    }
    public long getCourseCount(Predicate predicate){
        CriteriaQuery<Long> countQuery= criteriaBuilder.createQuery(Long.class);
        Root<Course> courseRoot =countQuery.from(Course.class);
        countQuery.select(criteriaBuilder.count(courseRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }
    private Pageable getPageable(CoursePage coursePage) {
        return PageRequest.of(coursePage.getPageNumber(), coursePage.getPageSize(), Sort.by(coursePage.getSortDirection(), coursePage.getSortBy()));
    }

    private void sortCourse(CoursePage coursePage,CriteriaQuery<Course> courseCriteriaQuery,Root<Course> courseRoot){
        if(coursePage.getSortDirection().isAscending()){
            courseCriteriaQuery.orderBy(criteriaBuilder.asc(courseRoot.get(coursePage.getSortBy())));
        }else{
            courseCriteriaQuery.orderBy(criteriaBuilder.desc(courseRoot.get(coursePage.getSortBy())));
        }
    }
    private Predicate getPredicate(CourseSearchCriteria courseSearchCriteria,Root<Course> courseRoot){
        List<Predicate> predicates=new ArrayList<>();
        if(Objects.nonNull(courseSearchCriteria.getName())){
            predicates.add(criteriaBuilder.like(courseRoot.get("name"),"%"+courseSearchCriteria.getName()+"%"));
        }
        if(Objects.nonNull(courseSearchCriteria.getEducator())){
            predicates.add(criteriaBuilder.equal(courseRoot.get("educator"),courseSearchCriteria.getEducator()));
        }
        predicates.add(criteriaBuilder.equal(courseRoot.get("deleted"),false));
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
