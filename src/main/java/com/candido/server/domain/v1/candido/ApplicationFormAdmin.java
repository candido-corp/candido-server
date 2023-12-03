package com.candido.server.domain.v1.candido;

import jakarta.persistence.*;

@Entity
@Table(name = "application_form_admin")
@IdClass(ApplicationFormAdminIdentity.class)
public class ApplicationFormAdmin {

    @Id
    @Column(name = "fk_application_form_id")
    private ApplicationForm form;

    @Id
    @Column(name = "fk_account_id")
    private int accountId;

}
