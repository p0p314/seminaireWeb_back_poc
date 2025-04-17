package com.seminaireweb.seminaireweb_poc.repository;

import com.seminaireweb.seminaireweb_poc.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
