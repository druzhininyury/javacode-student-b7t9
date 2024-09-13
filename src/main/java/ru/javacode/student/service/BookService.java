package ru.javacode.student.service;

import org.springframework.data.domain.Pageable;
import ru.javacode.student.model.Book;
import ru.javacode.student.model.dto.BookDtoNew;
import ru.javacode.student.model.dto.BookDtoUpdate;

import java.util.List;

public interface BookService {

    List<Book> getAllBooks(Pageable pageable);

    Book getBook(long bookId);

    Book addBook(BookDtoNew bookDtoNew);

    Book updateBook(BookDtoUpdate bookDtoUpdate);

    void deleteBook(long bookId);

}
