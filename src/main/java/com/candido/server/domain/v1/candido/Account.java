package com.candido.server.domain.v1.candido;

import jakarta.persistence.*;
import org.checkerframework.checker.units.qual.C;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "account")
public class Account {

    @Id
    @Column(name = "account_id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "fk_account_status_id", insertable = false, updatable = false)
    private AccountStatus status;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public Account() {
    }

    public Account(int id, AccountStatus status, String email, String password, LocalDateTime createdAt, LocalDateTime deletedAt) {
        this.id = id;
        this.status = status;
        this.email = email;
        this.password = password;
        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return id == account.id && Objects.equals(status, account.status) && Objects.equals(email, account.email) && Objects.equals(password, account.password) && Objects.equals(createdAt, account.createdAt) && Objects.equals(deletedAt, account.deletedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status, email, password, createdAt, deletedAt);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", status=" + status +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", createdAt=" + createdAt +
                ", deletedAt=" + deletedAt +
                '}';
    }
}
