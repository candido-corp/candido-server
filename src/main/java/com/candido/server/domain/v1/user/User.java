package com.candido.server.domain.v1.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
@Table(name = "user")
public class User {

    @Id
    @Column(name = "user_id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "fk_gender_id", insertable = false, updatable = false)
    private Gender gender;

    @OneToOne
    @JoinColumn(name = "fk_address_id", insertable = false, updatable = false)
    private Address address;

    @Column(name = "fk_account_id")
    private int accountId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "birth_date")
    private LocalDate birthdate;

    @Column(name = "mobile_number")
    private String mobileNumber;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

}