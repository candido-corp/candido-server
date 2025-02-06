package com.candido.server.domain.v1.application;

import lombok.Data;

import java.io.Serializable;

@Data
public class XrefAccountApplicationSavedIdentity implements Serializable {
    int applicationFormId;
    int accountId;
}
