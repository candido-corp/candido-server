package com.candido.server.domain.v1.application;

import com.candido.server.domain.v1.account.Account;
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
@Table(name = "application_form")
public class ApplicationForm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("application_form_id")
    @Column(name = "application_form_id")
    private int applicationFormId;

    @OneToOne
    @JoinColumn(name = "fk_account_id")
    private Account account;

    @JsonProperty("display_name")
    @Column(name = "display_name")
    private String displayName;

    @JsonProperty("notes")
    @Column(name = "notes")
    private String notes;

    @JsonProperty("max_applicants")
    @Column(name = "max_applicants")
    private int maxApplicants;

    @JsonProperty("application_form_body")
    @Column(name = "application_form_body")
    private String applicationFormBody;

    @JsonProperty("url_code")
    @Column(name = "url_code")
    private String urlCode;

    @JsonProperty("start_date")
    @Column(name = "start_date")
    private LocalDateTime startDate;

    @JsonProperty("end_date")
    @Column(name = "end_date")
    private LocalDateTime endDate;

}
