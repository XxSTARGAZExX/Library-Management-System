package com.lms.repository;

import com.lms.entity.Fine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FineRepository extends JpaRepository<Fine, Long> {
    List<Fine> findByMemberId(Long memberId);
    List<Fine> findByStatus(Fine.FineStatus status);

    @Query("SELECT SUM(f.amount) FROM Fine f WHERE f.status = 'PENDING'")
    Double getTotalPendingFines();

    @Query("SELECT SUM(f.amount) FROM Fine f WHERE f.member.id = ?1 AND f.status = 'PENDING'")
    Double getTotalPendingFinesForMember(Long memberId);
}
