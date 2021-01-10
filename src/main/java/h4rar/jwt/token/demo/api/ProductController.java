package h4rar.jwt.token.demo.api;

import h4rar.jwt.token.demo.dto.product.*;
import h4rar.jwt.token.demo.model.*;
import h4rar.jwt.token.demo.repository.*;
import h4rar.jwt.token.demo.service.*;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/api/")
public class ProductController {

    private final ProductService productService;

    public ProductController(
            ProductService productService
    ) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public Page<AllProductResponseDto> getAllProduct(
            @PageableDefault(sort = {"created"},
                    size = 12, value = 12, direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return productService.getAllProduct(pageable);
    }

    @GetMapping("/products/{id}")
    public ProductResponseDto getOneProduct(
            @PathVariable Long id
    ) {
        return productService.getOneProduct(id);
    }

    @PostMapping("/admin/products")
    public ProductResponseDto createNewProduct(
            @RequestBody ProductCreateRequestDto productDto
    ) {
        return productService.createNewProduct(productDto);
    }

    @PatchMapping("/admin/products")
    public ProductResponseDto updateProduct(
            @RequestBody ProductUpdateRequestDto productDto
    ) {
        return productService.updateNewProduct(productDto);
    }

    @PostMapping("/products/comments")
    public ProductResponseDto addComment(
            @RequestBody CommentCreateRequestDto commentDto,
            HttpServletRequest req
    ) {
        return productService.createComment(commentDto, req);
    }

    @PostMapping("/admin/products/comments/hide-comment/{idComment}")
    public ProductResponseDto hideComment(
            @PathVariable("idComment") String idComment
    ) {
        return productService.hideComment(idComment);
    }
}
