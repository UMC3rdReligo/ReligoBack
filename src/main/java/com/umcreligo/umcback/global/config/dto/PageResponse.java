package com.umcreligo.umcback.global.config.dto;

import lombok.Data;

@Data
public class PageResponse {
    int page;
    int size;
    int limit;
    int totalPage;
    long totalSize;
}
