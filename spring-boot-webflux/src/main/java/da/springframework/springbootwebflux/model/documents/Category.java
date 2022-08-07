package da.springframework.springbootwebflux.model.documents;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "categories")
public class Category {

    @Id
    @NotEmpty
    private String id;

    private String name;

    public Category(String name) {
        this.name = name;
    }
}
