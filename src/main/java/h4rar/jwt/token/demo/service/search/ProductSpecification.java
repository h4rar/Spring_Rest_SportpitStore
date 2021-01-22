package h4rar.jwt.token.demo.service.search;

import h4rar.jwt.token.demo.model.Product;
import h4rar.jwt.token.demo.model.statuses.*;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

public class ProductSpecification {
    public static Specification<Product> search(String search) {
        return (root, query, criteriaBuilder) -> {
            String likeSearch = "%" + search + "%";
            Predicate statusDel = criteriaBuilder.notEqual(root.get("basicStatus"), BasicStatus.DELETED);
            Predicate name = criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), likeSearch.toLowerCase());
            Predicate category = criteriaBuilder.like(criteriaBuilder.lower(root.join("category", JoinType.LEFT).get("name")),  likeSearch.toLowerCase());
//            Predicate description = criteriaBuilder.like(criteriaBuilder.lower(root.get("description")),  likeSearch.toLowerCase());
//            Predicate organizationName = criteriaBuilder.like(criteriaBuilder.lower(root.get("organizationName")),  likeSearch.toLowerCase());
            Predicate number = criteriaBuilder.like(criteriaBuilder.lower(root.get("id").as(String.class)),  likeSearch.toLowerCase());
            Predicate mainPredicate = criteriaBuilder.or(name, number, category);
            return criteriaBuilder.and(statusDel, mainPredicate);
        };
    }

    public static Specification<Product> categoryFilter(String category) {
        return (root, query, criteriaBuilder) -> {
            String likeSearch = "%" + category + "%";
            Predicate filterByCategory = criteriaBuilder.like(criteriaBuilder.lower(root.join("category", JoinType.LEFT).get("name")),  likeSearch.toLowerCase());

//            Predicate filterByCategory = criteriaBuilder.equal(criteriaBuilder.lower(root.join("category", JoinType.LEFT).get("name")),  category);
            return filterByCategory;
        };
    }

    public static Specification<Product> saleFilter(String sale) {
        return (root, query, criteriaBuilder) -> {
//            String likeSearch = "%" + sale + "%";
//            Predicate filterBySale = criteriaBuilder.like(criteriaBuilder.lower(root.get("saleStatus")),  likeSearch.toLowerCase());
            Predicate filterBySale = criteriaBuilder.equal(root.get("saleStatus"), SaleStatus.SALE);

//            Predicate filterByCategory = criteriaBuilder.equal(criteriaBuilder.lower(root.join("category", JoinType.LEFT).get("name")),  category);
            return filterBySale;
        };
    }
}
