package com.lms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDto {
    private Long id;
    private String title;
    private String author;
    private String isbn;
    private String category;
    private String description;
    private Double price;
    private Integer totalCopies;
    private Integer availableCopies;
    private String publisher;
    private Integer publishYear;
    private Boolean isActive;
}
