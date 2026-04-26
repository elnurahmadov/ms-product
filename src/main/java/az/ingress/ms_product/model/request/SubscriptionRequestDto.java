package az.ingress.ms_product.model.request;

import az.ingress.ms_product.model.enums.SubscriptionType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionRequestDto {

    @NotNull
    private UUID productId;

    @NotNull
    private SubscriptionType type;
}