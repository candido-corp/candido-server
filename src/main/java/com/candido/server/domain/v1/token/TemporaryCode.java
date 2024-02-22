package com.candido.server.domain.v1.token;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "temporary_code")
public class TemporaryCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "temporary_code_id")
    private int id;

    @OneToOne
    @JoinColumn(name = "fk_token_id", insertable = false, updatable = false)
    private Token token;

    @Column(name = "fk_token_id")
    private Long tokenId;

    @Column(name = "temporary_code")
    private String code;

    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;

    public boolean isExpired() {
        return getExpirationDate().isBefore(LocalDateTime.now());
    }

}
