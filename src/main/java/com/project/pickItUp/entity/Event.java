package com.project.pickItUp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "event_table")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "name")
    @NonNull
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "start_date")
    @NonNull
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "is_valid")
    private int isValid;

    @ManyToOne
    @JoinColumn(name = "organization_id", referencedColumnName = "id")
    private Organization organization;

    @ManyToMany
    @JoinTable(
            name = "event_user_table",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "event_id", referencedColumnName = "id")
    )
    private List<User> eventVolunteers = new ArrayList<>();

    @OneToMany(mappedBy = "event")
    private List<Address> addresses = new ArrayList<>();

    @OneToMany(mappedBy = "forEvent")
    private List<PickupRequest> pickupRequests = new ArrayList<>();
}
