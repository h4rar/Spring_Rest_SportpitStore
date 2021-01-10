package h4rar.jwt.token.demo.repository;

import h4rar.jwt.token.demo.model.*;
import h4rar.jwt.token.demo.model.statuses.BasicStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findAllByBasicStatusNotIn(final Collection<BasicStatus> basicStatuses);

    Optional<Category> findByName(String name);

}
