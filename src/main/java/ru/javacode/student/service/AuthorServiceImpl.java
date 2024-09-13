package ru.javacode.student.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javacode.student.model.Author;
import ru.javacode.student.repository.AuthorRepository;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    @Transactional
    public Author addAuthor(Author author) {
        Author dbAuthor = authorRepository.save(author);
        return dbAuthor;
    }
}
