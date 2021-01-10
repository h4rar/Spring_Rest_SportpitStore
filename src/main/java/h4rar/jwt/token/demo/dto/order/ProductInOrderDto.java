package h4rar.jwt.token.demo.dto.order;

import h4rar.jwt.token.demo.dto.product.ProductResponseDto;
import h4rar.jwt.token.demo.model.*;
import h4rar.jwt.token.demo.model.statuses.BasicStatus;
import lombok.Data;

@Data
public class ProductInOrderDto {

    private Long id;
    private BasicStatus status;
    private Integer quantity;
    private ProductResponseDto productResponseDto;

    public static ProductInOrderDto productInOrderDtoFromProductInOrder(ProductInOrder productInOrder) {
        ProductInOrderDto inOrder = new ProductInOrderDto();
        inOrder.setId(productInOrder.getId());
        inOrder.setStatus(productInOrder.getBasicStatus());
        inOrder.setQuantity(productInOrder.getQuantity());
        Product product = productInOrder.getProduct();
        inOrder.setProductResponseDto(ProductResponseDto.productResponseDtoFromProduct(product));
        return inOrder;
    }
}
