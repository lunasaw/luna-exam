package da.springframework.springbootwebflux.controllers;

import da.springframework.springbootwebflux.model.documents.Category;
import da.springframework.springbootwebflux.model.documents.Product;
import da.springframework.springbootwebflux.services.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Date;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@SessionAttributes("product") //save on session for create and save
@Controller
public class ProductController {

    private final ProductService productService;

    @Value("${config.uploads.path}")
    private String path;

    @GetMapping({"/list", "/"})
    public Mono<String> list(Model model) {

        Flux<Product> productFlux = productService.findAllByNameUpperCase();

        productFlux.subscribe(product -> log.info(product.getName()));

        model.addAttribute("products", productFlux);
        model.addAttribute("title", "Listado de Productos");

        return Mono.just("list");
    }

    @ModelAttribute("categories") //pass to the form in global form
    public Flux<Category> categories() {

        return productService.findAllCategories();
    }

    @GetMapping("/view/{id}")
    public Mono<String> view(Model model, @PathVariable String id) {

        return productService.findById(id)
                .doOnNext(product -> {
                    model.addAttribute("product", product);
                    model.addAttribute("title", "Detalle Producto");
                }).switchIfEmpty(Mono.just(new Product()))
                .flatMap(product -> {
                    if (product.getId() == null) {
                        return Mono.error(new InterruptedException("No existe el producto"));
                    }

                    return Mono.just(product);
                }).then(Mono.just("view"))
                .onErrorResume(throwable -> Mono.just("redirect:/list?error=no+existe+el+producto"));
    }

    @GetMapping("/uploads/img/{photoName:.+}") //.+ -> .jpg, .pgn, etc.
    public Mono<ResponseEntity<Resource>> viewPhoto(@PathVariable String photoName) throws MalformedURLException {
        Path filePath = Paths.get(path).resolve(photoName).toAbsolutePath();

        Resource resource = new UrlResource(filePath.toUri());

        return Mono.just(
                ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource)
        );
    }

    @GetMapping("/form")
    public Mono<String> create(Model model) {

        model.addAttribute("product", new Product());
        model.addAttribute("title", "Formulario de producto");
        model.addAttribute("button", "Crear");

        return Mono.just("form");
    }

    @GetMapping("/form/{id}")
    public Mono<String> edit(@PathVariable String id, Model model) {

        Mono<Product> productMono = productService.findById(id)
                .doOnNext(product -> log.info("Producto: " + product.getName()))
                .defaultIfEmpty(new Product());

        model.addAttribute("title", "Editar Producto");
        model.addAttribute("product", productMono);
        model.addAttribute("button", "Editar");

        return Mono.just("form");
    }

    @GetMapping("/form-v2/{id}")
    public Mono<String> editV2(@PathVariable String id, Model model) {

        return productService.findById(id)
                .doOnNext(product -> {
                    log.info("Producto: " + product.getName());
                    model.addAttribute("title", "Editar Producto");
                    model.addAttribute("product", product);
                    model.addAttribute("button", "Editar");
                })
                .defaultIfEmpty(new Product())
                .flatMap(product -> {
                    if (product.getId() == null) {
                        return Mono.error(new InterruptedException("No existe el producto"));
                    }

                    return Mono.just(product);
                })
                .then(Mono.just("form"))
                .onErrorResume(throwable -> Mono.just("redirect:/list?error=no+existe+el+producto"));
    }

    @PostMapping("/form")
    public Mono<String> save(@Valid Product product, BindingResult result, Model model, @RequestPart(name = "file") FilePart filePart, SessionStatus sessionStatus) {

        if (result.hasErrors()) {
            model.addAttribute("title", "Errores en el formulario Producto");
            model.addAttribute("button", "Guardar");

            return Mono.just("form");
        }

        sessionStatus.setComplete(); //To clean the SessionAttribute

        Mono<Category> categoryMono = productService.findCategoryById(product.getCategory().getId());

        return categoryMono.flatMap(category -> {
                    if (product.getCreationDate() == null) {
                        product.setCreationDate(new Date());
                    }

                    if (!filePart.filename().isEmpty()) {
                        product.setPhoto(UUID.randomUUID().toString() + "-" + filePart.filename()
                                .replace(" ", "")
                                .replace(":", "")
                                .replace("\\", "")
                        );
                    }

                    product.setCategory(category);

                    return productService.save(product);
                }).doOnNext(prod -> {
                    log.info("Categoria asignada: " + prod.getCategory().getName() + " Id Cat: " + prod.getCategory().getId());
                    log.info("Producto guardado: " + prod.getName() + " Id: " + prod.getId());
                })
                .flatMap(prod -> {
                    if (!filePart.filename().isEmpty()) {
                        return filePart.transferTo(new File(path + prod.getPhoto()));
                    }

                    return Mono.empty();
                })
                .thenReturn("redirect:/list?success=producto+guardado+con+exito"); // too : }).then(Mono.just("redirect:/list"));
    }

    @GetMapping("/delete/{id}")
    public Mono<String> delete(@PathVariable String id) {
        return productService.findById(id)
                .defaultIfEmpty(new Product())
                .flatMap(product -> {
                    if (product.getId() == null) {
                        return Mono.error(new InterruptedException("No existe el producto a eliminar"));
                    }

                    return Mono.just(product);
                })
                .flatMap(product -> {
                    log.info("Eliminando producto: " + product.getName());
                    log.info("Eliminando producto Id: " + product.getId());
                    return productService.delete(product);
                })
                .then(Mono.just("redirect:/list?success=producto+eliminado+con+exito"))
                .onErrorResume(throwable -> Mono.just("redirect:/list?error=no+existe+el+producto+a+eliminar"));
    }

    @GetMapping("/datadriver-list")
    public String dataDriverList(Model model) {

        Flux<Product> productFlux = productService.findAllByNameUpperCase().delayElements(Duration.ofSeconds(1));

        productFlux.subscribe(product -> log.info(product.getName()));

        model.addAttribute("products", new ReactiveDataDriverContextVariable(productFlux, 2));
        model.addAttribute("title", "Listado de Productos");

        return "list";
    }

    @GetMapping("/full-list")
    public String fullList(Model model) {

        Flux<Product> productFlux = productService.findAllByNameUpperCaseRepeat();

        model.addAttribute("products", productFlux);
        model.addAttribute("title", "Listado de Productos");

        return "list";
    }

    @GetMapping("/chunked-list")
    public String chunkedList(Model model) {

        Flux<Product> productFlux = productService.findAllByNameUpperCaseRepeat();

        model.addAttribute("products", productFlux);
        model.addAttribute("title", "Listado de Productos");

        return "chunked-list";
    }
}
