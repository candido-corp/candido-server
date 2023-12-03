package com.candido.server.domain.v1.candido;

import java.io.Serializable;


public class ApplicationFormAdminIdentity implements Serializable {
    private int formId;
    private int accountId;

    public ApplicationFormAdminIdentity(int formId, int accountId) {
        this.formId = formId;
        this.accountId = accountId;
    }

    public int getFormId() {
        return formId;
    }

    public void setFormId(int formId) {
        this.formId = formId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }
}
