package h4rar.jwt.token.demo.model;

import h4rar.jwt.token.demo.model.statuses.BasicStatus;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "categories")
@Setter
@Getter
public class Category extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "basic_status")
    private BasicStatus basicStatus;

    @Column(name = "name")
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "category")
    private Set<Product> products;
}