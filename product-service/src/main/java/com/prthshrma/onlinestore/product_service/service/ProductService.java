package com.prthshrma.onlinestore.product_service.service;

import java.util.List;
import java.util.logging.Logger;

// import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prthshrma.onlinestore.product_service.dto.ProductRequest;
import com.prthshrma.onlinestore.product_service.dto.ProductResponse;
import com.prthshrma.onlinestore.product_service.model.Product;
import com.prthshrma.onlinestore.product_service.repository.ProductRepository;

@Service
@Transactional
public class ProductService {

    private static final Logger log = Logger.getLogger(ProductService.class.getName());

    private final ProductRepository productRepository;

    ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public void createProduct(ProductRequest productRequest){
        Product product = new Product();
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());

        //To save it to database, access the product repository
        productRepository.save(product);

        log.info("Products is saved..." + product.getId());
    }

    public List<ProductResponse> getAllProducts(){
        List<Product> products = productRepository.findAll();

        return products.stream().map(product -> mapToProductResponse(product)).toList();
    }

    public ProductResponse mapToProductResponse(Product product){
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(product.getId());
        productResponse.setDescription(product.getDescription());
        productResponse.setName(product.getName());
        productResponse.setPrice(product.getPrice());
;       return productResponse;
    }
}
