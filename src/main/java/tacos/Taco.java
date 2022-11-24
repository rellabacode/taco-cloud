package tacos;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
public class Taco {
    private Long id;
    private Date createdAt;

    @Size(min = 5, message = "Name must be at least 5 characters long")
    @NotNull
    private String name;

    @Size(min = 1, message = "You must choose at least 1 ingredient")
    @NotNull
    private List<String> ingredients;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
