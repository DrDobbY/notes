package cz.n2.notes.controller;

import cz.n2.notes.model.Note;
import cz.n2.notes.service.NoteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/notes")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public List<Note> getNotes() {
        return noteService.getNotes();
    }

    @GetMapping(path = "{noteId}")
    public Note getNote(@PathVariable("noteId") Long noteId) {
        return noteService.getNote(noteId);
    }

    @PostMapping
    public void addNewNote(@RequestBody Note note) {
        noteService.addNote(note);
    }

    @DeleteMapping(path = "{noteId}")
    public void deleteNote(@PathVariable("noteId") Long noteId) {
        noteService.deleteNote(noteId);
    }

    @PutMapping(path = "{noteId}")
    public void updateNote(@PathVariable("noteId") Long noteId, @RequestBody(required = false) Note note) {
        noteService.updateNote(noteId, note.getHeading(), note.getBody());
    }

    @GetMapping(path = "/stats/{date}")
    public Integer getCountByDate(@PathVariable("date") String date) {
        return noteService.getCountByDate(date);
    }

    @GetMapping(path = "/stats/words")
    public Double countAverageWords() {
        return noteService.getAverageWords();
    }

}

