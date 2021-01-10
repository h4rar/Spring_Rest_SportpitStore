package h4rar.jwt.token.demo.dto.product;

import lombok.Data;

@Data
public class ProductCreateRequestDto {

    private String name;

    private double price;

    private int quantity;

    private String description;

    private Long categoryId;
}

