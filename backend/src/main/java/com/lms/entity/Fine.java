package com.lms.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "fines")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Fine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "transaction_id", nullable = false)
    private BorrowingTransaction transaction;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private Integer overdueDays;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FineStatus status;

    private LocalDateTime paidDate;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public enum FineStatus {
        PENDING, PAID, WAIVED
    }
}
