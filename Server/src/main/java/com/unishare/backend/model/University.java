package com.unishare.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class University {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String universityName;

    @OneToMany(mappedBy = "university")
    private List<User> users;
}
