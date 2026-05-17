package az.ingress.ms_product.service.concrete;

import az.ingress.ms_product.dao.entity.Product;
import az.ingress.ms_product.dao.repository.ProductRepository;
import az.ingress.ms_product.exception.NotFoundException;
import az.ingress.ms_product.mapper.ProductMapper;
import az.ingress.ms_product.model.response.ProductResponseDto;
import az.ingress.ms_product.publisher.ProductEventPublisher;
import az.ingress.ms_product.service.abstraction.AdminProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static az.ingress.ms_product.exception.ExceptionConstants.PRODUCT_NOT_FOUND_CODE;
import static az.ingress.ms_product.exception.ExceptionConstants.PRODUCT_NOT_FOUND_MESSAGE;
import static az.ingress.ms_product.model.enums.ProductStatus.APPROVED;
import static az.ingress.ms_product.model.enums.ProductStatus.PENDING;
import static az.ingress.ms_product.model.enums.ProductStatus.REJECTED;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminProductServiceHandler implements AdminProductService {

    private final ProductRepository productRepository;
    private final ProductEventPublisher productEventPublisher;
    private final ProductMapper productMapper;

    @Override
    public Page<ProductResponseDto> getPendingProducts(Pageable pageable) {
        return productRepository.findAllByStatus(PENDING, pageable)
                .map(productMapper::toResponseDto);
    }

    @Override
    public ProductResponseDto verify(UUID productId) {
        Product product = getProductById(productId);
        product.setStatus(APPROVED);
        productRepository.save(product);

        productEventPublisher.publishProductApproved(product);

        log.info("Product approved: {}", productId);
        return productMapper.toResponseDto(product);
    }

    @Override
    public ProductResponseDto reject(UUID productId) {
        Product product = getProductById(productId);
        product.setStatus(REJECTED);
        productRepository.save(product);

        productEventPublisher.publishProductRejected(product);

        log.info("Product rejected: {}", productId);
        return productMapper.toResponseDto(product);
    }

    private Product getProductById(UUID productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> {
                    log.error("ActionLog.getProductById.error: productId={}", productId);
                    return new NotFoundException(
                            PRODUCT_NOT_FOUND_MESSAGE.formatted(productId),
                            PRODUCT_NOT_FOUND_CODE
                    );
                });
    }
}
