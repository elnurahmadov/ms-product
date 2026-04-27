package az.ingress.ms_product.mapper;

import az.ingress.ms_product.dao.entity.Product;
import az.ingress.ms_product.dao.entity.ProductImage;
import az.ingress.ms_product.model.response.ProductResponseDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductMapper {

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
}