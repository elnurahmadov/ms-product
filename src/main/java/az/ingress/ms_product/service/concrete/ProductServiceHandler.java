package az.ingress.ms_product.service.concrete;

import az.ingress.ms_product.dao.entity.Product;
import az.ingress.ms_product.dao.entity.ProductImage;
import az.ingress.ms_product.dao.repository.ProductImageRepository;
import az.ingress.ms_product.dao.repository.ProductRepository;
import az.ingress.ms_product.model.request.ProductRequestDto;
import az.ingress.ms_product.model.response.ProductResponseDto;
import az.ingress.ms_product.service.abstraction.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static az.ingress.ms_product.model.enums.ProductStatus.PENDING;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceHandler implements ProductService {

    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;

    @Override
    public ProductResponseDto createProduct(ProductRequestDto request, UUID supplierId) {
        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setCategoryId(request.getCategoryId());
        product.setSupplierId(supplierId);
        product.setStatus(PENDING);
        product.setIsSponsored(false);

        productRepository.save(product);

        saveImages(product, request.getImageUrls());

        log.info("Product created: {}, supplierId: {}", product.getId(), supplierId);
        log.info("ActionLog.createProduct.info: param: {}, supplierId: {}", product.getId(), supplierId);
        return toResponseDto(product);
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

    private ProductResponseDto toResponseDto(Product product) {
        List<String> imageUrls = product.getImages().stream()
                .map(ProductImage::getUrl)
                .toList();

        return ProductResponseDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .status(product.getStatus())
                .supplierId(product.getSupplierId())
                .categoryId(product.getCategoryId())
                .isSponsored(product.getIsSponsored())
                .imageUrls(imageUrls)
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }
}
