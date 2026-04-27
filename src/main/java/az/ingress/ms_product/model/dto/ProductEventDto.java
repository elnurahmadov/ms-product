package az.ingress.ms_product.model.dto;

import az.ingress.ms_product.model.enums.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductEventDto {

    private UUID productId;
    private String productName;
    private UUID supplierId;
    private ProductStatus status;
}