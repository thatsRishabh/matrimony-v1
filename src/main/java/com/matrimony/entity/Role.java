package com.matrimony.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "roles_details")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    @OneToMany(mappedBy = "role" , fetch = FetchType.EAGER )
    private List<Permission> permission;

    @OneToMany(mappedBy = "role"  , fetch = FetchType.EAGER)
    private List<User> user;
}
