package h4rar.jwt.token.demo.dto.product;

import lombok.Data;

@Data
public class ProductUpdateRequestDto {

    private Long id;

    private String name;

    private Double price;

    private Integer quantity;

    private String description;

    private Long categoryId;
}

