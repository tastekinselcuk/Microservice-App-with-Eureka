package com.microservice.carDefectservice.domain;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;


public class CarSpecifications {

    public static Specification<Car> hasCarModel(String carModel) {
        return new Specification<Car>() {
            public Predicate toPredicate(Root<Car> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                return builder.equal(root.get("carModel"), carModel);
            }
        };
    }

}
