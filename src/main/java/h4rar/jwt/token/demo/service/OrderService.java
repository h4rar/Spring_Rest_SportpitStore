package h4rar.jwt.token.demo.service;

import h4rar.jwt.token.demo.dto.order.*;
import h4rar.jwt.token.demo.model.statuses.order.OrderStatus;
import org.springframework.data.domain.*;

import javax.servlet.http.HttpServletRequest;

public interface OrderService {

    void createNewOrder(HttpServletRequest req, OrderCreateRequestDto dto);
    Page<OrderResponseDto> getAllOrdersAdmin(Pageable pageable);
    Page<OrderResponseDto> getAllOrders(HttpServletRequest req, Pageable pageable);
    void updateStatus(OrderUpdateStatusDto updateStatusDto);
}
