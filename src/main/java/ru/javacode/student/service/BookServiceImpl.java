package ru.javacode.student.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javacode.student.model.Author;
import ru.javacode.student.model.Book;
import ru.javacode.student.model.dto.BookDtoNew;
import ru.javacode.student.model.dto.BookDtoUpdate;
import ru.javacode.student.repository.AuthorRepository;
import ru.javacode.student.repository.BookRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Override
    public List<Book> getAllBooks(Pageable pageable) {
        List<Book> books = bookRepository.findAllByIsAvailable(true, pageable);
        return books;
    }

    @Override
    public Book getBook(long bookId) {
        Optional<Book> bookOptional = bookRepository.findByIdAndIsAvailable(bookId, true);
        if (bookOptional.isEmpty()) {
            throw new EntityNotFoundException("Book with id=" + bookId + " hasn't been found.");
        }
        Book dbBook = bookOptional.get();

        return dbBook;
    }

    @Override
    @Transactional
    public Book addBook(BookDtoNew bookDtoNew) {
        Book newBook = new Book();
        newBook.setTitle(bookDtoNew.getTitle());

        Set<Author> authors = new HashSet<>(authorRepository.findAllById(bookDtoNew.getAuthorsIds()));
        if (authors.size() < bookDtoNew.getAuthorsIds().size()) {
            throw new EntityNotFoundException("Not all authors has been found.");
        }
        newBook.setAuthors(authors);

        Book dbBook = bookRepository.save(newBook);

        return dbBook;
    }

    @Override
    @Transactional
    public Book updateBook(BookDtoUpdate bookDtoUpdate) {
        Optional<Book> bookOptional = bookRepository.findById(bookDtoUpdate.getId());
        if (bookOptional.isEmpty()) {
            throw new EntityNotFoundException("Book with id=" + bookDtoUpdate.getId() + " hasn't been found.");
        }
        Book dbBook = bookOptional.get();

        if (bookDtoUpdate.getTitle() != null) {
            dbBook.setTitle(bookDtoUpdate.getTitle());
        }
        if (bookDtoUpdate.getAuthorsIds() != null) {
            Set<Author> authors = new HashSet<>(authorRepository.findAllById(bookDtoUpdate.getAuthorsIds()));
            if (authors.size() < bookDtoUpdate.getAuthorsIds().size()) {
                throw new EntityNotFoundException("Not all authors has been found.");
            }
            dbBook.setAuthors(authors);
        }

        dbBook = bookRepository.save(dbBook);

        return dbBook;
    }

    @Override
    @Transactional
    public void deleteBook(long bookId) {
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if (bookOptional.isEmpty()) {
            throw new EntityNotFoundException("Book with id=" + bookId + " hasn't been found.");
        }
        Book dbBook = bookOptional.get();

        dbBook.setIsAvailable(false);

        bookRepository.save(dbBook);
    }
}
