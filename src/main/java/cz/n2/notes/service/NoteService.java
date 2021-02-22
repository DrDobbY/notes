package cz.n2.notes.service;


import cz.n2.notes.model.Note;
import cz.n2.notes.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class NoteService {

    private final NoteRepository noteRepository;

    @Autowired
    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public List<Note> getNotes() {
        return noteRepository.findAll();
    }

    public Note getNote(Long noteId) {
        return noteRepository.findById(noteId).orElseThrow(() -> new IllegalStateException("note with " + noteId + " does not exist"));
    }

    public Note addNote(Note note) {
        Optional<Note> noteByHeading = noteRepository.findByHeading(note.getHeading());

        if (noteByHeading.isPresent()) {
            throw new IllegalStateException("Note with this heading already exists");
        }
        note.setModificationDate(LocalDate.now());
        note.setCreationDate(LocalDate.now());

        return noteRepository.save(note);
    }

    public void deleteNote(Long noteId) {
        boolean exists = noteRepository.existsById(noteId);

        if (!exists) {
            throw new IllegalStateException("note with id " + noteId + " does not exists");
        }

        noteRepository.deleteById(noteId);
    }

    @Transactional
    public Note updateNote(Long noteId, String heading, String body) {
        Note note = noteRepository.findById(noteId).orElseThrow(() -> new IllegalStateException("note with " + noteId + "does not exist"));

        note.setModificationDate(LocalDate.now());

        if (heading != null && !Objects.equals(note.getHeading(), heading)) {
            note.setHeading(heading);
        }

        if (body != null && !Objects.equals(note.getBody(), body)) {
            note.setBody(body);
        }

        return note;
    }

    public Integer getCountByDate(String date) {
        LocalDate parsedDate = LocalDate.parse(date);
        return noteRepository.countNotesByCreationDate(parsedDate);
    }

    public double getAverageWords() {
        double averageWords = 0;
        List<Note> notes = noteRepository.findAll();
        for (int i = 0; i < notes.size(); i++) {
            averageWords += getCountWords(notes.get(i).getBody());
        }
        averageWords /= notes.size();
        return averageWords;
    }

    public static int getCountWords(String input) {
        if (input == null || input.isEmpty()) {
            return 0;
        }
        String[] words = input.split("\\s+");
        return words.length;
    }


}
