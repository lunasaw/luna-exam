package da.springframework.springbootwebflux.controllers;

import da.springframework.springbootwebflux.model.documents.Product;
import da.springframework.springbootwebflux.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/products")
public class ProductRestController {

    private final ProductRepository productRepository;

    @GetMapping
    public Flux<Product> index() {
        Flux<Product> productFlux = productRepository.findAll()
                .map(product -> {
                    product.setName(product.getName().toUpperCase());

                    return product;
                }).doOnNext(product -> log.info(product.getName()));

        return productFlux;
    }

    @GetMapping("/{id}")
    public Mono<Product> show(@PathVariable String id) {

//        Mono<Product> productMono = productRepository.findById(id);
        Flux<Product> productFlux = productRepository.findAll();

        Mono<Product> productMono = productFlux.filter(product -> product.getId().equals(id))
                .next()
                .doOnNext(product -> log.info(product.getName()));

        return productMono;
    }
}
