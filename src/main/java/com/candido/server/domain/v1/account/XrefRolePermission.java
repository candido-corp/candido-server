package com.candido.server.domain.v1.account;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "xref_role_permission")
public class XrefRolePermission {

    @Id
    @Column(name = "fk_account_role_id")
    private int roleId;

    @Id
    @Column(name = "fk_account_permission_id")
    private int permissionId;

}
