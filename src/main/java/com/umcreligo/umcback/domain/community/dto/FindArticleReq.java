package com.umcreligo.umcback.domain.community.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class FindArticleReq {

    private String platformCode;

    private Long churchId;

}
