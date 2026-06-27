package org.goros.posapi.model.request;

import lombok.Data;

@Data
public class AuthRequest {
    private String identifier;
    private String password;
}
