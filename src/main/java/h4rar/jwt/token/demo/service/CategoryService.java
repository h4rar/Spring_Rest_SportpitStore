package h4rar.jwt.token.demo.service;

import h4rar.jwt.token.demo.dto.category.*;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CategoryService {
    ListCategoryResponseDto getAllCategory();
    CategoryResponseDto createNewCategory(CategoryCreateRequestDto categoryCreateRequestDto);
}
