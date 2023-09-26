package com.unishare.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String comment;
    private Double rating;



//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "reviewer_id")
//    public User reviewer;
}
