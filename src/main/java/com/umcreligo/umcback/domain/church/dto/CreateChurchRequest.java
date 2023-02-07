package com.umcreligo.umcback.domain.church.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class CreateChurchRequest {
    @NotBlank(message = "must not be blank")
    private String platformCode;

    @NotBlank(message = "must not be blank")
    private String locationCode;

    @NotBlank(message = "must not be blank")
    @Size(max = 30, message = "size must be between 0 and 30")
    private String name;

    @NotBlank(message = "must not be blank")
    @Size(max = 300, message = "size must be between 0 and 300")
    private String address;

    @Size(max = 300, message = "size must be between 0 and 300")
    private String homepageURL;

    @NotBlank(message = "must not be blank")
    @Size(max = 1000, message = "size must be between 0 and 1000")
    private String introduction;

    @Size(max = 30, message = "size must be between 0 and 30")
    private String minister;

    @Size(max = 1000, message = "size must be between 0 and 1000")
    private String schedule;

    @NotBlank(message = "must not be blank")
    @Size(max = 30, message = "size must be between 0 and 30")
    private String phoneNum;

    @Size(max = 10, message = "size must be between 0 and 10")
    private List<@NotBlank(message = "must not be blank") String> hashTags;

    @Size(max = 300, message = "size must be between 0 and 300")
    private String mainImage;

    @Size(max = 10, message = "size must be between 0 and 10")
    private List<
        @NotBlank(message = "must not be blank")
        @Size(max = 300, message = "size must be between 0 and 300") String> detailImages;
}
