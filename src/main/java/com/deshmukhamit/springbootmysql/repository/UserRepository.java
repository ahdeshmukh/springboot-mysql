package com.deshmukhamit.springbootmysql.repository;

import com.deshmukhamit.springbootmysql.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation

    Optional<User> findByEmail(String email);
    List<User> findByOrderByIdDesc(); // sort by most recently created desc
    List<User> findByOrderByUpdatedAtDesc(); // sort by most recently updated desc
    List<User> findByActiveIs(int active); // select all users where active = {active}, either 0 or 1
    Optional<User> findByIdAndActive(Long id, int active);
}
