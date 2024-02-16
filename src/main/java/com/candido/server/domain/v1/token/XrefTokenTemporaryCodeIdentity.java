package com.candido.server.domain.v1.token;


import lombok.Data;

import java.io.Serializable;

@Data
public class XrefTokenTemporaryCodeIdentity implements Serializable {
    private int tokenId;
    private int temporaryCodeId;
}
