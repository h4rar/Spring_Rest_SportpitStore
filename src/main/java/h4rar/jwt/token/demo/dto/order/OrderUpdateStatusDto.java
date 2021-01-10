package h4rar.jwt.token.demo.dto.order;

import h4rar.jwt.token.demo.model.statuses.order.OrderStatus;
import lombok.Data;

@Data
public class OrderUpdateStatusDto {

    private Long id;

    private OrderStatus orderStatus;
}
