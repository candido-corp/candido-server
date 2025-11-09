package com.candido.server.domain.v1.account;

import lombok.Data;

import java.io.Serializable;

@Data
public class XrefAccountSettingsIdentity implements Serializable {
    private int accountId;
    private int accountSettingsId;
}
