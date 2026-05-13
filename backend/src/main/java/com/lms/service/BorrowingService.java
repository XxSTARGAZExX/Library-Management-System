package com.lms.service;

import com.lms.dto.BorrowingTransactionDto;
import com.lms.entity.Book;
import com.lms.entity.BorrowingTransaction;
import com.lms.entity.Member;
import com.lms.exception.BadRequestException;
import com.lms.exception.ResourceNotFoundException;
import com.lms.repository.BookRepository;
import com.lms.repository.BorrowingTransactionRepository;
import com.lms.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BorrowingService {

    @Autowired
    private BorrowingTransactionRepository transactionRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BookRepository bookRepository;

    private static final int BORROW_PERIOD_DAYS = 14;

    public BorrowingTransaction borrowBook(Long memberId, Long bookId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found"));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));

        if (member.getBorrowedBooks() >= member.getMaxBooks()) {
            throw new BadRequestException("Maximum book limit reached");
        }

        if (book.getAvailableCopies() <= 0) {
            throw new BadRequestException("No copies available for this book");
        }

        if (!member.getStatus().equals(Member.MembershipStatus.ACTIVE)) {
            throw new BadRequestException("Member account is not active");
        }

        LocalDate borrowDate = LocalDate.now();
        LocalDate dueDate = borrowDate.plusDays(BORROW_PERIOD_DAYS);

        BorrowingTransaction transaction = BorrowingTransaction.builder()
                .member(member)
                .book(book)
                .borrowDate(borrowDate)
                .dueDate(dueDate)
                .status(BorrowingTransaction.BorrowStatus.BORROWED)
                .dailyFineAmount(10.0)
                .build();

        BorrowingTransaction saved = transactionRepository.save(transaction);

        // Update book availability
        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookRepository.save(book);

        // Update member borrowed books count
        member.setBorrowedBooks(member.getBorrowedBooks() + 1);
        memberRepository.save(member);

        return saved;
    }

    public BorrowingTransaction returnBook(Long transactionId) {
        BorrowingTransaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));

        if (!transaction.getStatus().equals(BorrowingTransaction.BorrowStatus.BORROWED)) {
            throw new BadRequestException("Book is not currently borrowed");
        }

        LocalDate returnDate = LocalDate.now();
        transaction.setReturnDate(returnDate);
        transaction.setStatus(BorrowingTransaction.BorrowStatus.RETURNED);

        BorrowingTransaction saved = transactionRepository.save(transaction);

        // Update book availability
        Book book = transaction.getBook();
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        bookRepository.save(book);

        // Update member borrowed books count
        Member member = transaction.getMember();
        member.setBorrowedBooks(Math.max(0, member.getBorrowedBooks() - 1));
        memberRepository.save(member);

        return saved;
    }

    public List<BorrowingTransaction> getMemberTransactions(Long memberId) {
        return transactionRepository.findByMemberId(memberId);
    }

    public List<BorrowingTransaction> getOverdueTransactions() {
        return transactionRepository.findOverdueTransactions();
    }

    public BorrowingTransaction getTransactionById(Long transactionId) {
        return transactionRepository.findById(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));
    }

    public long getBorrowedBooksCount() {
        return transactionRepository.countBorrowedBooks();
    }

    public long getOverdueBooksCount() {
        return transactionRepository.countOverdueBooks();
    }

    public List<BorrowingTransaction> getAllTransactions() {
        return transactionRepository.findAll();
    }
}
