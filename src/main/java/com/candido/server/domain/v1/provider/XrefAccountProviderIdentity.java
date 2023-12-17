package com.candido.server.domain.v1.provider;

import lombok.Data;

import java.io.Serializable;

@Data
public class XrefAccountProviderIdentity implements Serializable {
    private int accountId;
    private int providerId;
}
