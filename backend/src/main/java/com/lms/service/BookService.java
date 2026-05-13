package com.lms.service;

import com.lms.dto.BookDto;
import com.lms.entity.Book;
import com.lms.exception.BadRequestException;
import com.lms.exception.ResourceNotFoundException;
import com.lms.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public Book createBook(BookDto bookDto) {
        if (bookRepository.findByIsbn(bookDto.getIsbn()).isPresent()) {
            throw new BadRequestException("Book with ISBN " + bookDto.getIsbn() + " already exists");
        }

        Book book = Book.builder()
                .title(bookDto.getTitle())
                .author(bookDto.getAuthor())
                .isbn(bookDto.getIsbn())
                .category(bookDto.getCategory())
                .description(bookDto.getDescription())
                .price(bookDto.getPrice())
                .totalCopies(bookDto.getTotalCopies())
                .availableCopies(bookDto.getTotalCopies())
                .publisher(bookDto.getPublisher())
                .publishYear(bookDto.getPublishYear())
                .isActive(true)
                .build();

        return bookRepository.save(book);
    }

    public Book updateBook(Long bookId, BookDto bookDto) {
        Book book = getBookById(bookId);

        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());
        book.setCategory(bookDto.getCategory());
        book.setDescription(bookDto.getDescription());
        book.setPrice(bookDto.getPrice());
        book.setPublisher(bookDto.getPublisher());
        book.setPublishYear(bookDto.getPublishYear());

        return bookRepository.save(book);
    }

    public Book getBookById(Long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + bookId));
    }

    public List<Book> getBooksByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }

    public List<Book> getBooksByAuthor(String author) {
        return bookRepository.findByAuthorContainingIgnoreCase(author);
    }

    public List<Book> getBooksByCategory(String category) {
        return bookRepository.findByCategory(category);
    }

    public List<Book> getAllActiveBooks() {
        return bookRepository.findByIsActive(true);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public void deleteBook(Long bookId) {
        Book book = getBookById(bookId);
        book.setIsActive(false);
        bookRepository.save(book);
    }

    public Book increaseBookCopies(Long bookId, Integer count) {
        Book book = getBookById(bookId);
        book.setTotalCopies(book.getTotalCopies() + count);
        book.setAvailableCopies(book.getAvailableCopies() + count);
        return bookRepository.save(book);
    }

    public long getTotalBooksCount() {
        return bookRepository.countActiveBooks();
    }
}
