package com.candido.server.domain.v1.candido;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "application")
public class Application {

    @Id
    @Column(name = "application_id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "fk_account_id", insertable = false, updatable = false)
    private Account account;

    @OneToOne
    @JoinColumn(name = "fk_application_form_id", insertable = false, updatable = false)
    private ApplicationForm form;

    @ManyToOne
    @JoinColumn(name = "fk_application_status_id", insertable = false, updatable = false)
    private ApplicationStatus status;

    @Column(name = "filled_body")
    private String body;

    @Column(name = "progress")
    private int progress;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "sent_at")
    private LocalDateTime sentAt;

    public Application() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public ApplicationForm getForm() {
        return form;
    }

    public void setForm(ApplicationForm form) {
        this.form = form;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }

    @Override
    public String toString() {
        return "Application{" +
                "id=" + id +
                ", account=" + account +
                ", form=" + form +
                ", status=" + status +
                ", body='" + body + '\'' +
                ", progress=" + progress +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", deletedAt=" + deletedAt +
                ", sentAt=" + sentAt +
                '}';
    }
}
