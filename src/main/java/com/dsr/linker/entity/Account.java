package com.dsr.linker.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true,length = 40)
    private String email;

    @Column(nullable = false, length = 40)
    private String password;

    @OneToOne
    @JoinColumn(name="role_id", nullable = false)
    private Role role;

    private Date birthday;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @OneToMany(mappedBy = "sender")
    private Set<Message> messages;

    @Column(nullable = false, length = 100)
    private String firstName;

    @Column(nullable = false, length = 100)
    private String lastName;

    private String description;

    @OneToOne
    @JoinColumn(name = "account_status_id", nullable = false)
    private AccountStatus status;
}
