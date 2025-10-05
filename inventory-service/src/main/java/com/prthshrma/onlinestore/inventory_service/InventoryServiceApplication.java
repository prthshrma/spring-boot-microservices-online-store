package com.prthshrma.onlinestore.inventory_service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.prthshrma.onlinestore.inventory_service.model.Inventory;
import com.prthshrma.onlinestore.inventory_service.repository.InventoryRepository;

@SpringBootApplication
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(InventoryRepository inventoryRepository){
		return args -> {
			Inventory i1 = new Inventory();
			i1.setSkuCode("iphone13");
			i1.setQuantity(10);

			Inventory i2 = new Inventory();
			i2.setSkuCode("iphone14");
			i2.setQuantity(0);

			inventoryRepository.save(i1);
			inventoryRepository.save(i2);
		};
	}

}
