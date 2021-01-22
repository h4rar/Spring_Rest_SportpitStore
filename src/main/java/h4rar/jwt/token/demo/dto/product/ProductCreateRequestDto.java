package h4rar.jwt.token.demo.dto.product;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import h4rar.jwt.token.demo.model.statuses.SaleStatus;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductCreateRequestDto {

    private String name;

    private double price;

    private Double oldPrice;

    private int quantity;

    private String description;

    private Long categoryId;

    private MultipartFile pic;

    private SaleStatus saleStatus;
}

