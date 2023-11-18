package com.example.service;




import com.example.model.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.repo.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRep;

    public List<Product> getAllProducts() {

        return new ArrayList<>(productRep.findAll());
    }

    public Product getProductByHash(UUID hash) {
        return productRep.findByHash(hash);
    }

    public void saveOrUpdateProduct(Product product) {
        Product updatedProduct = productRep.save(product);
        productRep.findByHash(updatedProduct.getHash());
    }

    public boolean deleteProduct (UUID hash) {
        productRep.deleteByHash(hash);

        return productRep.findByHash(hash) != null;
    }

}
