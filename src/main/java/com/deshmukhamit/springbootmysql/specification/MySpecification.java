package com.deshmukhamit.springbootmysql.specification;

import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.Date;

// https://stackoverflow.com/questions/39167189/spring-boot-dynamic-query
// https://spring.io/blog/2011/04/26/advanced-spring-data-jpa-specifications-and-querydsl/

public class MySpecification {
    public static Specification<Object> withEqual(String attribute, Object value) {
        if(value == null || ((value instanceof String) && ((String) value).isEmpty())) {
            return null;
        } else {
            // Specification using Java 8 lambdas
            return (root, query, cb) -> cb.equal(root.get(attribute), value);
        }
    }

    public static Specification<Object> withDateEqual(String attribute, LocalDate value) {
        // https://stackoverflow.com/questions/38424516/date-comparison-using-the-jpa-criteria-api
        if(value == null) {
            return null;
        } else {
            return (root, query, cb) -> cb.equal(root.<Date>get(attribute).as(java.sql.Date.class), java.sql.Date.valueOf(value));
        }
    }
}
