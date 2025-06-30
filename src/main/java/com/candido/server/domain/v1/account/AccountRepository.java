package com.candido.server.domain.v1.account;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    @EntityGraph(attributePaths = {"role.accountPermissions", "status", "authProviders"})
    Optional<Account> findByEmail(String email);

}
