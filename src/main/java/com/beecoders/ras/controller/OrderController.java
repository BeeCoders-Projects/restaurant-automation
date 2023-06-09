package com.beecoders.ras.controller;

import com.beecoders.ras.model.request.AddOrderRequest;
import com.beecoders.ras.model.response.DishInfo;
import com.beecoders.ras.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
@Tag(name = "Order", description = "Order APIs documentation")
@SecurityScheme(
        name = "bearerAuth",
        scheme = "bearer",
        bearerFormat = "JWT",
        type = SecuritySchemeType.HTTP,
        in = SecuritySchemeIn.HEADER
)
public class OrderController {

    private final OrderService orderService;

    @SecurityRequirement(name = "bearerAuth")
    @Operation(
            summary = "Order creation",
            description = "As a table, I want to create an order consisting of selected dishes")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Create an order",
                    content = { @Content(schema = @Schema(implementation = Long.class),
                            mediaType = "application/json") }),
            @ApiResponse(responseCode = "401", description = "Log in to get access to the page",
                    content = { @Content(schema = @Schema(implementation = String.class),
                            mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", description = "Selected dishes not found",
                    content = { @Content(schema = @Schema(implementation = String.class),
                            mediaType = "application/json") })})
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Long save(@RequestBody AddOrderRequest addOrderRequest, Principal principal){
       return orderService.save(addOrderRequest, principal.getName());
    }

}