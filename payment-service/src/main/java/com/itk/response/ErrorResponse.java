package com.itk.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private String path;
    private LocalDateTime timeStamp;
    private String message;
    private String statusCode;
}
