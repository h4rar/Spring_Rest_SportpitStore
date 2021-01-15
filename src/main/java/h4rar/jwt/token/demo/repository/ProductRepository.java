package h4rar.jwt.token.demo.repository;

import h4rar.jwt.token.demo.model.*;
import h4rar.jwt.token.demo.model.statuses.BasicStatus;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;

import java.util.*;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor {
    Page<Product> findAllByBasicStatusNotIn(Pageable pageable, final Collection<BasicStatus> basicStatuses);
    Product findByIdAndBasicStatusNotIn(Long id, final Collection<BasicStatus> basicStatuses);
}
