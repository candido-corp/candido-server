package com.candido.server.domain.v1.candido;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "application_feedback_category")
public class ApplicationFeedbackCategory {

    @Id
    @Column(name = "application_feedback_category_id")
    private int id;

    @Column(name = "description")
    private String description;

    public ApplicationFeedbackCategory() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "ApplicationFeedbackCategory{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }
}
