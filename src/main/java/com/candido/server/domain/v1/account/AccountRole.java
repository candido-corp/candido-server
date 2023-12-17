package com.candido.server.domain.v1.account;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "account_role")
public class AccountRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_role_id")
    private int id;

    @Column(name = "description")
    private String description;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "xref_role_permission",
            joinColumns = @JoinColumn(name = "fk_account_role_id"),
            inverseJoinColumns = @JoinColumn(name = "fk_account_permission_id")
    )
    private Set<AccountPermission> accountPermissions;

    public AccountRole(int id) {
        this.id = id;
    }

}
