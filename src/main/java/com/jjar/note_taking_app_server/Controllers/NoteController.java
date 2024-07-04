package com.jjar.note_taking_app_server.Controllers;

import com.jjar.note_taking_app_server.Dtos.CreateNoteRequest;
import com.jjar.note_taking_app_server.Dtos.NoteResponse;
import com.jjar.note_taking_app_server.Entities.Category;
import com.jjar.note_taking_app_server.Entities.Note;
import com.jjar.note_taking_app_server.Services.CategoryService;
import com.jjar.note_taking_app_server.Services.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
@CrossOrigin()
public class NoteController {

    @Autowired
    private NoteService noteService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<NoteResponse>> getAllNotes() {
        List<NoteResponse> notes = noteService.getAllNotes();
        return ResponseEntity.ok(notes);
    }

    @GetMapping("/get-notes")
    public ResponseEntity<List<NoteResponse>> getAllNotesByUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        List<NoteResponse> notes = noteService.getAllNotesByUsername(username);
        return ResponseEntity.ok(notes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoteResponse> getNoteById(@PathVariable Long id) {
        NoteResponse note = noteService.getNoteById(id);
        return note != null ? ResponseEntity.ok(note) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Note> createNote(@RequestBody CreateNoteRequest createNoteRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Set<Category> categories = createNoteRequest.getCategoryNames().stream()
                .map(categoryService::getOrCreateCategory)
                .collect(Collectors.toSet());
        Note note = new Note();
        note.setTitle(createNoteRequest.getTitle());
        note.setContent(createNoteRequest.getContent());
        note.setCategories(categories);

        Note createdNote = noteService.createNoteForUser(note, username);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdNote);
    }

    @GetMapping("/active")
    public ResponseEntity<List<NoteResponse>> getActiveNotes() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        List<NoteResponse> notes = noteService.getActiveNotesForUser(username);
        return ResponseEntity.ok(notes);
    }

    @GetMapping("/archived")
    public ResponseEntity<List<NoteResponse>> getArchivedNotes() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        List<NoteResponse> notes = noteService.getArchivedNotesForUser(username);
        return ResponseEntity.ok(notes);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Note> updateNote(@PathVariable Long id, @RequestBody CreateNoteRequest noteRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Note existingNote = noteService.findById(id);
        if (existingNote == null) {
            return ResponseEntity.notFound().build();
        }

        if (!existingNote.getUser().getUsername().equals(username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Set<Category> categories = noteRequest.getCategoryNames().stream()
                .map(categoryService::getOrCreateCategory)
                .collect(Collectors.toSet());

        existingNote.setTitle(noteRequest.getTitle());
        existingNote.setContent(noteRequest.getContent());
        existingNote.setCategories(categories);

        Note updatedNote = noteService.updateNoteForUser(existingNote, username);
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

}
