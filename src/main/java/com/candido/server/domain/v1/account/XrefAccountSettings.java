package com.candido.server.domain.v1.account;

import com.fasterxml.jackson.annotation.JsonProperty;
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
@Table(name = "xref_account_settings")
@IdClass(XrefAccountSettingsIdentity.class)
public class XrefAccountSettings {

    @Id
    @Column(name = "fk_account_id")
    private Long accountId;

    @Id
    @Column(name = "fk_account_settings_id")
    private int accountSettingsId;

    @OneToOne
    @JoinColumn(name = "fk_account_settings_id", referencedColumnName = "account_settings_id", insertable = false, updatable = false)
    private AccountSettings accountSettings;

    @JsonProperty("value")
    @Column(name = "settings_value")
    private String value;

}
