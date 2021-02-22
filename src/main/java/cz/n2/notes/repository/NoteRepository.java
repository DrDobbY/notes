package cz.n2.notes.repository;

import cz.n2.notes.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface NoteRepository extends JpaRepository<Note,Long> {


    Optional<Note> findByHeading(String heading);

    @Query("SELECT COUNT(n) FROM Note n WHERE n.creationDate = ?1")
    Integer countNotesByCreationDate(LocalDate date);




}
