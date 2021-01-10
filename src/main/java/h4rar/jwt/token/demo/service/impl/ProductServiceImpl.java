package h4rar.jwt.token.demo.service.impl;

import h4rar.jwt.token.demo.dto.product.*;
import h4rar.jwt.token.demo.exception.*;
import h4rar.jwt.token.demo.model.*;
import h4rar.jwt.token.demo.model.statuses.BasicStatus;
import h4rar.jwt.token.demo.repository.*;
import h4rar.jwt.token.demo.security.jwt.JwtTokenProvider;
import h4rar.jwt.token.demo.service.ProductService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class ProductServiceImpl implements ProductService {

    private final CategoryRepository categoryRepository;

    private final ProductRepository productRepository;

    private final CommentRepository commentRepository;

    private final JwtTokenProvider jwtTokenProvider;

    public ProductServiceImpl(
            CategoryRepository categoryRepository, ProductRepository productRepository,
            CommentRepository commentRepository,
            JwtTokenProvider jwtTokenProvider
    ) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.commentRepository = commentRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public ProductResponseDto createNewProduct(ProductCreateRequestDto createDto) {
        Product product = new Product();
        product.setName(createDto.getName());
        product.setPrice(createDto.getPrice());
        product.setQuantity(createDto.getQuantity());
        product.setDescription(createDto.getDescription());
        Category category = categoryRepository.findById(createDto.getCategoryId())
                .orElseThrow(() -> new BadRequestException("Выбранной категории не существует"));
        product.setCategory(category);
        product.setBasicStatus(BasicStatus.ACTIVE);
        Product saveProduct = productRepository.save(product);
        return new ProductResponseDto(saveProduct);
    }

    @Override
    public ProductResponseDto updateNewProduct(ProductUpdateRequestDto updateDto) {
        Product product = productRepository.findById(updateDto.getId()).orElseThrow(() -> new NotFoundException("Продукта с таким id не существует"));
        Double price = updateDto.getPrice();
        if (!StringUtils.isBlank(updateDto.getName())
        ) {
            product.setName(updateDto.getName());
        }
        if (!StringUtils.isBlank(updateDto.getDescription())
        ) {
            product.setDescription(updateDto.getDescription());
        }
        product.setQuantity(updateDto.getQuantity());
        product.setPrice(price);
        Category category = categoryRepository.findById(updateDto.getCategoryId())
                .orElseThrow(() -> new BadRequestException("Выбранной категории не существует"));
        product.setCategory(category);
        Product saveProduct = productRepository.save(product);
        return new ProductResponseDto(saveProduct);
    }

    @Override
    public Page<AllProductResponseDto> getAllProduct(Pageable pageable) {
        Page<Product> allByStatusNotIn = productRepository.findAllByBasicStatusNotIn(pageable, Collections.singleton(BasicStatus.DELETED));
        return allByStatusNotIn.map(AllProductResponseDto::allProductResponseDtoFromProduct);
    }

    @Override
    public ProductResponseDto getOneProduct(Long id) {
        Product byStatusNotIn = productRepository.findByIdAndBasicStatusNotIn(id, Collections.singleton(BasicStatus.DELETED));
        return new ProductResponseDto(byStatusNotIn);
    }

    @Override
    public ProductResponseDto createComment(CommentCreateRequestDto commentDto, HttpServletRequest req) {
        Comment comment = new Comment();
        comment.setText(commentDto.getText());
        comment.setBasicStatus(BasicStatus.ACTIVE);

        comment.setUser(jwtTokenProvider.getUserFromHttpServletRequest(req));

        Product product = productRepository.findByIdAndBasicStatusNotIn(commentDto.getId(), Collections.singleton(BasicStatus.DELETED));
        List<Comment> comments = product.getComments();
        if (comments == null) {
            comments = new ArrayList<>();
        }
        comments.add(comment);
        product.setComments(comments);
        commentRepository.save(comment);
        productRepository.save(product);
        return new ProductResponseDto(product);
    }

    @Override
    public ProductResponseDto hideComment(String idComment) {
        Long idL = Long.valueOf(idComment);
        Comment comment = commentRepository.findById(idL)
                .orElseThrow(() -> new BadRequestException("Комментария с таким id не сущствует"));
        BasicStatus basicStatus = comment.getBasicStatus();
        if (basicStatus == BasicStatus.ACTIVE) {
            comment.setBasicStatus(BasicStatus.NOT_ACTIVE);
        } else if (basicStatus == BasicStatus.NOT_ACTIVE) {
            comment.setBasicStatus(BasicStatus.ACTIVE);
        }
        commentRepository.save(comment);
        Product product = comment.getProduct();
        return new ProductResponseDto(product);
    }
}
