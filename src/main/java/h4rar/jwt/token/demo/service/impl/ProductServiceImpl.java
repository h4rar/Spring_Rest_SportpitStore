package h4rar.jwt.token.demo.service.impl;

import h4rar.jwt.token.demo.dto.product.*;
import h4rar.jwt.token.demo.exception.*;
import h4rar.jwt.token.demo.model.*;
import h4rar.jwt.token.demo.model.statuses.BasicStatus;
import h4rar.jwt.token.demo.repository.*;
import h4rar.jwt.token.demo.security.jwt.JwtTokenProvider;
import h4rar.jwt.token.demo.service.*;
import h4rar.jwt.token.demo.service.search.ProductSpecification;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class ProductServiceImpl implements ProductService {

    private final CategoryRepository categoryRepository;

    private final ProductRepository productRepository;

    private final CommentRepository commentRepository;

    private final JwtTokenProvider jwtTokenProvider;

    private final S3Services s3Services;

    public ProductServiceImpl(
            CategoryRepository categoryRepository, ProductRepository productRepository,
            CommentRepository commentRepository,
            JwtTokenProvider jwtTokenProvider,
            S3Services s3Services
    ) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.commentRepository = commentRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.s3Services = s3Services;
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

        MultipartFile pic = createDto.getPic();
        String fileName = System.currentTimeMillis() + "_" + pic.getOriginalFilename();
        String pathS3 = "/" + product.getClass().getSimpleName() + "/" + product.getName();
        product.setPicPath("https://sportpit.s3.eu-north-1.amazonaws.com" + pathS3 + "/" + fileName);
        s3Services.uploadFile(pic, fileName, pathS3);

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
    public Page<AllProductResponseDto> getAllProduct(Pageable pageable, String textSearch, String category) {
        System.out.println(textSearch);
        System.out.println(category);
        Page<AllProductResponseDto> page = null;
        if (StringUtils.isBlank(textSearch) && StringUtils.isBlank(category)) {
            System.out.println(1);
            Page<Product> allByStatusNotIn = productRepository.findAllByBasicStatusNotIn(pageable, Collections.singleton(BasicStatus.DELETED));
            return allByStatusNotIn.map(AllProductResponseDto::allProductResponseDtoFromProduct);
        }
        if (!StringUtils.isBlank(textSearch) && !StringUtils.isBlank(category)) {
            System.out.println(2);
            List<Product> all = productRepository.findAll(ProductSpecification.search(textSearch).and(ProductSpecification.categoryFilter(category)));
            Set<Product> search = new HashSet<>(all);
            page = mapToDtoAndToPages(search, pageable);
            return page;
        }
        if (!StringUtils.isBlank(textSearch)) {
            System.out.println(3);
            List<Product> all = productRepository.findAll(ProductSpecification.search(textSearch));
            Set<Product> search = new HashSet<>(all);
            page = mapToDtoAndToPages(search, pageable);
        }
        if (!StringUtils.isBlank(category)) {
            System.out.println(4);
            List<Product> all = productRepository.findAll(ProductSpecification.categoryFilter(category));
            Set<Product> search = new HashSet<>(all);
            page = mapToDtoAndToPages(search, pageable);
        }
        return page;
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

    private Page<AllProductResponseDto> mapToDtoAndToPages(Set<Product> search, Pageable pageable) {
        List<Product> targetList = new ArrayList<>(search);
        int start = (int)pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), targetList.size());
        if (end < start) {
            Page<Product> orders = new PageImpl<>(targetList.subList(0, 0), pageable, 0);
            Page<AllProductResponseDto> page = orders.map(AllProductResponseDto::allProductResponseDtoFromProduct);
            return page;
        }
        Page<Product> orders = new PageImpl<>(targetList.subList(start, end), pageable, (long)targetList.size());
        Page<AllProductResponseDto> page = orders.map(AllProductResponseDto::allProductResponseDtoFromProduct);
        return page;
    }
}
