package com.candido.server.domain.v1.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "gender")
public class Gender {

    @Id
    @Column(name = "gender_id")
    private int id;

    @Column(name = "description")
    private String description;

}