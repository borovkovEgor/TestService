package com.borovkov.egor.testservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString(exclude = {"users"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Entity
@Table(name = "subscriptions", schema = "test_service")
public class Subscription {

    public Subscription(SubName name) {
        this.subName = name;
    }

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private SubName subName;

    @ManyToMany(mappedBy = "subscriptions")
    private Set<User> users = new HashSet<>();
}
