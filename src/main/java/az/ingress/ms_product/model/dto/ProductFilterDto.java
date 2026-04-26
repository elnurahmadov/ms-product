package az.ingress.ms_product.model.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductFilterDto {

    private String name;

    private BigDecimal minPrice;

    private BigDecimal maxPrice;

    private UUID categoryId;

    private String sortBy = "createdAt";

    private String sortDirection = "DESC";

    @Min(0)
    private int page = 0;

    @Min(1)
    @Max(50)
    private int size = 20;
}