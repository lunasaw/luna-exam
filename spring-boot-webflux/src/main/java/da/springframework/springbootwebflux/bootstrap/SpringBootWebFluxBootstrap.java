package da.springframework.springbootwebflux.bootstrap;

import da.springframework.springbootwebflux.model.documents.Category;
import da.springframework.springbootwebflux.model.documents.Product;
import da.springframework.springbootwebflux.services.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Component
public class SpringBootWebFluxBootstrap implements CommandLineRunner {

    private final ProductService productService;
    private final ReactiveMongoTemplate reactiveMongoTemplate;

    @Override
    public void run(String... args) throws Exception {

        reactiveMongoTemplate.dropCollection("products").subscribe();
        reactiveMongoTemplate.dropCollection("categories").subscribe();

        Category electronic = new Category("Electrónico");
        Category sport = new Category("Deportes");
        Category technology = new Category("Tecnología");
        Category furniture = new Category("Muebles");

        Flux.just(electronic, sport, technology, furniture)
                .flatMap(productService::saveCategory)
                .doOnNext(category -> log.info("Categoria creada: " + category.getName() + ", Id: " + category.getId()))
                .thenMany(
                        Flux.just(new Product("TV Panasonic Pantalla LCD", 456.89, electronic),
                                new Product("Sony Camara HD Digital", 177.99, electronic),
                                new Product("Apple iPad", 46.89, electronic),
                                new Product("Sony Notebook", 846.99, technology),
                                new Product("Hewlett Packard Multifuncional", 200.89, technology),
                                new Product("Bianchi Bicicleta", 70.89, sport),
                                new Product("HP Notebook Omen 17", 2500.89, technology),
                                new Product("Mica Cómoda 5 Cajones", 150.99, furniture),
                                new Product("TV Sony Bravia OLED 4K Ultra HD", 2255.99, electronic)
                        ).flatMap(product -> {
                            product.setCreationDate(new Date());
                            return productService.save(product);
                        })
                ).subscribe(product -> log.info("Inserted: " + product.getId() + " " + product.getName()));
    }
}
