package org.goros.posapi.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AppUserRequest {
    @Size(min=3, max=20, message="Username must be between 3 and 20 characters.")
    @Pattern(regexp = "^[a-zA-Z0-9](?:[a-zA-Z0-9._]{1,18}[a-zA-Z0-9])?$", message = "Username can contain letters, numbers, . and _, but cannot start or end with them")
    private String username;

    @Email(message = "Please enter a valid email address.")
    private String email;

    @Size(min=8, message="Password must be at least 8 characters.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "Password must contain uppercase, lowercase, number, and special character")
    private String password;

    @Size(min=2, message="First name must be at least 2 characters.")
    private String firstName;

    @Size(min=2, message="Last name must be at least 2 characters.")
    private String lastName;
}
