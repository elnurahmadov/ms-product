package az.ingress.ms_product.controller;

import az.ingress.ms_product.model.request.ProductRequestDto;
import az.ingress.ms_product.model.response.ProductResponseDto;
import az.ingress.ms_product.service.abstraction.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponseDto> createProduct(@RequestBody @Valid ProductRequestDto request,
                                                            @RequestHeader("X-User-Id") UUID supplierId) {
        return ResponseEntity.status(CREATED).body(productService.createProduct(request, supplierId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable UUID id,
                                                            @RequestBody @Valid ProductRequestDto request,
                                                            @RequestHeader("X-User-Id") UUID supplierId) {
        return ResponseEntity.ok(productService.updateProduct(id, request, supplierId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID id,
                                              @RequestHeader("X-User-Id") UUID supplierId) {
        productService.deleteProduct(id, supplierId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/my")
    public ResponseEntity<Page<ProductResponseDto>> getMyProducts(@RequestHeader("X-User-Id") UUID supplierId,
                                                                  Pageable pageable) {
        return ResponseEntity.ok(productService.getMyProducts(supplierId, pageable));
    }
}
