package com.springboot.restful.product;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.restful.ResponseDTO;

@RestController
@RequestMapping("/api/v1/products")
@Validated
public class ProductAPI {
    private final ProductService productService;

    @Autowired
    public ProductAPI(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<ResponseDTO> findAll() {
        ResponseDTO responseDTO = ResponseDTO.builder()
            .status(HttpStatus.OK.toString())
            .body(productService.findAll()).build();

        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO> findById(@PathVariable @ProductIDExisting Long id) {
        ResponseDTO responseDTO = ResponseDTO.builder()
            .status(HttpStatus.OK.toString())
            .body(productService.findById(id)).build();

        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> create(@RequestBody Product product) {
        ResponseDTO responseDTO = ResponseDTO.builder()
            .status(HttpStatus.CREATED.toString())
            .body(productService.save(product)).build();

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO> update(@PathVariable Long id, @RequestBody @Valid Product product) {
        ResponseDTO responseDTO = ResponseDTO.builder()
            .status(HttpStatus.ACCEPTED.toString())
            .body(productService.save(product)).build();

        return ResponseEntity.accepted().body(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> delete(@PathVariable @ProductIDExisting Long id) {
        productService.deleteById(id);

        ResponseDTO responseDTO = ResponseDTO.builder()
            .status(HttpStatus.ACCEPTED.toString()).build();

        return ResponseEntity.accepted().body(responseDTO);
    }
}