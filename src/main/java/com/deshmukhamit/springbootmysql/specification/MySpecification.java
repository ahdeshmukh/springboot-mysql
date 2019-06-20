package com.deshmukhamit.springbootmysql.specification;

//import com.deshmukhamit.springbootmysql.model.User;
import org.springframework.data.jpa.domain.Specification;

// https://stackoverflow.com/questions/39167189/spring-boot-dynamic-query

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
