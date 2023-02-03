package com.umcreligo.umcback.domain.church.dto;

import com.umcreligo.umcback.global.config.validation.NullOrNotBlank;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class UpdateChurchRequest {
    @NullOrNotBlank
    private String platformCode;

    @NullOrNotBlank
    private String locationCode;

    @NullOrNotBlank
    @Size(max = 30, message = "size must be between 0 and 30")
    private String name;

    @NullOrNotBlank
    @Size(max = 300, message = "size must be between 0 and 300")
    private String address;

    @Size(max = 300, message = "size must be between 0 and 300")
    private String homepageURL;

    @NullOrNotBlank
    @Size(max = 1000, message = "size must be between 0 and 1000")
    private String introduction;

    @NullOrNotBlank
    @Size(max = 30, message = "size must be between 0 and 30")
    private String minister;

    @NullOrNotBlank
    @Size(max = 1000, message = "size must be between 0 and 1000")
    private String schedule;

    @NullOrNotBlank
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
