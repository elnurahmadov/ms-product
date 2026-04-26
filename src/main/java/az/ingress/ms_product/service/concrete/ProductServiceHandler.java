package az.ingress.ms_product.service.concrete;

import az.ingress.ms_product.model.request.ProductRequestDto;
import az.ingress.ms_product.model.response.ProductResponseDto;
import az.ingress.ms_product.service.abstraction.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceHandler implements ProductService {
    @Override
    public ProductResponseDto createProduct(ProductRequestDto request, UUID supplierId) {
        return null;
    }

    @Override
    public ProductResponseDto updateProduct(UUID productId, ProductRequestDto request, UUID supplierId) {
        return null;
    }

    @Override
    public void deleteProduct(UUID productId, UUID supplierId) {

    }

    @Override
    public Page<ProductResponseDto> getMyProducts(UUID supplierId, Pageable pageable) {
        return null;
    }
}
