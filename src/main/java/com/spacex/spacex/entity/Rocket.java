package com.spacex.spacex.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "rockets")
@Entity
@Builder
public class Rocket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false)
    private int weightInPounds;
    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "rocket", cascade = CascadeType.ALL)
    private Set<Launch> launches;
}