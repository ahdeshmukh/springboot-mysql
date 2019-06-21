package com.deshmukhamit.springbootmysql.specification;

import org.springframework.data.jpa.domain.Specification;

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
}
