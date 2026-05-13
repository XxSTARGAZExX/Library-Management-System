package com.lms.controller;

import com.lms.dto.ApiResponse;
import com.lms.entity.Fine;
import com.lms.service.FineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fines")
@CrossOrigin
public class FineController {

    @Autowired
    private FineService fineService;

    @PostMapping("/calculate/{transactionId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN')")
    public ResponseEntity<?> calculateFine(@PathVariable Long transactionId) {
        try {
            Fine fine = fineService.calculateAndCreateFine(transactionId);
            return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Fine calculated successfully", fine));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.error(e.getMessage()));
        }
    }

    @PutMapping("/{fineId}/pay")
    @PreAuthorize("hasRole('MEMBER') or hasRole('ADMIN') or hasRole('LIBRARIAN')")
    public ResponseEntity<?> payFine(@PathVariable Long fineId) {
        try {
            Fine fine = fineService.payFine(fineId);
            return ResponseEntity.ok(ApiResponse.success("Fine paid successfully", fine));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/{fineId}")
    public ResponseEntity<?> getFineById(@PathVariable Long fineId) {
        try {
            Fine fine = fineService.getFineById(fineId);
            return ResponseEntity.ok(ApiResponse.success("Fine fetched successfully", fine));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/member/{memberId}")
    @PreAuthorize("hasRole('MEMBER') or hasRole('ADMIN') or hasRole('LIBRARIAN')")
    public ResponseEntity<?> getMemberFines(@PathVariable Long memberId) {
        try {
            List<Fine> fines = fineService.getMemberFines(memberId);
            return ResponseEntity.ok(ApiResponse.success("Member fines fetched successfully", fines));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/pending")
    @PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN')")
    public ResponseEntity<?> getPendingFines() {
        try {
            List<Fine> fines = fineService.getPendingFines();
            return ResponseEntity.ok(ApiResponse.success("Pending fines fetched successfully", fines));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/total-pending")
    public ResponseEntity<?> getTotalPendingFines() {
        try {
            Double total = fineService.getTotalPendingFines();
            return ResponseEntity.ok(ApiResponse.success("Total pending fines fetched successfully", total));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/member/{memberId}/pending")
    @PreAuthorize("hasRole('MEMBER') or hasRole('ADMIN') or hasRole('LIBRARIAN')")
    public ResponseEntity<?> getMemberPendingFines(@PathVariable Long memberId) {
        try {
            Double total = fineService.getMemberPendingFines(memberId);
            return ResponseEntity.ok(ApiResponse.success("Member pending fines fetched successfully", total));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.error(e.getMessage()));
        }
    }
}
