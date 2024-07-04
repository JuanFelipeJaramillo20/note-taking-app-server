package com.jjar.note_taking_app_server.Repositories;

import com.jjar.note_taking_app_server.Entities.Note;
import com.jjar.note_taking_app_server.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {

    List<Note> findByUserAndArchived(User user, boolean archived);
    List<Note> findByUserAndCategories_Name(User user, String categoryName);
    List<Note> findByUserId(Long id);
}
