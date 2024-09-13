package ru.javacode.student.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.javacode.student.model.Author;
import ru.javacode.student.model.Book;
import ru.javacode.student.model.dto.BookDtoNew;
import ru.javacode.student.model.dto.BookDtoUpdate;
import ru.javacode.student.service.BookService;

import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;

import static org.hamcrest.Matchers.in;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService bookService;

    @Test
    public void getAllBooksTest_whenInputValid_thenBookReturned() throws Exception {
        int page = 1;
        int size = 1;

        Author author1 = new Author();
        author1.setId(1L);
        author1.setName("Boris");

        Author author2 = new Author();
        author2.setId(2L);
        author2.setName("John");

        Book book = new Book();
        book.setId(2L);
        book.setTitle("Art of programming");
        book.setAuthors(new HashSet<>(List.of(author1, author2)));

        when(bookService.getAllBooks(any(Pageable.class))).thenReturn(List.of(book));

        mvc.perform(get("/books?page={page}&size={size}", page, size)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(2L), Long.class))
                .andExpect(jsonPath("$[0].title", is(book.getTitle())))
                .andExpect(jsonPath("$[0].authors[0].id", in(new Long[]{1L, 2L}), Long.class))
                .andExpect(jsonPath("$[0].authors[1].id", in(new Long[]{1L, 2L}), Long.class));

    }

    @Test
    public void getBookTest_whenInputValid_thenBookReturned() throws Exception {
        long bookId = 1L;

        Author author1 = new Author();
        author1.setId(1L);
        author1.setName("Boris");

        Author author2 = new Author();
        author2.setId(2L);
        author2.setName("John");

        Book book = new Book();
        book.setId(bookId);
        book.setTitle("Art of programming");
        book.setAuthors(new HashSet<>(List.of(author1, author2)));

        when(bookService.getBook(bookId)).thenReturn(book);

        mvc.perform(get("/books/{bookId}", bookId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(bookId), Long.class))
                .andExpect(jsonPath("$.title", is(book.getTitle())))
                .andExpect(jsonPath("$.authors[0].id", in(new Long[]{1L, 2L}), Long.class))
                .andExpect(jsonPath("$.authors[1].id", in(new Long[]{1L, 2L}), Long.class));

    }

    @Test
    public void addBookTest_whenInputValid_thenBookAdded() throws Exception {
        long bookId = 1L;
        String requestBody = """
                {
                  "title": "Art of programming",
                  "authorsIds": [1, 2]
                }""";

        Author author1 = new Author();
        author1.setId(1L);
        author1.setName("Boris");

        Author author2 = new Author();
        author2.setId(2L);
        author2.setName("John");

        Book book = new Book();
        book.setId(bookId);
        book.setTitle("Art of programming");
        book.setAuthors(new HashSet<>(List.of(author1, author2)));

        when(bookService.addBook(any(BookDtoNew.class))).thenReturn(book);

        mvc.perform(post("/books")
                    .content(requestBody)
                    .characterEncoding(StandardCharsets.UTF_8)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(bookId), Long.class))
                .andExpect(jsonPath("$.title", is(book.getTitle())))
                .andExpect(jsonPath("$.authors[0].id", in(new Long[]{1L, 2L}), Long.class))
                .andExpect(jsonPath("$.authors[1].id", in(new Long[]{1L, 2L}), Long.class));

    }

    @Test
    public void updateBookTest_whenInputValid_thenBookUpdate() throws Exception {
        long bookId = 1L;
        String requestBody = """
                {
                  "id": 1,
                  "title": "Art of programming science.",
                  "authorsIds": [2, 3]
                }""";

        Author author3 = new Author();
        author3.setId(3L);
        author3.setName("Boris");

        Author author2 = new Author();
        author2.setId(2L);
        author2.setName("John");

        Book book = new Book();
        book.setId(bookId);
        book.setTitle("Art of programming science.");
        book.setAuthors(new HashSet<>(List.of(author3, author2)));

        when(bookService.updateBook(any(BookDtoUpdate.class))).thenReturn(book);

        mvc.perform(patch("/books")
                        .content(requestBody)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(bookId), Long.class))
                .andExpect(jsonPath("$.title", is(book.getTitle())))
                .andExpect(jsonPath("$.authors[0].id", in(new Long[]{2L, 3L}), Long.class))
                .andExpect(jsonPath("$.authors[1].id", in(new Long[]{2L, 3L}), Long.class));

    }

}
