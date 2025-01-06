package com.sametp.example.tutorial;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tutorials")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tutorial {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "published")
    private boolean published;

}
