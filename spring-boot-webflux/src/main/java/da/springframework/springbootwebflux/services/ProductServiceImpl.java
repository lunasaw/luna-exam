package da.springframework.springbootwebflux.services;

import da.springframework.springbootwebflux.model.documents.Category;
import da.springframework.springbootwebflux.model.documents.Product;
import da.springframework.springbootwebflux.repositories.CategoryRepository;
import da.springframework.springbootwebflux.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Flux<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Flux<Product> findAllByNameUpperCase() {
        return productRepository.findAll()
                .map(product -> {
                    product.setName(product.getName().toUpperCase());

                    return product;
                });
    }

    @Override
    public Flux<Product> findAllByNameUpperCaseRepeat() {
        return findAllByNameUpperCase().repeat(5000);
    }

    @Override
    public Mono<Product> findById(String id) {
        return productRepository.findById(id);
    }

    @Override
    public Mono<Product> save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Mono<Void> delete(Product product) {
        return productRepository.delete(product);
    }

    @Override
    public Flux<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Mono<Category> findCategoryById(String id) {
        return categoryRepository.findById(id);
    }

    @Override
    public Mono<Category> saveCategory(Category category) {
        return categoryRepository.save(category);
    }
}
