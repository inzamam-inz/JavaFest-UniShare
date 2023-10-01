package com.unishare.backend.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
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
    private Double marketPrice;
    private Double basePrice;
    private Double perDayPrice;
    private String status;
    private Boolean isRestricted;
    private String image1;
    private String image2;
    private String image3;

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

//    @Override
//    public String toString() {
//        String bookings = "[ ";
//        for (Booking booking : this.bookings) {
//            bookings += booking.toString() + "\n";
//        }
//        bookings += " ]";
//        return "sProduct{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", description='" + description + '\'' +
//                ", marketPrice='" + marketPrice + '\'' +
//                ", basePrice='" + basePrice + '\'' +
//                ", perDayPrice='" + perDayPrice + '\'' +
//                ", status='" + status + '\'' +
//                ", isRestricted='" + isRestricted + '\'' +
//                ", image1='" + image1 + '\'' +
//                ", image2='" + image2 + '\'' +
//                ", image3='" + image3 + '\'' +
//                ", owner='" + owner + '\'' +
//                ", category='" + category + '\'' +
//                ", bookings='" + bookings + '\'' +
//                ", tags='" + tags + '\'' +
//                '}';
//    }
}
