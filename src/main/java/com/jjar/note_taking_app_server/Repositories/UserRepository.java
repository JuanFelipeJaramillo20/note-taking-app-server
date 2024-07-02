package com.jjar.note_taking_app_server.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.jjar.note_taking_app_server.Entities.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
