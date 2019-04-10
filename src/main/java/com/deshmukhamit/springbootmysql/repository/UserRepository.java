package com.deshmukhamit.springbootmysql.repository;

import com.deshmukhamit.springbootmysql.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
