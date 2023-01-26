package com.umcreligo.umcback.domain.church.dto;

import com.umcreligo.umcback.domain.church.domain.Church;
import lombok.Data;

import java.util.List;

@Data
public class FindChurchResult {
    private Church info;
    private List<String> hashTags;
    private String mainImage;
    private List<String> detailImages;
}
