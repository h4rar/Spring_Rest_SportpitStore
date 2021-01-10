package h4rar.jwt.token.demo.repository;

import h4rar.jwt.token.demo.model.*;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Page<Order> findAllByUser(User user, Pageable pageable);
}
