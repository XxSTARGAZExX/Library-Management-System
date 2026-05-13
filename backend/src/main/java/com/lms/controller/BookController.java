package com.lms.controller;

import com.lms.dto.ApiResponse;
import com.lms.dto.BookDto;
import com.lms.entity.Book;
import com.lms.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@CrossOrigin
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN')")
    public ResponseEntity<?> createBook(@Valid @RequestBody BookDto bookDto) {
        try {
            Book book = bookService.createBook(bookDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Book created successfully", book));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.error(e.getMessage()));
        }
    }

    @PutMapping("/{bookId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN')")
    public ResponseEntity<?> updateBook(@PathVariable Long bookId, @Valid @RequestBody BookDto bookDto) {
        try {
            Book book = bookService.updateBook(bookId, bookDto);
            return ResponseEntity.ok(ApiResponse.success("Book updated successfully", book));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<?> getBookById(@PathVariable Long bookId) {
        try {
            Book book = bookService.getBookById(bookId);
            return ResponseEntity.ok(ApiResponse.success("Book fetched successfully", book));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllBooks(@RequestParam(required = false) String title,
                                         @RequestParam(required = false) String author,
                                         @RequestParam(required = false) String category) {
        try {
            List<Book> books;
            if (title != null && !title.isEmpty()) {
                books = bookService.getBooksByTitle(title);
            } else if (author != null && !author.isEmpty()) {
                books = bookService.getBooksByAuthor(author);
            } else if (category != null && !category.isEmpty()) {
                books = bookService.getBooksByCategory(category);
            } else {
                books = bookService.getAllActiveBooks();
            }
            return ResponseEntity.ok(ApiResponse.success("Books fetched successfully", books));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(e.getMessage()));
        }
    }

    @DeleteMapping("/{bookId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN')")
    public ResponseEntity<?> deleteBook(@PathVariable Long bookId) {
        try {
            bookService.deleteBook(bookId);
            return ResponseEntity.ok(ApiResponse.success("Book deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(e.getMessage()));
        }
    }

    @PostMapping("/{bookId}/add-copies")
    @PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN')")
    public ResponseEntity<?> addBookCopies(@PathVariable Long bookId, @RequestParam Integer count) {
        try {
            Book book = bookService.increaseBookCopies(bookId, count);
            return ResponseEntity.ok(ApiResponse.success("Book copies added successfully", book));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(e.getMessage()));
        }
    }
}
