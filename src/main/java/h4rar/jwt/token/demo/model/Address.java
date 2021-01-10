package h4rar.jwt.token.demo.model;

import h4rar.jwt.token.demo.model.statuses.BasicStatus;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "address")
@Setter
@Getter
public class Address extends BaseEntity{

    @Enumerated(EnumType.STRING)
    @Column(name = "basic_status")
    private BasicStatus basicStatus;

    @Column(name = "street")
    private String street;

    @Column(name = "house")
    private String house;

    @Column(name = "room")
    private String room;

    @Column(name = "index")
    private String index;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "address", cascade = CascadeType.ALL)
    private List<Order> orders;
}