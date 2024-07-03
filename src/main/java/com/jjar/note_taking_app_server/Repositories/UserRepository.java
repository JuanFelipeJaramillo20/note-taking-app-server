package com.jjar.note_taking_app_server.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.jjar.note_taking_app_server.Entities.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
