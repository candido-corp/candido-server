package com.candido.server.domain.v1.candido;

import jakarta.persistence.*;

@Entity
@Table
public class ApplicationFeedback {

    @Id
    @Column(name = "application_feedback_id")
    private int id;

    @OneToOne
    @JoinColumn(name = "fk_application_id", insertable = false, updatable = false)
    private Application application;

    @ManyToOne
    @JoinColumn(name = "fk_sender_account_id", insertable = false, updatable = false)
    private Account accountSender;

    @ManyToOne
    @JoinColumn(name = "fk_application_feedback_category_id", insertable = false, updatable = false)
    private ApplicationFeedbackCategory category;

    public ApplicationFeedback() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public Account getAccountSender() {
        return accountSender;
    }

    public void setAccountSender(Account accountSender) {
        this.accountSender = accountSender;
    }

    public ApplicationFeedbackCategory getCategory() {
        return category;
    }

    public void setCategory(ApplicationFeedbackCategory category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "ApplicationFeedback{" +
                "id=" + id +
                ", application=" + application +
                ", accountSender=" + accountSender +
                ", category=" + category +
                '}';
    }
}
