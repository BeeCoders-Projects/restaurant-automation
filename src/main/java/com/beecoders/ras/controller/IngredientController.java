package com.beecoders.ras.controller;

import com.beecoders.ras.model.response.IngredientInfo;
import com.beecoders.ras.service.IngredientService;
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

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/ingredients")
@Tag(name = "Ingredient", description = "Ingredient APIs documentation")
@SecurityScheme(
        name = "bearerAuth",
        scheme = "bearer",
        bearerFormat = "JWT",
        type = SecuritySchemeType.HTTP,
        in = SecuritySchemeIn.HEADER
)
public class IngredientController {
    private final IngredientService ingredientService;

    @SecurityRequirement(name = "bearerAuth")
    @Operation(
            summary = "Creating a ingredients",
            description = "I want to create ingredients.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Ingredients were created successfully"),
            @ApiResponse(responseCode = "400", description = "Incorrect data",
                    content = { @Content(schema = @Schema(implementation = String.class),
                            mediaType = "application/json") }),
            @ApiResponse(responseCode = "401", description = "Log in to get access to the page",
                    content = { @Content(schema = @Schema(implementation = String.class),
                            mediaType = "application/json") })})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody List<IngredientInfo> ingredients){
        ingredientService.saveAll(ingredients);
    }
}
