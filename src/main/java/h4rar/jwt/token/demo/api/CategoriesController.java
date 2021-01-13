package h4rar.jwt.token.demo.api;

import h4rar.jwt.token.demo.dto.category.*;
import h4rar.jwt.token.demo.service.CategoryService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/")
@CrossOrigin
public class CategoriesController {

    private final CategoryService categoryService;

    public CategoriesController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories")
    public ListCategoryResponseDto getAllCategories() {
        return categoryService.getAllCategory();
    }

    @PostMapping("admin/categories")
    public CategoryResponseDto createNewCategory(
            @RequestBody CategoryCreateRequestDto categoryCreateRequestDto
    ) {
        return categoryService.createNewCategory(categoryCreateRequestDto);
    }
}
