package com.lms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardStatsDto {
    private Long totalBooks;
    private Long totalMembers;
    private Long borrowedBooks;
    private Long overdueBooks;
    private Double totalFines;
    private Long activeUsers;
}
