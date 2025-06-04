package com.candido.server.domain.v1.application;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "application_feedback")
public class ApplicationFeedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("application_feedback_id")
    @Column(name = "application_feedback_id")
    private int applicationFeedbackId;

    @OneToOne
    @JoinColumn(
            name = "fk_application_id",
            insertable = false,
            updatable = false
    )
    private Application application;

    @JsonProperty("application_id")
    @Column(name = "fk_application_id")
    private int applicationId;

}
