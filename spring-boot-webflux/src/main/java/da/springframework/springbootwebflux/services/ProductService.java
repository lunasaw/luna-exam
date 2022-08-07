package da.springframework.springbootwebflux.services;

import da.springframework.springbootwebflux.model.documents.Category;
import da.springframework.springbootwebflux.model.documents.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductService {

    public Flux<Product> findAll();

    public Flux<Product> findAllByNameUpperCase();

    public Flux<Product> findAllByNameUpperCaseRepeat();

    public Mono<Product> findById(String id);

    public Mono<Product> save(Product product);

    public Mono<Void> delete(Product product);

    public Flux<Category> findAllCategories();

    public Mono<Category> findCategoryById(String id);

    public Mono<Category> saveCategory(Category category);
}
