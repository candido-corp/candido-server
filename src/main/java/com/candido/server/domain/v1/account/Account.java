package com.candido.server.domain.v1.account;

import com.candido.server.domain.v1.provider.AuthProvider;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "account")
public class Account implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "fk_account_role_id")
    private AccountRole role;

    @ManyToOne
    @JoinColumn(name = "fk_account_status_id")
    private AccountStatus status;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @JsonProperty("created_at")
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @JsonProperty("deleted_at")
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "xref_account_provider",
            joinColumns = @JoinColumn(name = "fk_account_id"),
            inverseJoinColumns = @JoinColumn(name = "fk_provider_id")
    )
    Set<AuthProvider> authProviders;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        var permissions = role.getAccountPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getDescription()))
                .toList();

        var roles = new ArrayList<GrantedAuthority>();
        roles.add(new SimpleGrantedAuthority("ROLE_" + role.getDescription()));

        var combined = new ArrayList<GrantedAuthority>();
        combined.addAll(permissions);
        combined.addAll(roles);

        return combined;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return deletedAt == null
                && status != null
                && status.getId() != AccountStatusEnum.DISABLED.getStatusId();
    }

    public List<String> getRoles() {
        return Collections.singletonList("ROLE_" + role.getDescription());
    }

    public List<String> getPermissions() {
        return role.getAccountPermissions()
                .stream()
                .map(AccountPermission::getDescription)
                .collect(Collectors.toList());
    }

}
