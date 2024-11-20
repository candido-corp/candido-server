package com.candido.server.domain.v1.application;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "application")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("application_id")
    @Column(name = "application_id")
    private int applicationId;

    @JsonProperty("filled_body")
    @Column(name = "filled_body")
    private String filledBody;

    @JsonProperty("progress")
    @Column(name = "progress")
    private int progress;

    @JsonProperty("created_at")
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @JsonProperty("deleted_at")
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @JsonProperty("sent_at")
    @Column(name = "sent_at")
    private LocalDateTime sentAt;

}
