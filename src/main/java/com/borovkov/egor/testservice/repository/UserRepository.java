package com.borovkov.egor.testservice.repository;

import com.borovkov.egor.testservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select s from User s where s.id=:id")
    Optional<User> findById(@Param("id") Long id);
}
