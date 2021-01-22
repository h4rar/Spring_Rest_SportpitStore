package h4rar.jwt.token.demo.model;

import h4rar.jwt.token.demo.model.statuses.*;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "products")
@Setter
@Getter
public class Product extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "basic_status")
    private BasicStatus basicStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "sale_status")
    private SaleStatus saleStatus;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private double price;

    @Column(name = "old_price")
    private Double oldPrice;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "description")
    @Type(type = "text")
    private String description;

    @Column(name = "pic_path")
    private String picPath;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "product_id")
    private List<Comment> comments;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "product_id")
    private List<ProductInOrder> productInOrder;
}