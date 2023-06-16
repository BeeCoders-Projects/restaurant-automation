package com.beecoders.ras.controller;

import com.beecoders.ras.model.request.AddOrderRequest;
import com.beecoders.ras.model.request.AddPromocode;
import com.beecoders.ras.model.request.PayOrder;
import com.beecoders.ras.model.response.OrderDetailInfo;
import com.beecoders.ras.model.response.PromocodeStatistic;
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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

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
            @ApiResponse(responseCode = "201", description = "Create an order",
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

    @SecurityRequirement(name = "bearerAuth")
    @Operation(
            summary = "Get order detail info",
            description = "As a table, I want to get order detail info by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Get order detail info",
                    content = { @Content(schema = @Schema(implementation = OrderDetailInfo.class),
                            mediaType = "application/json") }),
            @ApiResponse(responseCode = "401", description = "Log in to get access to the page",
                    content = { @Content(schema = @Schema(implementation = String.class),
                            mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", description = "Order not found",
                    content = { @Content(schema = @Schema(implementation = String.class),
                            mediaType = "application/json") })})
    @GetMapping("/{id}")
    public OrderDetailInfo getOrderDetailInfo(@PathVariable Long id){
        return orderService.getOrderDetailInfoById(id);
    }

    @SecurityRequirement(name = "bearerAuth")
    @Operation(
            summary = "Pay order",
            description = "As a table, I want to pay order")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Paid order with successful message",
                    content = { @Content(schema = @Schema(implementation = String.class),
                            mediaType = "application/json") }),
            @ApiResponse(responseCode = "401", description = "Log in to get access to the page",
                    content = { @Content(schema = @Schema(implementation = String.class),
                            mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", description = "Order not found",
                    content = { @Content(schema = @Schema(implementation = String.class),
                            mediaType = "application/json") }),
            @ApiResponse(responseCode = "409", description = "Illegal payment",
                    content = { @Content(schema = @Schema(implementation = String.class),
                            mediaType = "application/json") })})
    @PostMapping("/payment")
    public String payOrder(@Valid @RequestBody PayOrder request){
        return orderService.payOrder(request);
    }

    @SecurityRequirement(name = "bearerAuth")
    @Operation(
            summary = "Add promocode to order",
            description = "As a table, I want to add promocode with discount to order")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Add promocode to order successfully",
                    content = { @Content(schema = @Schema(implementation = String.class),
                            mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", description = "Incorrect data",
                    content = { @Content(schema = @Schema(implementation = String.class),
                            mediaType = "application/json") }),
            @ApiResponse(responseCode = "401", description = "Log in to get access to the page",
                    content = { @Content(schema = @Schema(implementation = String.class),
                            mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", description = "Order or Promocode not found",
                    content = { @Content(schema = @Schema(implementation = String.class),
                            mediaType = "application/json") })})
    @PostMapping("/promocode")
    public void addPromocodeToOrder(@Valid @RequestBody AddPromocode request){
        orderService.addPromocode(request);
    }

    @SecurityRequirement(name = "bearerAuth")
    @Operation(
            summary = "Get statistic of promocodes",
            description = "As a table, I want to get statistic of promocodes")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Get statistic of promocodes successfully",
                    content = { @Content(schema = @Schema(implementation = PromocodeStatistic.class),
                            mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", description = "Incorrect data",
                    content = { @Content(schema = @Schema(implementation = String.class),
                            mediaType = "application/json") }),
            @ApiResponse(responseCode = "401", description = "Log in to get access to the page",
                    content = { @Content(schema = @Schema(implementation = String.class),
                            mediaType = "application/json") })})
    @GetMapping ("/promocode/statistic")
    public List<PromocodeStatistic> getPromocodeStatistic(@RequestParam(required = false)
                                                          @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate from,
                                                          @RequestParam(required = false)
                                                          @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate to){
        return orderService.getPromocodeStatistic(from, to);
    }
}