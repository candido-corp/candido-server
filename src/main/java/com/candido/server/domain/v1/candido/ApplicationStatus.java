package com.candido.server.domain.v1.candido;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "application_status")
public class ApplicationStatus {

    @Id
    @Column(name = "application_status_id")
    private int id;

    @Column(name = "description")
    private String description;

    public ApplicationStatus() {
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
        return "ApplicationStatus{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }
}
