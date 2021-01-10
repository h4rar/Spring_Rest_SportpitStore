package h4rar.jwt.token.demo.model;

import h4rar.jwt.token.demo.model.statuses.BasicStatus;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "roles")
@Data
public class Role extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "basic_status")
    private BasicStatus basicStatus;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private List<User> users;
}
