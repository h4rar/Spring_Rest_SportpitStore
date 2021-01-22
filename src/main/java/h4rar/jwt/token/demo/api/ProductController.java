package h4rar.jwt.token.demo.api;

import h4rar.jwt.token.demo.dto.product.*;
import h4rar.jwt.token.demo.service.*;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/api/")
@CrossOrigin
public class ProductController {

    private final ProductService productService;

    public ProductController(
            ProductService productService
    ) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public Page<AllProductResponseDto> getAllProduct(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String sale,
            @PageableDefault(sort = {"created"},
                    size = 12, value = 12, direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return productService.getAllProduct(pageable, search, category, sale);
    }

    @GetMapping("/products/{id}")
    public ProductResponseDto getOneProduct(
            @PathVariable Long id
    ) {
        return productService.getOneProduct(id);
    }

    @PostMapping(value = "/admin/products", consumes = {"multipart/form-data"})
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponseDto createOrder(
            @ModelAttribute ProductCreateRequestDto productDto
    ) {
        return productService.createNewProduct(productDto);
    }

    @DeleteMapping(value = "/admin/products/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponseDto deleteOrder(
            @PathVariable Long id
    ) {
        return productService.deleteOrder(id);
    }

    @PatchMapping(value = "/admin/products/{id}", consumes = {"multipart/form-data"})
    public ProductResponseDto updateProduct(
            @ModelAttribute ProductCreateRequestDto productDto,
            @PathVariable Long id
    ) {
        return productService.updateNewProduct(id, productDto);
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
