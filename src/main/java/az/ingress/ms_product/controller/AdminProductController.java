package az.ingress.ms_product.controller;

import az.ingress.ms_product.model.response.ProductResponseDto;
import az.ingress.ms_product.service.abstraction.AdminProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/v1/admin/products")
@RequiredArgsConstructor
public class AdminProductController {

    private final AdminProductService adminProductService;

    @GetMapping("/pending")
    public ResponseEntity<Page<ProductResponseDto>> getPending(Pageable pageable) {
        return ResponseEntity.ok(adminProductService.getPendingProducts(pageable));
    }

    @PostMapping("/{id}/verify")
    public ResponseEntity<ProductResponseDto> verify(@PathVariable UUID id) {
        return ResponseEntity.ok(adminProductService.verify(id));
    }

    @PostMapping("/{id}/reject")
    public ResponseEntity<ProductResponseDto> reject(@PathVariable UUID id) {
        return ResponseEntity.ok(adminProductService.reject(id));
    }
}