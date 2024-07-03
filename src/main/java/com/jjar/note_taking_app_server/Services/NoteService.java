package com.jjar.note_taking_app_server.Services;

import com.jjar.note_taking_app_server.Entities.Note;
import com.jjar.note_taking_app_server.Entities.User;
import com.jjar.note_taking_app_server.Repositories.NoteRepository;
import com.jjar.note_taking_app_server.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    public void archiveNoteById(Long id) {
        Note note = noteRepository.findById(id).orElseThrow(() -> new RuntimeException("Note not found"));
        note.setArchived(true);
        noteRepository.save(note);
    }

    public void unarchiveNoteById(Long id) {
        Note note = noteRepository.findById(id).orElseThrow(() -> new RuntimeException("Note not found"));
        note.setArchived(false);
        noteRepository.save(note);
    }

    public Note createNoteForUser(Note note, String username) {
        Optional<User> user = userRepository.findByUsername(username);
        user.ifPresent(note::setUser);
        return noteRepository.save(note);
    }

    public List<Note> getActiveNotesForUser(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if(user.isPresent()) {
            return noteRepository.findByUserAndArchived(user.get(), false);
        }
        else{
            return List.of();
        }
    }

    public List<Note> getArchivedNotesForUser(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if(user.isPresent()) {
            return noteRepository.findByUserAndArchived(user.get(), true);
        }
        else{
            return List.of();
        }
    }

    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }

    public Optional<Note> getNoteById(Long id) {
        return noteRepository.findById(id);
    }

    public Note createOrUpdateNote(Note note) {
        return noteRepository.save(note);
    }

    public void deleteNoteById(Long id) {
        noteRepository.deleteById(id);
    }
}