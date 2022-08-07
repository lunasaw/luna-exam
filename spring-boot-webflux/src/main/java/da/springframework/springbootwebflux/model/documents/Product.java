package da.springframework.springbootwebflux.model.documents;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "products")
public class Product {

    @Id
    private String id;

    @NotEmpty
    private String name;

    @NotNull
    private Double price;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date creationDate;

    @Valid
    private Category category;

    private String photo;

    public Product(String name, Double price) {
        this.name = name;
        this.price = price;
    }

    public Product(String name, Double price, Category category) {
        this(name, price);
        this.category = category;
    }
}
