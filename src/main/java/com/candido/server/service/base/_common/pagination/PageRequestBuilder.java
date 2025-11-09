package com.candido.server.service.base._common.pagination;

import com.candido.server.dto.v1.request.util.RequestPagination;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageRequestBuilder {
    public static Pageable from(RequestPagination request) {
        Sort.Direction direction = Sort.Direction.fromString(request.getDirection());
        return PageRequest.of(
                request.getPage(),
                request.getSize(),
                Sort.by(direction, request.getSortBy())
        );
    }
}
