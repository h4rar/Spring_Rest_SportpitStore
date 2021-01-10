package h4rar.jwt.token.demo;

import h4rar.jwt.token.demo.model.*;
import h4rar.jwt.token.demo.repository.ProductRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

import java.util.Collections;

@SpringBootApplication
public class JwtApplication {

    private final ProductRepository productRepository;

    public JwtApplication(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(JwtApplication.class, args);
    }

//    @Override
//    public void onApplicationEvent(ApplicationReadyEvent event) {
//        Product product = productRepository.findByIdAndStatusNotIn(1l, Collections.singleton(Status.DELETED));
//
//        ProductInOrder productInOrder = new ProductInOrder();
//
//        productInOrder.setProduct(product);
//        productInOrder.setQuantity(12);
//
//
//    }
}
