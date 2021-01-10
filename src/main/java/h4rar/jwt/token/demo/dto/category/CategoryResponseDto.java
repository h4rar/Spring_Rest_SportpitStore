package h4rar.jwt.token.demo.dto.category;

import h4rar.jwt.token.demo.model.Category;
import lombok.Data;

@Data
public class CategoryResponseDto {
    private Long id;
    private String name;

    public  CategoryResponseDto(Category category) {
        this.id = category.getId();
        this.name = category.getName();
    }

    public static CategoryResponseDto categoryResponseDtoFromCategory(Category category){
        return new CategoryResponseDto(category);
    }
}