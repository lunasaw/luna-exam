package da.springframework.springbootwebflux.repositories;

import da.springframework.springbootwebflux.model.documents.Category;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CategoryRepository extends ReactiveMongoRepository<Category, String> {
}
