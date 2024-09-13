package ru.javacode.student.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.javacode.student.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByIsAvailable(boolean isAvailable, Pageable pageable);

    Optional<Book> findByIdAndIsAvailable(long id, boolean isAvailable);

}
