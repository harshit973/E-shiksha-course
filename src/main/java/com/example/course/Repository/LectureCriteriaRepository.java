package com.example.course.Repository;

import com.example.course.Entity.Lecture;
import com.example.course.Entity.Page.LecturePage;
import com.example.course.Entity.SearchCriteria.LectureSearchCriteria;
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
public class LectureCriteriaRepository {
    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;
    public LectureCriteriaRepository(EntityManager entityManager){
        this.entityManager=entityManager;
        this.criteriaBuilder= entityManager.getCriteriaBuilder();
    }
    public Page<Lecture> filter(LecturePage coursePage, LectureSearchCriteria lectureSearchCriteria){
        CriteriaQuery<Lecture> criteriaQuery=criteriaBuilder.createQuery(Lecture.class);
        Root<Lecture> courseRoot=criteriaQuery.from(Lecture.class);
        Predicate predicate=getPredicate(lectureSearchCriteria,courseRoot);
        criteriaQuery.where(predicate);
        sortLecture(coursePage,criteriaQuery,courseRoot);
        TypedQuery<Lecture> typedQuery=entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(coursePage.getPageNumber()*coursePage.getPageSize());
        typedQuery.setMaxResults(coursePage.getPageSize());
        Pageable pageable=getPageable(coursePage);
        long count=getLectureCount(predicate);
        return new PageImpl<>(typedQuery.getResultList(),pageable,count);
    }
    public long getLectureCount(Predicate predicate){
        CriteriaQuery<Long> countQuery= criteriaBuilder.createQuery(Long.class);
        Root<Lecture> courseRoot =countQuery.from(Lecture.class);
        countQuery.select(criteriaBuilder.count(courseRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }
    private Pageable getPageable(LecturePage lecturePage) {
        return PageRequest.of(lecturePage.getPageNumber(), lecturePage.getPageSize(), Sort.by(lecturePage.getSortDirection(), lecturePage.getSortBy()));
    }

    private void sortLecture(LecturePage lecturePage,CriteriaQuery<Lecture> lectureCriteriaQuery,Root<Lecture> lectureRoot){
        if(lecturePage.getSortDirection().isAscending()){
            lectureCriteriaQuery.orderBy(criteriaBuilder.asc(lectureRoot.get(lecturePage.getSortBy())));
        }else{
            lectureCriteriaQuery.orderBy(criteriaBuilder.desc(lectureRoot.get(lecturePage.getSortBy())));
        }
    }
    private Predicate getPredicate(LectureSearchCriteria lectureSearchCriteria,Root<Lecture> lectureRoot){
        List<Predicate> predicates=new ArrayList<>();
        if(Objects.nonNull(lectureSearchCriteria.getName())){
            predicates.add(criteriaBuilder.like(lectureRoot.get("name"),"%"+lectureSearchCriteria.getName()+"%"));
        }
        if(Objects.nonNull(lectureSearchCriteria.getModule())){
            predicates.add(criteriaBuilder.equal(lectureRoot.get("module"),lectureSearchCriteria.getModule()));
        }
        predicates.add(criteriaBuilder.equal(lectureRoot.get("deleted"),false));
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
