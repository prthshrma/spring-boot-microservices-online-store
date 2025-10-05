package com.prthshrma.onlinestore.inventory_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.prthshrma.onlinestore.inventory_service.service.InventoryService;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    InventoryController(InventoryService inventoryService){
        this.inventoryService = inventoryService;
    }

    @GetMapping("/{sku_code}")
    @ResponseStatus(HttpStatus.OK)
    public boolean isInStock(@PathVariable("sku_code") String skuCode){ //To check if the item(skucode) is in stock?
        return inventoryService.isInStock(skuCode);
    }
}
