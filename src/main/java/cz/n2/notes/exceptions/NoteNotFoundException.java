package cz.n2.notes.exceptions;

public class NoteNotFoundException extends RuntimeException {
    public NoteNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
