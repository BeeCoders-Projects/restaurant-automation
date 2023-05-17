package com.beecoders.ras.model.request.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthLogin {
    @NotBlank(message = "Username should not be blank")
    @Size(min = 1, max = 50, message = "Username must be greater than 1 and less than 50 characters")
    private String username;
    @NotBlank(message = "Password should not be blank")
    @Size(min = 8, max = 40, message = "Password must be greater than 8 and less than 40 characters")
    private String password;
}
