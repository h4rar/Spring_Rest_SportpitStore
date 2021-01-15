package h4rar.jwt.token.demo.dto.product;

import h4rar.jwt.token.demo.model.*;
import lombok.Data;

import java.util.*;

@Data
public class ProductResponseDto {

    private Long id;

    private String name;

    private double price;

    private int quantity;

    private String description;

    private String category;

    private String picPath;

    private Set<CommentDto> commentDto = new HashSet<>();

    public ProductResponseDto(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.quantity = product.getQuantity();
        this.description = product.getDescription();
        this.category = product.getCategory().getName();
        this.picPath = product.getPicPath();
        List<Comment> comments = product.getComments();
        if(comments != null){
            for (Comment comm: comments
            ) {
                CommentDto cd = new CommentDto(comm);
                commentDto.add(cd);
            }
        }
    }

    public static ProductResponseDto productResponseDtoFromProduct(Product product) {
        return new ProductResponseDto(product);
    }
}