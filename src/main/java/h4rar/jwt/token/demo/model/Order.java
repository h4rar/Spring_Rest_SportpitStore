package h4rar.jwt.token.demo.model;

import h4rar.jwt.token.demo.model.statuses.order.*;
import lombok.*;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "orders")
@Setter
@Getter
public class Order extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatus orderStatus;

    @OneToMany
    @JoinColumn(name = "order_id")
    private Set<ProductInOrder> productInOrders;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    @Enumerated(EnumType.STRING)
    @Column(name = "delivery")
    private VersionDelivery delivery;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method")
    private PaymentMethod paymentMethod;
}
