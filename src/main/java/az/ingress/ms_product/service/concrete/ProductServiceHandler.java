package az.ingress.ms_product.service.concrete;

import az.ingress.ms_product.dao.entity.Product;
import az.ingress.ms_product.dao.entity.ProductImage;
import az.ingress.ms_product.dao.repository.ProductImageRepository;
import az.ingress.ms_product.dao.repository.ProductRepository;
import az.ingress.ms_product.exception.NotFoundException;
import az.ingress.ms_product.mapper.ProductMapper;
import az.ingress.ms_product.model.dto.ProductFilterDto;
import az.ingress.ms_product.model.request.ProductRequestDto;
import az.ingress.ms_product.model.response.ProductResponseDto;
import az.ingress.ms_product.publisher.ProductEventPublisher;
import az.ingress.ms_product.service.abstraction.ProductService;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static az.ingress.ms_product.exception.ExceptionConstants.PRODUCT_NOT_FOUND_CODE;
import static az.ingress.ms_product.exception.ExceptionConstants.PRODUCT_NOT_FOUND_MESSAGE;
import static az.ingress.ms_product.model.enums.ProductStatus.APPROVED;
import static az.ingress.ms_product.model.enums.ProductStatus.PENDING;
import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceHandler implements ProductService {

    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;
    private final ProductMapper productMapper;
    private final ProductEventPublisher productEventPublisher;

    @Transactional
    @Override
    public ProductResponseDto createProduct(ProductRequestDto request, UUID supplierId) {
        Product product = productMapper.toEntity(request, supplierId);

        productRepository.save(product);
        saveImages(product, request.getImageUrls());
        productEventPublisher.publishProductCreated(product);

        log.info("ActionLog.createProduct.info: created: {}, supplierId: {}", product.getId(), supplierId);
        return productMapper.toResponseDto(product);
    }

    @Transactional
    @Override
    public ProductResponseDto updateProduct(UUID productId, ProductRequestDto request, UUID supplierId) {
        Product product = getProductByIdAndSupplierId(productId, supplierId);

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setCategoryId(request.getCategoryId());
        product.setStatus(PENDING);

        product.getImages().clear();
        saveImages(product, request.getImageUrls());

        productRepository.save(product);

        log.info("ActionLog.updateProduct.info: updated: {}, supplierId: {}", productId, supplierId);
        return productMapper.toResponseDto(product);
    }

    @Transactional
    @Override
    public void deleteProduct(UUID productId, UUID supplierId) {
        Product product = getProductByIdAndSupplierId(productId, supplierId);
        productRepository.delete(product);
        log.info("ActionLog.deleteProduct.info: deleted: {}, supplierId: {}", productId, supplierId);
    }

    @Override
    public Page<ProductResponseDto> getMyProducts(UUID supplierId, Pageable pageable) {
        return productRepository.findAllBySupplierId(supplierId, pageable)
                .map(productMapper::toResponseDto);
    }

    @Override
    public Page<ProductResponseDto> getAll(ProductFilterDto filter) {
        Pageable pageable = buildPageable(filter);
        Specification<Product> spec = buildSpecification(filter);
        return productRepository.findAll(spec, pageable)
                .map(productMapper::toResponseDto);
    }

    @Override
    public ProductResponseDto getById(UUID productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> {
                    log.error("ActionLog.getById.error: productId={}", productId);
                    return new NotFoundException(
                            PRODUCT_NOT_FOUND_MESSAGE.formatted(productId),
                            PRODUCT_NOT_FOUND_CODE
                    );
                });
        return productMapper.toResponseDto(product);
    }

    private void saveImages(Product product, List<String> imageUrls) {
        if (imageUrls == null || imageUrls.isEmpty()) return;

        List<ProductImage> productImageList = new ArrayList<>();
        for (int i = 0; i < imageUrls.size(); i++) {
            ProductImage productImage = new ProductImage();
            productImage.setProduct(product);
            productImage.setUrl(imageUrls.get(i));
            productImage.setIsMain(i == 0);
            productImageList.add(productImage);
        }
        productImageRepository.saveAll(productImageList);
        product.getImages().addAll(productImageList);
    }

    private Product getProductByIdAndSupplierId(UUID productId, UUID supplierId) {
        return productRepository.findByIdAndSupplierId(productId, supplierId)
                .orElseThrow(() -> {
                    log.error("ActionLog.getProductByIdAndSupplierId.error: productId={}, supplierId={}", productId, supplierId);
                    return new NotFoundException(
                            PRODUCT_NOT_FOUND_MESSAGE.formatted(productId),
                            PRODUCT_NOT_FOUND_CODE
                    );
                });
    }

    private Pageable buildPageable(ProductFilterDto filter) {
        Sort.Direction direction = filter.getSortDirection().equalsIgnoreCase("ASC")
                ? ASC
                : DESC;

        Sort sort = Sort.by(direction, filter.getSortBy());

        return PageRequest.of(filter.getPage(), filter.getSize(), sort);
    }

    private Specification<Product> buildSpecification(ProductFilterDto filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(cb.equal(root.get("status"), APPROVED));

            if (filter.getName() != null && !filter.getName().isBlank()) {
                predicates.add(cb.like(
                        cb.lower(root.get("name")),
                        "%" + filter.getName().toLowerCase() + "%"
                ));
            }

            if (filter.getCategoryId() != null) {
                predicates.add(cb.equal(root.get("categoryId"), filter.getCategoryId()));
            }

            if (filter.getMinPrice() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("price"), filter.getMinPrice()));
            }

            if (filter.getMaxPrice() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("price"), filter.getMaxPrice()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
