package ru.javacode.student.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.javacode.student.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {



}
