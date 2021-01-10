package h4rar.jwt.token.demo.model;

import h4rar.jwt.token.demo.model.statuses.BasicStatus;
import lombok.*;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.*;

import javax.persistence.*;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "products_in_orders")
@Setter
@Getter
public class ProductInOrder extends BaseEntity{

    @Enumerated(EnumType.STRING)
    @Column(name = "basic_status")
    private BasicStatus basicStatus;

    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;

    @Column(name = "quantity")
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private Order order;
}