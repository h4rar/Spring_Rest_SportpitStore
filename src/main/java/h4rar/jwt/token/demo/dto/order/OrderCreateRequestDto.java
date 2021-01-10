package h4rar.jwt.token.demo.dto.order;

import h4rar.jwt.token.demo.model.statuses.order.*;
import lombok.Data;

import java.util.Map;

@Data
public class OrderCreateRequestDto {

    private Map<Long, Integer> mapProducts;

    private Long addressId;

    private VersionDelivery delivery;

    private PaymentMethod paymentMethod;
}
