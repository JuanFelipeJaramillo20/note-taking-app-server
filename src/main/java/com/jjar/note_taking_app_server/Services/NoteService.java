package com.jjar.note_taking_app_server.Services;

import com.jjar.note_taking_app_server.Entities.Note;
import com.jjar.note_taking_app_server.Entities.User;
import com.jjar.note_taking_app_server.Repositories.NoteRepository;
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

    public List<Note> getActiveNotesForAuthenticatedUser() {
        User authenticatedUser = authService.getAuthenticatedUser();
        return noteRepository.findByUserIdAndArchivedFalse(authenticatedUser);
    }

    public List<Note> getArchivedNotesForAuthenticatedUser() {
        User authenticatedUser = authService.getAuthenticatedUser();
        return noteRepository.findByUserIdAndArchivedTrue(authenticatedUser);
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