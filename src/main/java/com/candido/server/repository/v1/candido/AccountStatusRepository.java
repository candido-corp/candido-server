package com.candido.server.repository.v1.candido;

import com.candido.server.domain.v1.candido.AccountStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountStatusRepository extends JpaRepository<AccountStatus, Integer> {
}
