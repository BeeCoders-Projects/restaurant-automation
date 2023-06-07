package com.beecoders.ras.controller;

import com.beecoders.ras.model.request.TableStatusChange;
import com.beecoders.ras.service.RestaurantTableService;
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
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tables")
@Tag(name = "Restaurant table", description = "Restaurant table APIs documentation")
@SecurityScheme(
        name = "bearerAuth",
        scheme = "bearer",
        bearerFormat = "JWT",
        type = SecuritySchemeType.HTTP,
        in = SecuritySchemeIn.HEADER
)
public class RestaurantTableController {
    private final RestaurantTableService restaurantTableService;

    @SecurityRequirement(name = "bearerAuth")
    @Operation(
            summary = "Changing table status",
            description = "I want to change status of table.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Table status was changed successfully"),
            @ApiResponse(responseCode = "400", description = "Incorrect data",
                    content = { @Content(schema = @Schema(implementation = String.class),
                            mediaType = "application/json") }),
            @ApiResponse(responseCode = "401", description = "Log in to get access to the page",
                    content = { @Content(schema = @Schema(implementation = String.class),
                            mediaType = "application/json") }),
            @ApiResponse(responseCode = "403", description = "Access denied to changing status of table",
                    content = { @Content(schema = @Schema(implementation = String.class),
                            mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", description = "Table or status of table does not exist",
                    content = { @Content(schema = @Schema(implementation = String.class),
                            mediaType = "application/json") })})
    @PatchMapping("/status")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateTableStatus(@RequestBody TableStatusChange request) {
        restaurantTableService.updateStatus(request);
    }
}
