package com.candido.server.domain.v1.candido;

import jakarta.persistence.*;

@Entity
@Table(name = "application_form_admin")
public class ApplicationFormAdmin {

    @Id
    @OneToOne
    @JoinColumn(name = "fk_application_form_id", insertable = false, updatable = false)
    private ApplicationForm form;

    @Id
    @OneToOne
    @JoinColumn(name = "fk_account_id", insertable = false, updatable = false)
    private Account account;

}
