package com.project.pickItUp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "address_table")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;

    @Column(name = "address")
    private String address;

    @JoinColumn(name = "city_id", referencedColumnName = "id")
    private City city;

    @JoinColumn(name = "state_id", referencedColumnName = "id")
    private State state;

    @JoinColumn(name = "country_id", referencedColumnName = "id")
    private Country country;

    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @JoinColumn(name = "organization_id", referencedColumnName = "id")
    private Organization organization;

    @JoinColumn(name = "event_id", referencedColumnName = "id")
    private Event event;

    @Enumerated
    @Column(name = "parent_type")
    private ParentType parentType;

}

enum ParentType {
    USER,
    ORGANIZATION,
    EVENT
}