package com.scaler.demoproject.controller;

import com.scaler.demoproject.dto.ErrorDto;
import com.scaler.demoproject.exceptions.ProductNotFoundException;
import com.scaler.demoproject.model.Product;
import com.scaler.demoproject.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductController {

//    public List<Product>Products = new ArrayList<>();

    private ProductService productService;

    @Autowired
    public ProductController(@Qualifier("fakeStoreProductService") ProductService productService) {
        this.productService = productService;
     }

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) throws ProductNotFoundException {
//        productService.createProduct();
        Product postRequestResponse = productService.createProduct(product);
        return new ResponseEntity<>(postRequestResponse, HttpStatus.CREATED);

    }
    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable("id") Long productId) throws ProductNotFoundException {
        Product currentProduct = productService.getSingleProduct(productId);

        if (currentProduct == null) {
            throw new ProductNotFoundException("Product not found with ID: " + productId);
        }

        return new ResponseEntity<>(currentProduct, HttpStatus.OK);
    }

//    @ControllerAdvice
//    public class GlobalExceptionHandler {
//
//        @ExceptionHandler(ProductNotFoundException.class)
//        public ResponseEntity<String> handleProductNotFound(ProductNotFoundException ex) {
//            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
//        }
//    }

    @GetMapping("/products")
    public Page<Product> getAllProducts(@RequestParam("pageSize") int pageSize, @RequestParam("pageNumber") int pageNumber, @RequestParam("sortBy") String fieldName) throws ProductNotFoundException {
       return productService.getAllProducts(pageSize, pageNumber, fieldName);
//        return ResponseEntity.ok(productService.getAllProducts());
    }

//      @ExceptionHandler(ProductNotFoundException.class)
//    public ResponseEntity<ErrorDto> handleProductNotFoundException(Exception e) {
//        ErrorDto errorDto = new ErrorDto();
//        errorDto.setMessage(e.getMessage());
//
//        return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
//    }
    @GetMapping("/health")
    public ResponseEntity<String> checkHealthOfTheService() {
        return new ResponseEntity<>("Backend application server is running perfectly fine", HttpStatus.OK);
    }
    @PutMapping("/products/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") Long id, @RequestBody Product product) throws ProductNotFoundException {
        Product res = productService.updateProduct(product);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
