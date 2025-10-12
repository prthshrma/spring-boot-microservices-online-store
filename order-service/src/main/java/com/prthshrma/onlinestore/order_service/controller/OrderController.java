package com.prthshrma.onlinestore.order_service.controller;

import java.util.concurrent.CompletableFuture;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.prthshrma.onlinestore.order_service.dto.OrderRequest;
import com.prthshrma.onlinestore.order_service.service.OrderService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CircuitBreaker(name = "inventory",fallbackMethod = "fallbackMethod")
    @TimeLimiter(name = "inventory")
    @Retry(name = "inventory")
    public CompletableFuture<String> placeOrder(@RequestBody OrderRequest OrderRequest){
        return CompletableFuture.supplyAsync(() -> orderService.placeOrder(OrderRequest) );
    }

    public CompletableFuture<String> fallbackMethod(OrderRequest orderRequest, RuntimeException runtimeException){
       return CompletableFuture.supplyAsync(() -> "Something went wrong! Order after sometime"); 
    } 

    // public String placeOrder(@RequestBody OrderRequest OrderRequest){
    //     orderService.placeOrder(OrderRequest);
    //     return "Order is placed";
    // }

    // public String fallbackMethod(OrderRequest orderRequest, RuntimeException runtimeException){
    //    return "Something went wrong! Order after sometime"; 
    // } 
}
