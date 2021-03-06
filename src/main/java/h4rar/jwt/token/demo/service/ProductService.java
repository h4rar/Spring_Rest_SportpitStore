package h4rar.jwt.token.demo.service;

import h4rar.jwt.token.demo.dto.product.*;
import org.springframework.data.domain.*;

import javax.servlet.http.HttpServletRequest;

public interface ProductService {
    ProductResponseDto createNewProduct(ProductCreateRequestDto createDto);
    ProductResponseDto updateNewProduct(Long id, ProductCreateRequestDto updateDto);
    ProductResponseDto deleteOrder(Long id);
    Page<AllProductResponseDto> getAllProduct(Pageable pageable, String textSearch, String category, String sale);
    ProductResponseDto getOneProduct(Long id);
    ProductResponseDto createComment(CommentCreateRequestDto commentDto, HttpServletRequest req);
    ProductResponseDto hideComment(String idComment);
}
