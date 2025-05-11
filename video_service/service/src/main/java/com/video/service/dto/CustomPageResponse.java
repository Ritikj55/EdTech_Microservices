package com.video.service.dto;

import lombok.*;

import java.util.List;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CustomPageResponse<T> {
    private  int pageNumber;
    private int pageSize;
    private long totalElements;
    private  int totalPages;
    private boolean isLast;
    private List<T> content;
}
