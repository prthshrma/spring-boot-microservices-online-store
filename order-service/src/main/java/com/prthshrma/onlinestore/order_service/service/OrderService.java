package com.prthshrma.onlinestore.order_service.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prthshrma.onlinestore.order_service.dto.OrderLineItemsDto;
import com.prthshrma.onlinestore.order_service.dto.OrderRequest;
import com.prthshrma.onlinestore.order_service.model.Order;
import com.prthshrma.onlinestore.order_service.model.OrderLineItems;
import com.prthshrma.onlinestore.order_service.repository.OrderRepository;

@Service
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;

    //Dependency Injection
    public OrderService(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }


    public void placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = 
                    orderRequest.getOrderLineItemsDto()
                    .stream().map((itemDto)->getOrderItemList(itemDto)).toList();

        order.setOrderLineItemsList(orderLineItems);

        orderRepository.save(order);
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
