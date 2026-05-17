package az.ingress.ms_product.mapper;

import az.ingress.ms_product.dao.entity.Product;
import az.ingress.ms_product.dao.entity.ProductImage;
import az.ingress.ms_product.model.request.ProductRequestDto;
import az.ingress.ms_product.model.response.ProductResponseDto;

import java.util.List;
import java.util.UUID;

import static az.ingress.ms_product.model.enums.ProductStatus.PENDING;

public enum ProductMapper {
    PRODUCT_MAPPER;

    public ProductResponseDto toResponseDto(Product product) {
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

    public Product toEntity(ProductRequestDto request, UUID supplierId) {
        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setCategoryId(request.getCategoryId());
        product.setSupplierId(supplierId);
        product.setStatus(PENDING);
        product.setIsSponsored(false);
        return product;
    }
}