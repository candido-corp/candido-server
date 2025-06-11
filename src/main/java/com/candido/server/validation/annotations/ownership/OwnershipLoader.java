package com.candido.server.validation.annotations.ownership;

import com.candido.server.domain.v1._common.Ownable;

public interface OwnershipLoader<T extends Ownable> {
    T loadById(Integer id);
}
