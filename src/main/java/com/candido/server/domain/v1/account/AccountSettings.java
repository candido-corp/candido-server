package com.candido.server.domain.v1.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "account_settings")
public class AccountSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("settings_id")
    @Column(name = "account_settings_id")
    private int accountSettingsId;

    @JsonProperty("settings_key")
    @Column(name = "account_settings_key")
    private String accountSettingsKey;

    @JsonProperty("description")
    @Column(name = "description")
    private String description;

}
