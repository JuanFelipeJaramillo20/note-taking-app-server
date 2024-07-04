package com.jjar.note_taking_app_server.Services;

import com.jjar.note_taking_app_server.Dtos.NoteResponse;
import com.jjar.note_taking_app_server.Entities.Note;
import com.jjar.note_taking_app_server.Entities.User;
import com.jjar.note_taking_app_server.Repositories.NoteRepository;
import com.jjar.note_taking_app_server.Repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private ModelMapper modelMapper;

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

    public List<NoteResponse> getActiveNotesForUser(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.map(value -> noteRepository.findByUserAndArchived(value, false).stream()
                .map(note -> modelMapper.map(note, NoteResponse.class))
                .collect(Collectors.toList())).orElseGet(List::of);
    }

    public List<NoteResponse> getArchivedNotesForUser(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.map(value -> noteRepository.findByUserAndArchived(value, true).stream()
                .map(note -> modelMapper.map(note, NoteResponse.class))
                .collect(Collectors.toList())).orElseGet(List::of);
    }


    public List<NoteResponse> getAllNotes() {
        return noteRepository.findAll().stream()
                .map(note -> modelMapper.map(note, NoteResponse.class))
                .collect(Collectors.toList());
    }


    public NoteResponse getNoteById(Long id) {
        Optional <Note> optionalNote= noteRepository.findById(id);
        return optionalNote.map(note -> modelMapper.map(note, NoteResponse.class)).orElse(null);
    }

    public Note updateNoteForUser(Note note, String username) {
        Optional<User> user = userRepository.findByUsername(username);
        user.ifPresent(note::setUser);
        return noteRepository.save(note);
    }

    public void deleteNoteById(Long id) {
        noteRepository.deleteById(id);
    }

    public Note findById(Long id){
        return noteRepository.findById(id).orElseThrow(() -> new RuntimeException("Note not found"));
    }

    public List<NoteResponse> getAllNotesByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.map(value -> noteRepository.findByUserId(value.getId()).stream().map(note -> modelMapper.map(note, NoteResponse.class))
                .collect(Collectors.toList())).orElseGet(List::of);
    }
}