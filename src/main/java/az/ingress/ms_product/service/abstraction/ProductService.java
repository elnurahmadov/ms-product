package az.ingress.ms_product.service.abstraction;

import az.ingress.ms_product.model.request.ProductRequestDto;
import az.ingress.ms_product.model.response.ProductResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ProductService {

    ProductResponseDto createProduct(ProductRequestDto request, UUID supplierId);

    ProductResponseDto updateProduct(UUID productId, ProductRequestDto request, UUID supplierId);

    void deleteProduct(UUID productId, UUID supplierId);

    Page<ProductResponseDto> getMyProducts(UUID supplierId, Pageable pageable);
}
