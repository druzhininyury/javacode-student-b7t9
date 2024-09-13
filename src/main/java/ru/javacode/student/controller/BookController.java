package ru.javacode.student.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.javacode.student.model.Book;
import ru.javacode.student.model.dto.BookDtoNew;
import ru.javacode.student.model.dto.BookDtoUpdate;
import ru.javacode.student.service.BookService;

import java.util.List;

@RestController
@RequestMapping("/books")
@Validated
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Book> getAllBooks(Pageable pageable) {
        return bookService.getAllBooks(pageable);
    }

    @GetMapping("/{bookId}")
    @ResponseStatus(HttpStatus.OK)
    public Book getBook(@PathVariable long bookId) {
        return bookService.getBook(bookId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book addBook(@RequestBody BookDtoNew bookDtoNew) {
        return bookService.addBook(bookDtoNew);
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public Book updateBook(@RequestBody @Valid BookDtoUpdate bookDtoUpdate) {
        return bookService.updateBook(bookDtoUpdate);
    }

    @DeleteMapping("/{bookId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteBook(@PathVariable long bookId) {
        bookService.deleteBook(bookId);
    }

}
