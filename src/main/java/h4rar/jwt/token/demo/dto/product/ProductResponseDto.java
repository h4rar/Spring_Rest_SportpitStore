package h4rar.jwt.token.demo.dto.product;

import h4rar.jwt.token.demo.model.*;
import h4rar.jwt.token.demo.model.statuses.SaleStatus;
import lombok.Data;

import java.util.*;

@Data
public class ProductResponseDto {

    private Long id;

    private String name;

    private double price;

    private Double oldPrice;

    private int quantity;

    private String description;

    private String category;

    private String picPath;

    private SaleStatus saleStatus;

    private List<CommentDto> commentDto = new ArrayList<>();

    public ProductResponseDto(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.oldPrice = product.getOldPrice();
        this.quantity = product.getQuantity();
        this.description = product.getDescription();
        this.category = product.getCategory().getName();
        this.picPath = product.getPicPath();
        this.saleStatus = product.getSaleStatus();
        List<Comment> comments = product.getComments();
        List<CommentDto> newCommentDto = new ArrayList<>();
        if(comments != null){
            for (Comment comm: comments
            ) {
                CommentDto cd = new CommentDto(comm);
                newCommentDto.add(cd);
            }
        }
        Collections.reverse(newCommentDto);
        this.commentDto = newCommentDto;
    }

    public static ProductResponseDto productResponseDtoFromProduct(Product product) {
        return new ProductResponseDto(product);
    }
}