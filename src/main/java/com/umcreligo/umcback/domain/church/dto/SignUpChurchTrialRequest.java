package com.umcreligo.umcback.domain.church.dto;

import lombok.Data;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class SignUpChurchTrialRequest {
    @Size(max = 30, message = "size must be between 0 and 30")
    private String name;

    @Size(max = 30, message = "size must be between 0 and 30")
    private String phoneNum;

    @Size(max = 1000, message = "size must be between 0 and 1000")
    private String message;

    @FutureOrPresent(message = "must be a date in the present or in the future")
    private LocalDate scheduledDate;
}
