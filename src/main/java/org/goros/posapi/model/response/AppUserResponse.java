package org.goros.posapi.model.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
public class AppUserResponse {
    private String email;
    private String username;
    private String firstName;
    private String lastName;
}
