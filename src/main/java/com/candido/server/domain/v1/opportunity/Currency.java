package com.candido.server.domain.v1.opportunity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "currency", schema = "candido")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "currency_id")
    private Long currencyId;

    @Column(name = "code", unique = true, nullable = false, length = 3)
    private String code;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "symbol", length = 8)
    private String symbol;

    @Column(name = "enabled", nullable = false)
    private Boolean enabled = true;
}
