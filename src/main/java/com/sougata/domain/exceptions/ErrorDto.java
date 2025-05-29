package com.sougata.domain.exceptions;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDto {
    private String message;
    private HttpStatus status;
    private String path;
    private Timestamp timestamp;
}
