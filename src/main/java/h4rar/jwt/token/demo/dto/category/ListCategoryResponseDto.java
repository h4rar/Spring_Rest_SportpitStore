package h4rar.jwt.token.demo.dto.category;

import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class ListCategoryResponseDto {
    List<CategoryResponseDto> categories;
}