package com.beecoders.ras.controller.auth;

import com.beecoders.ras.model.request.auth.AuthLogin;
import com.beecoders.ras.security.jwt.constant.JwtTokenConstant;
import com.beecoders.ras.service.auth.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Auth APIs documentation")
public class AuthController {
    private final AuthService authService;

    @Operation(
            summary = "Guest log in",
            description = "As a guest, I want to log into the system",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(schema = @Schema(implementation = AuthLogin.class),
                            mediaType = "application/json")))
    @ApiResponses({ @ApiResponse(responseCode = "200",  headers={
            @Header(name="Access token", description="Jwt-token",
                    schema = @Schema(implementation = String.class))},
            content = { @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", description = "Incorrect data",
                    content = { @Content(schema = @Schema(implementation = HashMap.class),
                            mediaType = "application/json") }),
            @ApiResponse(responseCode = "401", description = "Incorrect credentials",
                    content = { @Content(schema = @Schema(implementation = HashMap.class),
                            mediaType = "application/json") })})
    @PostMapping("/login")
    public HttpHeaders login(@Valid @RequestBody AuthLogin authLogin) {
        String jwtToken = authService.login(authLogin);
        HttpHeaders headers = new HttpHeaders();

        headers.add(JwtTokenConstant.JWT_ACCESS_TOKEN, jwtToken);

        return headers;
    }
}
