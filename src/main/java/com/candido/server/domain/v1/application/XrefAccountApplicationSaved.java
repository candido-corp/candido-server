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
@Table(name = "xref_account_application_saved")
@IdClass(XrefAccountApplicationSavedIdentity.class)
public class XrefAccountApplicationSaved {

    @Id
    @JsonProperty("application_form_id")
    @Column(name = "fk_application_form_id")
    private int applicationFormId;

    @Id
    @JsonProperty("account_id")
    @Column(name = "fk_account_id")
    private int accountId;

    @OneToOne
    @JoinColumn(name = "fk_application_form_id", insertable = false, updatable = false)
    private ApplicationForm applicationForm;

}
