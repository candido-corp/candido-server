package com.candido.server.domain.v1.token;

import com.candido.server.domain.v1.account.Account;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "token")
@SQLRestriction("valid=true")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fk_account_id")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "fk_token_type_id")
    private TokenType tokenType;

    @ManyToOne
    @JoinColumn(name = "fk_token_scope_category_id")
    private TokenScopeCategory tokenScopeCategory;

    @Column(name = "access_token")
    private String accessToken;

    @Column(name = "access_token_expiration")
    private LocalDateTime accessTokenExpiration;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "refresh_token_expiration")
    private LocalDateTime refreshTokenExpiration;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "uuid_access_token")
    private String uuidAccessToken;

    @Column(name = "valid")
    private boolean valid;

    public boolean isAccessTokenExpired() {
        return getAccessTokenExpiration().isBefore(LocalDateTime.now());
    }

    public boolean isRefreshTokenExpired() {
        return getRefreshTokenExpiration().isBefore(LocalDateTime.now());
    }

}
