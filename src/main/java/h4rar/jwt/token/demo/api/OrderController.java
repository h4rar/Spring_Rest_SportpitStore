package h4rar.jwt.token.demo.api;

import h4rar.jwt.token.demo.dto.order.*;
import h4rar.jwt.token.demo.service.OrderService;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/api/")
public class OrderController {

    private final OrderService orderService;

    public OrderController(
            OrderService orderService
    ) {
        this.orderService = orderService;
    }

    @GetMapping("/admin/orders")
    public Page<OrderResponseDto> getAllOrdersAdmin(
            @PageableDefault(sort = {"created"},
                    size = 12, value = 12, direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return orderService.getAllOrdersAdmin(pageable);
    }

    @PostMapping("/admin/orders/update-status")
    @ResponseStatus(HttpStatus.OK)
    public void updateStatus(
            @RequestBody OrderUpdateStatusDto updateStatusDto
    ) {
        orderService.updateStatus(updateStatusDto);
    }

    @GetMapping("/users/orders")
    public Page<OrderResponseDto> getAllOrders(
            HttpServletRequest req,
            @PageableDefault(sort = {"created"},
                    size = 12, value = 12, direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return orderService.getAllOrders(req, pageable);
    }

    @PostMapping("/cart/to-order")
    public void createNewOrder(
            HttpServletRequest req,
            @RequestBody OrderCreateRequestDto dto

    ) {
        orderService.createNewOrder(req, dto);
    }
}
