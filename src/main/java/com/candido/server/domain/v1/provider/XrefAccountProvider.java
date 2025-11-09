package com.candido.server.domain.v1.provider;

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
@Table(name = "xref_account_provider")
@IdClass(XrefAccountProviderIdentity.class)
public class XrefAccountProvider {

    @Id
    @Column(name = "fk_account_id")
    private Long accountId;

    @Id
    @Column(name = "fk_provider_id")
    private int providerId;

}
