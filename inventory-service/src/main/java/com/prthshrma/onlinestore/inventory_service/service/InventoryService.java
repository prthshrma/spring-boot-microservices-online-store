package com.prthshrma.onlinestore.inventory_service.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prthshrma.onlinestore.dto.InventoryResponse;
import com.prthshrma.onlinestore.inventory_service.repository.InventoryRepository;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository){
        this.inventoryRepository = inventoryRepository;
    }

    @Transactional(readOnly = true)
    public List<InventoryResponse> isInStock(List<String> skuCode){
        // return inventoryRepository.findBySkuCode(skuCode).isPresent();
        return inventoryRepository.findBySkuCodeIn(skuCode).stream()
        .map(inventory -> {
            InventoryResponse inventoryResponse = new InventoryResponse();
            inventoryResponse.setSkuCode(inventory.getSkuCode());
            int quantity = inventory.getQuantity();
            if(quantity>0)
                inventoryResponse.setInStock(true);
            else
                inventoryResponse.setInStock(false);

            return inventoryResponse;
        })
        .toList();
    }
}
