package com.jjar.note_taking_app_server.Controllers;

import com.jjar.note_taking_app_server.Entities.Note;
import com.jjar.note_taking_app_server.Services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @GetMapping
    public ResponseEntity<List<Note>> getAllNotes() {
        List<Note> notes = noteService.getAllNotes();
        return ResponseEntity.ok(notes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Note> getNoteById(@PathVariable Long id) {
        Optional<Note> note = noteService.getNoteById(id);
        return note.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Note> createNote(@RequestBody Note note) {
        Note createdNote = noteService.createOrUpdateNote(note);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdNote);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Note> updateNote(@PathVariable Long id, @RequestBody Note note) {
        note.setId(id);
        Note updatedNote = noteService.createOrUpdateNote(note);
        return ResponseEntity.ok(updatedNote);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNoteById(@PathVariable Long id) {
        noteService.deleteNoteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/archive")
    public void archiveNote(@PathVariable Long id) {
        noteService.archiveNoteById(id);
    }

    @PutMapping("/{id}/unarchive")
    public void unarchiveNote(@PathVariable Long id) {
        noteService.unarchiveNoteById(id);
    }

    @GetMapping("/active")
    public List<Note> getActiveNotesForUser() {
        return noteService.getActiveNotesForAuthenticatedUser();
    }

    @GetMapping("/archived")
    public List<Note> getArchivedNotesForUser() {
        return noteService.getArchivedNotesForAuthenticatedUser();
    }
}
