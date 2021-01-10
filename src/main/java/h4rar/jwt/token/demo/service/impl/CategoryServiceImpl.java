package h4rar.jwt.token.demo.service.impl;

import h4rar.jwt.token.demo.dto.category.*;
import h4rar.jwt.token.demo.model.*;
import h4rar.jwt.token.demo.model.statuses.BasicStatus;
import h4rar.jwt.token.demo.repository.CategoryRepository;
import h4rar.jwt.token.demo.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public ListCategoryResponseDto getAllCategory() {
        List<Category> allCategory = categoryRepository.findAllByBasicStatusNotIn(Collections.singleton(BasicStatus.DELETED));
        List<CategoryResponseDto> collect = allCategory.stream().map(CategoryResponseDto::categoryResponseDtoFromCategory).collect(Collectors.toList());
        return new ListCategoryResponseDto(collect);
    }

    @Override
    public CategoryResponseDto createNewCategory(CategoryCreateRequestDto categoryCreateRequestDto) {
        Category category = new Category();
        String name = categoryCreateRequestDto.getName();
        category.setName(name);
        category.setBasicStatus(BasicStatus.ACTIVE);
        categoryRepository.save(category);
        return new CategoryResponseDto(category);
    }
}
