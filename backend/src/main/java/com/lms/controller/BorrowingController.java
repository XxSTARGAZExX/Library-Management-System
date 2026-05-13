package com.lms.controller;

import com.lms.dto.ApiResponse;
import com.lms.entity.BorrowingTransaction;
import com.lms.service.BorrowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/borrowing")
@CrossOrigin
public class BorrowingController {

    @Autowired
    private BorrowingService borrowingService;

    @PostMapping("/borrow")
    @PreAuthorize("hasRole('MEMBER') or hasRole('ADMIN') or hasRole('LIBRARIAN')")
    public ResponseEntity<?> borrowBook(@RequestParam Long memberId, @RequestParam Long bookId) {
        try {
            BorrowingTransaction transaction = borrowingService.borrowBook(memberId, bookId);
            return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Book borrowed successfully", transaction));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.error(e.getMessage()));
        }
    }

    @PostMapping("/return/{transactionId}")
    @PreAuthorize("hasRole('MEMBER') or hasRole('ADMIN') or hasRole('LIBRARIAN')")
    public ResponseEntity<?> returnBook(@PathVariable Long transactionId) {
        try {
            BorrowingTransaction transaction = borrowingService.returnBook(transactionId);
            return ResponseEntity.ok(ApiResponse.success("Book returned successfully", transaction));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/member/{memberId}")
    @PreAuthorize("hasRole('MEMBER') or hasRole('ADMIN') or hasRole('LIBRARIAN')")
    public ResponseEntity<?> getMemberTransactions(@PathVariable Long memberId) {
        try {
            List<BorrowingTransaction> transactions = borrowingService.getMemberTransactions(memberId);
            return ResponseEntity.ok(ApiResponse.success("Member transactions fetched successfully", transactions));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/overdue")
    @PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN')")
    public ResponseEntity<?> getOverdueTransactions() {
        try {
            List<BorrowingTransaction> transactions = borrowingService.getOverdueTransactions();
            return ResponseEntity.ok(ApiResponse.success("Overdue transactions fetched successfully", transactions));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<?> getTransactionById(@PathVariable Long transactionId) {
        try {
            BorrowingTransaction transaction = borrowingService.getTransactionById(transactionId);
            return ResponseEntity.ok(ApiResponse.success("Transaction fetched successfully", transaction));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllTransactions() {
        try {
            List<BorrowingTransaction> transactions = borrowingService.getAllTransactions();
            return ResponseEntity.ok(ApiResponse.success("All transactions fetched successfully", transactions));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.error(e.getMessage()));
        }
    }
}
