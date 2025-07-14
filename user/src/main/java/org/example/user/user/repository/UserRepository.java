package org.example.user.user.repository;

import org.example.user.user.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository  extends JpaRepository<User, Long> {
    @Query("select u from User u where u.emailAddress=:email")
    Optional<User> findByEmail(String userEmail);


    boolean existsByEmailAddress(String emailAddress);



}
