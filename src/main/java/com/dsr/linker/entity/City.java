package com.dsr.linker.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @ManyToOne
    @JoinColumn(name="country_id", nullable = false)
    private Country country;

    @OneToMany(mappedBy = "city")
    private Set<Account> accounts;

}
