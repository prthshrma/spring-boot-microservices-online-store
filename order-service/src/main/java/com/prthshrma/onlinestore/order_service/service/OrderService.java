package com.prthshrma.onlinestore.order_service.service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import com.prthshrma.onlinestore.order_service.dto.InventoryResponse;
import com.prthshrma.onlinestore.order_service.dto.OrderLineItemsDto;
import com.prthshrma.onlinestore.order_service.dto.OrderRequest;
import com.prthshrma.onlinestore.order_service.model.Order;
import com.prthshrma.onlinestore.order_service.model.OrderLineItems;
import com.prthshrma.onlinestore.order_service.repository.OrderRepository;

@Service
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;

    private final WebClient webClient;

    //Dependency Injection
    public OrderService(OrderRepository orderRepository, WebClient webClient){
        this.orderRepository = orderRepository;
        this.webClient = webClient;
    }

    public void placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = 
                    orderRequest.getOrderLineItemsDto()
                    .stream().map((itemDto)->getOrderItemList(itemDto)).toList();

        order.setOrderLineItemsList(orderLineItems);

        List<String> skuCodes = order.getOrderLineItemsList().stream().map(orderLineItem -> {
            return orderLineItem.getSkuCode(); 
        })
        .toList();

        //Check if the items are in stock?
        InventoryResponse[] inventoryResponses = webClient.get().uri("http://localhost:8082/api/inventory", 
                uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

        Boolean isAllInStock = Arrays.stream(inventoryResponses)
                                .allMatch(inventoryResponse -> inventoryResponse.isInStock());


        if(isAllInStock)
            orderRepository.save(order);
        else{
            throw new IllegalArgumentException("Products is out of stock");
        }
    }

    public OrderLineItems getOrderItemList(OrderLineItemsDto orderLineItemsDto){
        OrderLineItems orderLineItems = new OrderLineItems();
        // orderLineItems.setId(orderLineItemsDto.getId());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());

        return orderLineItems;
    }
}
