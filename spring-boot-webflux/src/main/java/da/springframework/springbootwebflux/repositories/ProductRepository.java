package da.springframework.springbootwebflux.repositories;

import da.springframework.springbootwebflux.model.documents.Product;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ProductRepository extends ReactiveMongoRepository<Product, String> {
}
