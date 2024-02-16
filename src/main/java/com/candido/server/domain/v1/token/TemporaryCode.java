package com.candido.server.domain.v1.token;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Entity
@Table(name = "temporary_code")
public class TemporaryCode {

    @Id
    @Column(name = "temporary_code_id")
    private int id;

    @Column(name = "temporary_code")
    private String code;

}
