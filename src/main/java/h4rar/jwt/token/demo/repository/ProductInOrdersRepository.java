package h4rar.jwt.token.demo.repository;

import h4rar.jwt.token.demo.model.*;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface ProductInOrdersRepository extends JpaRepository<ProductInOrder, Long> {
}
