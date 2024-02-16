package com.candido.server.domain.v1.token;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "xref_token_temporary_code")
@IdClass(XrefTokenTemporaryCode.class)
public class XrefTokenTemporaryCode {

    @Id
    @Column(name = "token_id")
    private int tokenId;

    @Id
    @Column(name = "temporary_code_id")
    private int temporaryCodeId;

}
