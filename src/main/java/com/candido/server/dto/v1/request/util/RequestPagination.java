package com.candido.server.dto.v1.request.util;

import lombok.Data;

@Data
public class RequestPagination {
    private int page = 0;
    private int size = 10;
    private String sortBy = "createdAt";
    private String direction = "DESC";
}