package com.project.pickItUp.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "user_table")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "contact_number")
    private String contactNo;

    @Column(name = "is_admin")
    private int idAdmin;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "organization_user_table",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "organization_id", referencedColumnName = "id")
    )
    private List<Organization> associatedOrganizations = new ArrayList<>();

    @ManyToMany(mappedBy = "eventVolunteers",fetch = FetchType.LAZY)
    private List<Event> events;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Address address;

    @OneToMany(mappedBy = "requestedBy", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PickupRequest> pickupRequests = new ArrayList<>();
}
