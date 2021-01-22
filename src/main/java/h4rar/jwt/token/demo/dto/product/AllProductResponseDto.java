package h4rar.jwt.token.demo.dto.product;

import com.sun.org.apache.xalan.internal.xsltc.trax.DOM2SAX;
import h4rar.jwt.token.demo.model.*;
import h4rar.jwt.token.demo.model.statuses.SaleStatus;
import lombok.Data;

import java.util.*;

@Data
public class AllProductResponseDto {

    private Long id;

    private String name;

    private double price;

    private Double oldPrice;
    private SaleStatus saleStatus;

    private int quantity;

    private String description;

    private String category;

    private String picPath;


    public AllProductResponseDto(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.quantity = product.getQuantity();
        this.description = product.getDescription();
        this.category = product.getCategory().getName();
        this.picPath = product.getPicPath();
        this.saleStatus = product.getSaleStatus();
        this.oldPrice = product.getOldPrice();
    }

    public static AllProductResponseDto allProductResponseDtoFromProduct(Product product) {
        return new AllProductResponseDto(product);
    }
}