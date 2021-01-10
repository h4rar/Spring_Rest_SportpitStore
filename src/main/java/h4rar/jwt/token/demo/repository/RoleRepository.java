package h4rar.jwt.token.demo.repository;

import h4rar.jwt.token.demo.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);
}
