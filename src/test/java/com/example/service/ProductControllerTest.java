package com.example.service;

import com.example.model.dto.ProductRequestDTO;
import com.example.model.entity.Product;
import com.example.repo.ProductRepository;
import com.example.service.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

@SpringBootTest
class ProductControllerTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;
    private Product product;

    @Test
    void testSaveProduct() {
        // Criar um objeto ProductRequestDTO para simular os dados do formulário
        ProductRequestDTO productRequestDTO = new ProductRequestDTO();
        productRequestDTO.setName("Test Product");
        // Adicionar outros atributos conforme necessário

        // Criar um objeto Product simulado após a conversão
        Product simulatedProduct = new Product();
        simulatedProduct.setId(1); // Simulando um ID atribuído pelo banco
        simulatedProduct.setName(productRequestDTO.getName());
        // Adicionar outros atributos conforme necessário

        // Simular o comportamento do repository ao salvar o produto
        when(productRepository.save(any(Product.class))).thenReturn(simulatedProduct);

        // Chamar o método de serviço para salvar o produto
        productService.saveOrUpdateProduct(product);

        // Verificar se o método save do repository foi chamado
        verify(productRepository, times(1)).save(any(Product.class));
    }
}

