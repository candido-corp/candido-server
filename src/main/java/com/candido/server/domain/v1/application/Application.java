package com.candido.server.domain.v1.application;

import com.candido.server.domain.v1.account.Account;
import com.candido.server.domain.v1.opportunity.Opportunity;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
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

    @JsonProperty("fk_account_id")
    @Column(name = "fk_account_id")
    private Long accountId;

    @ManyToOne
    @JoinColumn(name = "fk_account_id", insertable = false, updatable = false)
    private Account account;

    @OneToOne
    @JoinColumn(name = "fk_opportunity_id")
    private Opportunity opportunity;

    @ManyToOne
    @JoinColumn(name = "fk_application_status_id", insertable = false, updatable = false)
    private ApplicationStatus applicationStatus;

    @JsonProperty("fk_application_status_id")
    @Column(name = "fk_application_status_id")
    private int applicationStatusId;

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
