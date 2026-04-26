package az.ingress.ms_product.model.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PromoCodeRequestDto {

    @NotBlank
    private String code;

    @NotNull
    @DecimalMin("0.01")
    @DecimalMax("100.00")
    private BigDecimal discountPercent;

    @NotNull
    @Future
    private LocalDate expireDate;
}