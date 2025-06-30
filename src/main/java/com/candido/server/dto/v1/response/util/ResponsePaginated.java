package com.candido.server.dto.v1.response.util;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponsePaginated<T> {

    @JsonProperty("content")
    private List<T> content;

    @JsonProperty("page_number")
    private int pageNumber;

    @JsonProperty("size")
    private int size;

    @JsonProperty("total_pages")
    private int totalPages;

    @JsonProperty("total_elements")
    private long totalElements;

    @JsonProperty("first")
    private boolean first;

    @JsonProperty("last")
    private boolean last;

    @JsonProperty("empty")
    private boolean empty;

    @JsonProperty("number_of_elements")
    private int numberOfElements;

    @JsonProperty("sort")
    private SortResponse sort;

    @JsonProperty("pageable")
    private PageableResponse pageable;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SortResponse {
        private boolean sorted;
        private boolean unsorted;
        private boolean empty;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PageableResponse {
        private int pageNumber;
        private int pageSize;
        private long offset;
        private boolean paged;
        private boolean unpaged;
        private SortResponse sort;
    }

    public static <T> ResponsePaginated<T> fromPage(Page<T> page) {
        Sort sort = page.getPageable().getSort();
        SortResponse sortResponse = new SortResponse(
                sort.isSorted(),
                sort.isUnsorted(),
                sort.isEmpty()
        );

        Pageable pageable = page.getPageable();
        PageableResponse pageableResponse = new PageableResponse(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                pageable.getOffset(),
                pageable.isPaged(),
                pageable.isUnpaged(),
                sortResponse
        );

        return new ResponsePaginated<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalPages(),
                page.getTotalElements(),
                page.isFirst(),
                page.isLast(),
                page.isEmpty(),
                page.getNumberOfElements(),
                sortResponse,
                pageableResponse
        );
    }
}
