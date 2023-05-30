package com.beecoders.ras.controller.dish;

import com.beecoders.ras.model.request.auth.AuthLogin;
import com.beecoders.ras.model.request.dish.ChangeSpecialDish;
import com.beecoders.ras.model.response.dish.DishDetailInfo;
import com.beecoders.ras.model.response.dish.DishInfo;
import com.beecoders.ras.service.dish.DishService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.beecoders.ras.model.constants.dish.CategoryConstant.ALL;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/dishes")
@Tag(name = "Dish", description = "Dish APIs documentation")
@SecurityScheme(
        name = "bearerAuth",
        scheme = "bearer",
        bearerFormat = "JWT",
        type = SecuritySchemeType.HTTP,
        in = SecuritySchemeIn.HEADER
)
public class DishController {
    private final DishService dishService;

    @SecurityRequirement(name = "bearerAuth")
    @Operation(
            summary = "Menu of dishes",
            description = "As a table, I want to show menu with all dishes or dishes by specific category.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Retrieve menu",
                    content = { @Content(schema = @Schema(implementation = DishInfo.class),
                            mediaType = "application/json") }),
            @ApiResponse(responseCode = "401", description = "Log in to get access to the page",
                    content = { @Content(schema = @Schema(implementation = String.class),
                            mediaType = "application/json") })})
    @GetMapping
    public List<DishInfo> retrieveDishMenu(@Parameter(description = "Category name of the dish")
                                               @RequestParam(required = false, defaultValue = ALL) String category) {
        return dishService.retrieveDishMenu(category);
    }

    @SecurityRequirement(name = "bearerAuth")
    @Operation(
            summary = "Detail information about the dish",
            description = "As a table, I want to show detail information of the dish.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Retrieve dish",
                    content = { @Content(schema = @Schema(implementation = DishDetailInfo.class),
                            mediaType = "application/json") }),
            @ApiResponse(responseCode = "401", description = "Log in to get access to the page",
                    content = { @Content(schema = @Schema(implementation = String.class),
                            mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", description = "Not found dish",
                    content = { @Content(schema = @Schema(implementation = String.class),
                            mediaType = "application/json") })})
    @GetMapping("/{dishId}")
    public DishDetailInfo retrieveDishById(@PathVariable Long dishId) {
        return dishService.retrieveDishById(dishId);
    }


    @SecurityRequirement(name = "bearerAuth")
    @Operation(
            summary = "Upload icon of dish",
            description = "I want to upload icon of dish.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Uploaded image successfully"),
            @ApiResponse(responseCode = "400", description = "Incorrect image file",
                    content = { @Content(schema = @Schema(implementation = String.class),
                    mediaType = "application/json") }),
            @ApiResponse(responseCode = "401", description = "Log in to get access to the page",
                    content = { @Content(schema = @Schema(implementation = String.class),
                            mediaType = "application/json") })})
    @PostMapping("/image/{id}/upload")
    @ResponseStatus(HttpStatus.OK)
    public void uploadImageToDish(@PathVariable Long id,
                            @Parameter(required = true, description = "Image file")
                            @RequestParam MultipartFile image){
        dishService.uploadImage(id, image);
    }

    @SecurityRequirement(name = "bearerAuth")
    @Operation(
            summary = "Set dish as special",
            description = "I want to set dish as special offer.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(schema = @Schema(implementation = ChangeSpecialDish.class),
                            mediaType = "application/json")))
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Changed dish status"),
            @ApiResponse(responseCode = "401", description = "Log in to get access to the page",
                    content = { @Content(schema = @Schema(implementation = String.class),
                            mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", description = "Not found dish",
                    content = { @Content(schema = @Schema(implementation = String.class),
                            mediaType = "application/json") })})
    @PutMapping("/special")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void setSpecialDish(@RequestBody ChangeSpecialDish request) {
        dishService.setSpecialDish(request);
    }
}
