package h4rar.jwt.token.demo.repository;

import h4rar.jwt.token.demo.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
