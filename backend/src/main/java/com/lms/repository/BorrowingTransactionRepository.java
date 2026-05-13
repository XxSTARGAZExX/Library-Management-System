package com.lms.repository;

import com.lms.entity.BorrowingTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BorrowingTransactionRepository extends JpaRepository<BorrowingTransaction, Long> {
    List<BorrowingTransaction> findByMemberId(Long memberId);
    List<BorrowingTransaction> findByStatus(BorrowingTransaction.BorrowStatus status);
    List<BorrowingTransaction> findByBookId(Long bookId);

    @Query("SELECT COUNT(b) FROM BorrowingTransaction b WHERE b.status = 'BORROWED'")
    long countBorrowedBooks();

    @Query("SELECT COUNT(b) FROM BorrowingTransaction b WHERE b.status = 'OVERDUE' AND b.dueDate < CURRENT_DATE")
    long countOverdueBooks();

    @Query("SELECT b FROM BorrowingTransaction b WHERE b.status = 'BORROWED' AND b.dueDate < CURRENT_DATE")
    List<BorrowingTransaction> findOverdueTransactions();
}
