package h4rar.jwt.token.demo.model;

import h4rar.jwt.token.demo.model.statuses.BasicStatus;
import lombok.Data;
import org.springframework.data.annotation.*;

import javax.persistence.*;
import javax.persistence.Id;
import java.time.*;

@MappedSuperclass
@Data
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    @Column(name = "created")
    private LocalDateTime created = LocalDateTime.now();

    @LastModifiedDate
    @Column(name = "updated")
    private LocalDateTime updated = LocalDateTime.now();
}