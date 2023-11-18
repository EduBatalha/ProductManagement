package com.example.controller;

import com.example.model.dto.ProductRequestDTO;
import com.example.model.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.service.ProductService;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/home")
public class ProductController {

    @Autowired
    private ProductService productService;

    @RequestMapping(method = RequestMethod.GET)
    public String home(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "home";
    }

    @RequestMapping(value = "/edit/{productHash}", method = RequestMethod.GET)
    public String editProduct(@PathVariable UUID productHash, Model model) {
        Product product = productService.getProductByHash(productHash);
        model.addAttribute("product", product);
        return "edit";
    }

    @GetMapping("/new")
    public String showProductForm(Model model) {
        model.addAttribute("productRequestDTO", new ProductRequestDTO());
        return "new";
    }

    @PostMapping("/save")
    public String saveProduct(@ModelAttribute("productRequestDTO") ProductRequestDTO productRequestDTO) {
        Product product = convertToEntity(productRequestDTO);
        productService.saveOrUpdateProduct(product);
        return "redirect:/home";
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
        return product;
    }
}

