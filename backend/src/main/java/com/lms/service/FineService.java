package com.lms.service;

import com.lms.entity.BorrowingTransaction;
import com.lms.entity.Fine;
import com.lms.entity.Member;
import com.lms.exception.BadRequestException;
import com.lms.exception.ResourceNotFoundException;
import com.lms.repository.BorrowingTransactionRepository;
import com.lms.repository.FineRepository;
import com.lms.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class FineService {

    @Autowired
    private FineRepository fineRepository;

    @Autowired
    private BorrowingTransactionRepository transactionRepository;

    @Autowired
    private MemberRepository memberRepository;

    public Fine calculateAndCreateFine(Long transactionId) {
        BorrowingTransaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));

        if (!transaction.getStatus().equals(BorrowingTransaction.BorrowStatus.BORROWED)) {
            throw new BadRequestException("Can only calculate fine for borrowed books");
        }

        LocalDate today = LocalDate.now();
        if (today.isBefore(transaction.getDueDate())) {
            throw new BadRequestException("Book is not yet overdue");
        }

        long overdueDays = ChronoUnit.DAYS.between(transaction.getDueDate(), today);
        double fineAmount = overdueDays * transaction.getDailyFineAmount();

        Member member = transaction.getMember();

        Fine fine = Fine.builder()
                .member(member)
                .transaction(transaction)
                .amount(fineAmount)
                .overdueDays((int) overdueDays)
                .status(Fine.FineStatus.PENDING)
                .build();

        Fine savedFine = fineRepository.save(fine);

        // Update member total fines
        member.setTotalFines(member.getTotalFines() + fineAmount);
        memberRepository.save(member);

        // Update transaction status to OVERDUE
        transaction.setStatus(BorrowingTransaction.BorrowStatus.OVERDUE);
        transactionRepository.save(transaction);

        return savedFine;
    }

    public Fine payFine(Long fineId) {
        Fine fine = fineRepository.findById(fineId)
                .orElseThrow(() -> new ResourceNotFoundException("Fine not found"));

        if (fine.getStatus().equals(Fine.FineStatus.PAID)) {
            throw new BadRequestException("Fine is already paid");
        }

        fine.setStatus(Fine.FineStatus.PAID);
        fine.setPaidDate(LocalDateTime.now());

        return fineRepository.save(fine);
    }

    public Fine getFineById(Long fineId) {
        return fineRepository.findById(fineId)
                .orElseThrow(() -> new ResourceNotFoundException("Fine not found"));
    }

    public List<Fine> getMemberFines(Long memberId) {
        return fineRepository.findByMemberId(memberId);
    }

    public List<Fine> getPendingFines() {
        return fineRepository.findByStatus(Fine.FineStatus.PENDING);
    }

    public Double getTotalPendingFines() {
        Double total = fineRepository.getTotalPendingFines();
        return total != null ? total : 0.0;
    }

    public Double getMemberPendingFines(Long memberId) {
        Double total = fineRepository.getTotalPendingFinesForMember(memberId);
        return total != null ? total : 0.0;
    }
}
