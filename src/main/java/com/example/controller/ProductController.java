    package com.example.controller;

    import com.example.model.dto.ProductRequestDTO;
    import com.example.model.entity.Product;
    import com.example.service.ProductValidationException;
    import jakarta.validation.ConstraintViolationException;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.dao.DataIntegrityViolationException;
    import org.springframework.web.servlet.mvc.support.RedirectAttributes;
    import org.springframework.http.ResponseEntity;
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import com.example.service.ProductService;
    import org.springframework.web.bind.annotation.*;

    import java.util.List;
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
            return "index";
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

        @DeleteMapping("/delete/{productHash}")
        public String deleteProduct(@PathVariable UUID productHash) {
            boolean deleted = productService.deleteProduct(productHash);

            return "redirect:/products";
        }


        @GetMapping("/list")
        @ResponseBody
        public List<Product> getAllProducts() {
            return productService.getAllProducts();
        }


        @PostMapping("/save")
        public String saveProduct(@ModelAttribute("productRequestDTO") ProductRequestDTO productRequestDTO, RedirectAttributes redirectAttributes) {
            try {
                // Converte o objeto DTO para a entidade correspondente
                Product product = convertToEntity(productRequestDTO);

                // Chama o serviço para salvar o produto
                productService.saveProduct(product);

                // Redireciona para a página de produtos após o salvamento bem-sucedido
                return "redirect:/products";
            } catch (DataIntegrityViolationException e) {
                // Manipule a exceção de violação de integridade (por exemplo, duplicidade de valores únicos)
                redirectAttributes.addFlashAttribute("error", "Erro de integridade ao salvar o produto. <br>O produto não pode ter nome ou EAN repetido/");
                return "redirect:/products";
            } catch (ProductValidationException e) {
                // Captura a exceção específica para validação de produto
                redirectAttributes.addFlashAttribute("error", e.getMessage());
                return "redirect:/products";
            } catch (Exception e) {
                // Trate outras exceções, se necessário
                redirectAttributes.addFlashAttribute("error", "Erro ao salvar o produto.");
                return "redirect:/products";
            }
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

