package org.goros.posapi.model.response;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.Instant;

@Data
@Builder
public class ApiResponseVoid {
    private Boolean success;
    private String message;
    private HttpStatus status;
    private Instant timestamp;
}
