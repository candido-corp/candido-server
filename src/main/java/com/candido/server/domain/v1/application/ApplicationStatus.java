package com.candido.server.domain.v1.application;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "application_status")
public class ApplicationStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("application_status_id")
    @Column(name = "application_status_id")
    private int applicationStatusId;

    @JsonProperty("description")
    @Column(name = "description")
    private String description;

}
