package com.beecoders.ras.controller.dish;

import com.beecoders.ras.model.request.dish.AddCategoryRequest;
import com.beecoders.ras.model.response.dish.CategoryResponse;
import com.beecoders.ras.service.dish.CategoryService;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
@Tag(name = "Category", description = "Category APIs documentation")
@SecurityScheme(
        name = "bearerAuth",
        scheme = "bearer",
        bearerFormat = "JWT",
        type = SecuritySchemeType.HTTP,
        in = SecuritySchemeIn.HEADER
)
public class CategoryController {
    private final CategoryService categoryService;

    @SecurityRequirement(name = "bearerAuth")
    @Operation(
            summary = "List of all categories",
            description = "Get the list of all categories with name and image")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Retrieve all categories",
                    content = { @Content(schema = @Schema(implementation = CategoryResponse.class),
                            mediaType = "application/json") }),
            @ApiResponse(responseCode = "401", description = "Log in to get access to the page",
                    content = { @Content(schema = @Schema(implementation = String.class),
                            mediaType = "application/json") })})
    @GetMapping
    public List<CategoryResponse> retrieveCategories() {
        return categoryService.getAllCategories();
    }

    @SecurityRequirement(name = "bearerAuth")
    @Operation(
            summary = "Creating a category",
            description = "I want to create a category for dishes.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "The category was created successfully"),
            @ApiResponse(responseCode = "400", description = "Incorrect category data or image file",
                    content = { @Content(schema = @Schema(implementation = String.class),
                            mediaType = "application/json") }),
            @ApiResponse(responseCode = "401", description = "Log in to get access to the page",
                    content = { @Content(schema = @Schema(implementation = String.class),
                            mediaType = "application/json") })})
    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CategoryResponse> save(@RequestPart AddCategoryRequest addCategoryRequest,
                                                 @RequestPart MultipartFile image){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(categoryService.save(addCategoryRequest, image));
    }
}