package h4rar.jwt.token.demo.service.impl;

import h4rar.jwt.token.demo.dto.order.*;
import h4rar.jwt.token.demo.dto.product.AllProductResponseDto;
import h4rar.jwt.token.demo.exception.BadRequestException;
import h4rar.jwt.token.demo.model.*;
import h4rar.jwt.token.demo.model.statuses.BasicStatus;
import h4rar.jwt.token.demo.model.statuses.order.OrderStatus;
import h4rar.jwt.token.demo.repository.*;
import h4rar.jwt.token.demo.security.jwt.JwtTokenProvider;
import h4rar.jwt.token.demo.service.OrderService;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {

    private final JwtTokenProvider jwtTokenProvider;

    private final AddressRepository addressRepository;

    private final ProductRepository productRepository;

    private final ProductInOrdersRepository productInOrdersRepository;

    private final OrderRepository orderRepository;

    public OrderServiceImpl(
            JwtTokenProvider jwtTokenProvider, AddressRepository addressRepository, ProductRepository productRepository,
            ProductInOrdersRepository productInOrdersRepository,
            OrderRepository orderRepository
    ) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.addressRepository = addressRepository;
        this.productRepository = productRepository;
        this.productInOrdersRepository = productInOrdersRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public OrderResponseDto createNewOrder(HttpServletRequest req, OrderCreateRequestDto dto) {
        User user = jwtTokenProvider.getUserFromHttpServletRequest(req);
        Order order = new Order();
        order.setOrderStatus(OrderStatus.В_процессе);
        order.setUser(user);
        order.setAllPrice(dto.getAllPrice());
        order.setDelivery(dto.getDelivery());
        order.setPaymentMethod(dto.getPaymentMethod());
        Address address = addressRepository.findById(dto.getAddressId())
                .orElseThrow(() -> new BadRequestException("Адреса с таким id  не существует"));
        order.setAddress(address);
        Map<Long, Integer> mapProducts = dto.getMapProducts();
        Set<ProductInOrder> set = new HashSet<>();
        for (Map.Entry<Long, Integer> entry : mapProducts.entrySet()
        ) {
            Product product = productRepository.findByIdAndBasicStatusNotIn(entry.getKey(), Collections.singleton(BasicStatus.DELETED));
            ProductInOrder productInOrder = new ProductInOrder();
            productInOrder.setProduct(product);
            productInOrder.setQuantity(entry.getValue());
            productInOrder.setBasicStatus(BasicStatus.ACTIVE);
            productInOrder.setOrder(order);
            productInOrdersRepository.save(productInOrder);
            set.add(productInOrder);
        }
        order.setProductInOrders(set);
        Order save = orderRepository.save(order);
        return OrderResponseDto.orderResponseDtoFromOrder(save);
    }

    @Override
    public Page<OrderResponseDto> getAllOrdersAdmin(
            Pageable pageable
    ) {
        Page<Order> orders = orderRepository.findAll(pageable);
        return orders.map(OrderResponseDto::orderResponseDtoFromOrder);
    }

    @Override
    public Page<OrderResponseDto> getAllOrders(HttpServletRequest req, Pageable pageable) {
        User user = jwtTokenProvider.getUserFromHttpServletRequest(req);
        Page<Order> orders = orderRepository.findAllByUser(user, pageable);
        return orders.map(OrderResponseDto::orderResponseDtoFromOrder);
    }

    @Override
    public void updateStatus(OrderUpdateStatusDto updateStatusDto) {
        Order order = orderRepository.findById(updateStatusDto.getId())
                .orElseThrow(() -> new BadRequestException("Заказа с таким id  не существует"));
        order.setOrderStatus(updateStatusDto.getOrderStatus());
        orderRepository.save(order);
    }
}
