    package com.example.controller;

    import com.example.model.dto.ProductRequestDTO;
    import com.example.model.entity.Product;
    import jakarta.validation.Valid;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.ResponseEntity;
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.validation.BindingResult;
    import com.example.model.dto.ProductRequestDTO;
    import com.example.model.entity.Product;
    import com.example.service.ProductService;
    import org.springframework.web.bind.annotation.*;
    import com.example.service.ProductService;

    import java.util.List;
    import java.util.Objects;
    import java.util.UUID;

    @Controller
    @RequestMapping("/products")
    public class ProductController {

        @Autowired
        private ProductService productService;

        @GetMapping
        public String home(Model model) {
            List<Product> products = productService.getAllProducts();
            model.addAttribute("products", products);
            return "home";
        }

        @PostMapping("/update/{productHash}")
        public String updateProduct(
                @PathVariable UUID productHash,
                @ModelAttribute ProductRequestDTO updatedProductData) {
            System.out.println("Update Product Controller - Hash: " + productHash);
            System.out.println("Updated Product Data: " + updatedProductData.toString());

            ResponseEntity<String> response = productService.updateProduct(productHash, updatedProductData);

            System.out.println("Response from Service: " + response.toString());

            // Verifique se a atualização foi bem-sucedida antes de redirecionar
            if (response.getStatusCode().is2xxSuccessful()) {
                return "redirect:/products";
            } else {
                // Se a atualização falhou, você pode querer tratar isso de alguma forma específica,
                // como exibindo uma mensagem de erro
                return "redirect:/error";
            }
        }




        @GetMapping("/list")
        @ResponseBody
        public List<Product> getAllProducts() {
            return productService.getAllProducts();
        }

        @PostMapping("/save")
        public String saveProduct(@ModelAttribute("productRequestDTO") ProductRequestDTO productRequestDTO) {
            Product product = convertToEntity(productRequestDTO);
            productService.saveProduct(product);
            return "redirect:/products";
        }



        private Product convertToEntity(ProductRequestDTO productRequestDTO) {
            Product product = new Product();
            product.setName(productRequestDTO.getName());
            product.setDescription(productRequestDTO.getDescription());
            product.setEan13(productRequestDTO.getEan13());
            product.setActive(productRequestDTO.isActive());
            product.setMinStock(productRequestDTO.getMinStock());
            product.setPrice(productRequestDTO.getPrice());
            product.setQuantity(productRequestDTO.getQuantity());
            // Pode definir outras propriedades conforme necessário
            return product;
        }

    }

