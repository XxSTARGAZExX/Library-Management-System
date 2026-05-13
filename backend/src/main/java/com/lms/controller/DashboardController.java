package com.lms.controller;

import com.lms.dto.ApiResponse;
import com.lms.dto.DashboardStatsDto;
import com.lms.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dashboard")
@CrossOrigin
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/stats")
    @PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN')")
    public ResponseEntity<?> getDashboardStats() {
        try {
            DashboardStatsDto stats = dashboardService.getDashboardStats();
            return ResponseEntity.ok(ApiResponse.success("Dashboard stats fetched successfully", stats));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(ApiResponse.error(e.getMessage()));
        }
    }
}
