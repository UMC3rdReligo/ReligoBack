package com.umcreligo.umcback.domain.church.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class FindRegistrationResult {
    private Long id;
    private Long userId;
    private Long churchId;
    private String name;
    private LocalDate birthday;
    private String phoneNum;
    private String address;
    private String email;
    private String referee;
    private String message;
    private LocalDateTime scheduledDateTime;
    private LocalDateTime createdAt;
}
