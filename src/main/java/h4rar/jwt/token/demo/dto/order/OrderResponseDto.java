package h4rar.jwt.token.demo.dto.order;

import h4rar.jwt.token.demo.dto.address.AddressResponseDto;
import h4rar.jwt.token.demo.dto.user.response.UserResponseDto;
import h4rar.jwt.token.demo.model.*;
import h4rar.jwt.token.demo.model.statuses.order.*;
import lombok.Data;

import java.util.*;

@Data
public class OrderResponseDto {

    private Long id;

    private OrderStatus orderStatus;

    private VersionDelivery delivery;

    private PaymentMethod paymentMethod;

    private AddressResponseDto address;

    private UserResponseDto user;

    private Set<ProductInOrderDto> products;

    public OrderResponseDto(Order order) {
        this.id = order.getId();
        this.orderStatus = order.getOrderStatus();
        this.address = AddressResponseDto.fromAddress(order.getAddress());
        this.user = UserResponseDto.fromUser(order.getUser());
        this.delivery = order.getDelivery();
        this.paymentMethod = order.getPaymentMethod();
        Set<ProductInOrder> productInOrders = order.getProductInOrders();
        Set<ProductInOrderDto> dto = new HashSet<>();
        for (ProductInOrder product : productInOrders
        ) {
            dto.add(ProductInOrderDto.productInOrderDtoFromProductInOrder(product));
        }
        this.products = dto;
    }

    public static OrderResponseDto orderResponseDtoFromOrder(Order order) {
        return new OrderResponseDto(order);
    }
}
