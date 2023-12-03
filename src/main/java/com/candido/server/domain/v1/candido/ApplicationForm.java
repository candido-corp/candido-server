package com.candido.server.domain.v1.candido;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "application_form")
public class ApplicationForm {

    @Id
    @Column(name = "application_form_id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "fk_account_id", insertable = false, updatable = false)
    private Account account;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "notes")
    private String notes;

    @Column(name = "max_applicants")
    private int maxApplicants;

    @Column(name = "application_form_body")
    private String body;

    @Column(name = "url_code")
    private String urlCode;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "feedback_publication_date")
    private LocalDateTime feedbackPublicationDate;

    @Column(name = "feedback_expiration_date")
    private LocalDateTime feedbackExpirationDate;

    public ApplicationForm() {
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

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getMaxApplicants() {
        return maxApplicants;
    }

    public void setMaxApplicants(int maxApplicants) {
        this.maxApplicants = maxApplicants;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getUrlCode() {
        return urlCode;
    }

    public void setUrlCode(String urlCode) {
        this.urlCode = urlCode;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
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

    public LocalDateTime getFeedbackPublicationDate() {
        return feedbackPublicationDate;
    }

    public void setFeedbackPublicationDate(LocalDateTime feedbackPublicationDate) {
        this.feedbackPublicationDate = feedbackPublicationDate;
    }

    public LocalDateTime getFeedbackExpirationDate() {
        return feedbackExpirationDate;
    }

    public void setFeedbackExpirationDate(LocalDateTime feedbackExpirationDate) {
        this.feedbackExpirationDate = feedbackExpirationDate;
    }

    @Override
    public String toString() {
        return "ApplicationForm{" +
                "id=" + id +
                ", account=" + account +
                ", displayName='" + displayName + '\'' +
                ", notes='" + notes + '\'' +
                ", maxApplicants=" + maxApplicants +
                ", body='" + body + '\'' +
                ", urlCode='" + urlCode + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", deletedAt=" + deletedAt +
                ", feedbackPublicationDate=" + feedbackPublicationDate +
                ", feedbackExpirationDate=" + feedbackExpirationDate +
                '}';
    }
}
