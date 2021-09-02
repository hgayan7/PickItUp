package com.project.pickItUp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.project.pickItUp.model.ParentType;
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
    private Long id;

    @Column(name = "address")
    private String address;

    @ManyToOne
    @JoinColumn(name = "city_id", referencedColumnName = "id")
    private City city;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "organization_id", referencedColumnName = "id")
    private Organization organization;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "event_id", referencedColumnName = "id")
    private Event event;

    @Enumerated
    @Column(name = "parent_type")
    private ParentType parentType;

}