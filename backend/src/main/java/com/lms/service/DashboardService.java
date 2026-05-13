package com.lms.service;

import com.lms.dto.DashboardStatsDto;
import com.lms.repository.BookRepository;
import com.lms.repository.BorrowingTransactionRepository;
import com.lms.repository.FineRepository;
import com.lms.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BorrowingTransactionRepository transactionRepository;

    @Autowired
    private FineRepository fineRepository;

    public DashboardStatsDto getDashboardStats() {
        long totalBooks = bookRepository.countActiveBooks();
        long totalMembers = memberRepository.countByStatus(com.lms.entity.Member.MembershipStatus.ACTIVE);
        long borrowedBooks = transactionRepository.countBorrowedBooks();
        long overdueBooks = transactionRepository.countOverdueBooks();
        Double totalFines = fineRepository.getTotalPendingFines();
        long activeUsers = memberRepository.countByStatus(com.lms.entity.Member.MembershipStatus.ACTIVE);

        return DashboardStatsDto.builder()
                .totalBooks(totalBooks)
                .totalMembers(totalMembers)
                .borrowedBooks(borrowedBooks)
                .overdueBooks(overdueBooks)
                .totalFines(totalFines != null ? totalFines : 0.0)
                .activeUsers(activeUsers)
                .build();
    }
}
