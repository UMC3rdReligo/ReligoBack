package com.umcreligo.umcback.domain.church.dto;

import lombok.Data;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class SignUpChurchMemberRequest {
    @Size(max = 30, message = "size must be between 0 and 30")
    private String name;

    @Past(message = "must be a date in the past or in the present")
    private LocalDate birthday;

    @Size(max = 30, message = "size must be between 0 and 30")
    private String phoneNum;

    @Size(max = 300, message = "size must be between 0 and 300")
    private String address;

    @Size(max = 300, message = "size must be between 0 and 300")
    private String email;

    @Size(max = 30, message = "size must be between 0 and 30")
    private String referee;

    @Size(max = 1000, message = "size must be between 0 and 1000")
    private String message;

    @FutureOrPresent(message = "must be a date in the present or in the future")
    private LocalDate scheduledDate;
}
