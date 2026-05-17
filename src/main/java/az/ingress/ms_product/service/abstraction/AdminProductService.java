package az.ingress.ms_product.service.abstraction;

import az.ingress.ms_product.model.response.ProductResponseDto;
import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface AdminProductService {
    @Nullable Page<ProductResponseDto> getPendingProducts(Pageable pageable);

    @Nullable ProductResponseDto verify(UUID id);

    @Nullable ProductResponseDto reject(UUID id);

    void deleteAllRejectedProducts();
}
