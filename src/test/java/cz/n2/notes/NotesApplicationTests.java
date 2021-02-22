package cz.n2.notes;

import cz.n2.notes.model.Note;
import cz.n2.notes.service.NoteService;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.Assert;
import org.junit.jupiter.api.TestInstance;
import org.junit.rules.ExpectedException;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@Transactional
class NotesApplicationTests {

	private NoteService noteService;

//	private ExpectedException expectedEx = ExpectedException.none();

	Note note1 = new Note(LocalDate.now(), LocalDate.now(), "Heading one", "Body 1");
	Note note2 = new Note(LocalDate.now(), LocalDate.now(), "Heading two", "Body 2");
	Note note3 = new Note(LocalDate.now(), LocalDate.now(), "Heading three", "Body 3 3");

	@Autowired
	public final void setup(NoteService noteService) {
		this.noteService = noteService;
	}

	@Test
	void testCreateNote() {

		// TODO změnit na count notes
		List<Note> notes = noteService.getNotes();

		// Create three testing notes
		this.noteService.addNote(note1);
		this.noteService.addNote(note2);
		Note noteTest = this.noteService.addNote(note3);

		// Check if created testing note has Heading of value "Heading 3"
		Assert.assertEquals(noteService.getNote(noteTest.getId()).getHeading(), note3.getHeading());

		// Check if all three notes has been sucessfully created and notes count is equal three
		Assert.assertEquals(noteService.getNotes().size(), notes.size() + 3);
	}

	@Test
	void testUpdateNote() {
		final String updatedHeading = "Updated heading";

		// Create one testing note
		this.noteService.addNote(note1);

		List<Note> notes = noteService.getNotes();

		// Check if note has been created
		Assert.assertTrue(notes.size() > 0);

		// Get one note & update Heading attribute
		Note note = notes.get(0);
		noteService.updateNote(note.getId(), updatedHeading, note.getBody());

		// Check if note Heading attribute has been updated
		Assert.assertEquals(noteService.getNote(note.getId()).getHeading(), updatedHeading);
	}

	@Test
	void testDeleteNoteCount() {
		// Create test note
		Note noteTest = noteService.addNote(note1);

		// Get notes count
		Integer noteCount = noteService.getNotes().size();

		// Delete note
		noteService.deleteNote(noteTest.getId());

		// Check if current notes count is less than previous notes count
		Assert.assertTrue(noteService.getNotes().size() < noteCount.longValue());
	}

	@Test
	void testDeleteNoteException() {
		// Create test note
		Note noteTest = noteService.addNote(note1);
		// Delete note
		noteService.deleteNote(noteTest.getId());
		// Check if
		IllegalStateException e = assertThrows(
				IllegalStateException.class,
				() -> noteService.getNote(noteTest.getId()),
				"Expected getNote() to throw error"
		);
		// Check if e message matches message from deleteNote method
		Assert.assertEquals(e.getMessage(), "note with " + noteTest.getId() + " does not exist");
	}

	@Test
	void testGetNotes() {
		//Count notes
		Integer notesCount = noteService.getNotes().size();
		// Add test note to database
		Note noteTest = noteService.addNote(note1);
		// Get  new count with all notes
		Integer newNotesCounts = noteService.getNotes().size();
		// Check that original count + 1 matches new count
		Assert.assertTrue(notesCount + 1 == newNotesCounts);
	}

	@Test
	void testStatsCountByDate() {
		// Add one note to database
		noteService.addNote(note1);
		//Get count of notes with specified date
		Integer count = noteService.getCountByDate(LocalDate.now().toString());
		// Add two other notes
		noteService.addNote(note2);
		noteService.addNote(note3);
		// Get new count with all notes in database
		Integer newCount = noteService.getCountByDate(LocalDate.now().toString());
		// Check that new count is larger by 2 than original
		Assert.assertTrue(newCount == count + 2);
	}

	@Test
	void testAverageWords() {
		// Add one note with 2 words in body to database
		noteService.addNote(note1);
		// Get count of words of notes in database
		Double count = noteService.getAverageWords();
		// Check if number of words in database is 2
		Assert.assertTrue(count == 2.0);
		// Add another note with 3 words in body to database
		noteService.addNote(note3);
		//Get newCount of words in database
		Double newCount = noteService.getAverageWords();
		//Check that new count is equal to 2,5 words
		Assert.assertTrue(newCount == 2.5);
	}

}
