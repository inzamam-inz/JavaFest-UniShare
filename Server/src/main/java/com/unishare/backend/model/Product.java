package com.unishare.backend.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private Double basePrice;
    private String status;
    private Boolean isRestricted;
    private String image;

    @ManyToOne
    @JoinColumn(name = "ownerId")
    private User owner;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<Booking> bookings = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "product_tags",
            joinColumns = @JoinColumn(name = "productID"),
            inverseJoinColumns = @JoinColumn(name = "tagID")
    )
    private List<Tag> tags = new ArrayList<>();


//    @OneToMany(mappedBy = "product")
//    private List<Review> reviews = new ArrayList<>();


//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "owner_id")
//    public User owner;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "category_id")
//    public Category category;

}
