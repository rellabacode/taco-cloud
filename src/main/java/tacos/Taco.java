package tacos;

import com.google.common.base.Joiner;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Taco {

    public Taco() {
        this.ingredients = new LinkedList<String>();
    }

    public Taco(Long id, Date createdAt, String name, List<String> ingredients) {
        this.id = id;
        this.createdAt = createdAt;
        this.name = name;
        this.ingredients = ingredients;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date createdAt;

    @Size(min = 5, message = "Name must be at least 5 characters long")
    @NotNull
    private String name;

//    @ManyToMany(targetEntity = Ingredient.class)
//    @JoinTable(name = "taco_ingredients", joinColumns = @JoinColumn("taco"), inverseJoinColumns = @JoinColumn("ingredient"))
    @ElementCollection
    @CollectionTable(name = "taco_ingredients", joinColumns = @JoinColumn(name = "taco"))
    @Column(name = "ingredient")
    @Size(min = 1, message = "You must choose at least 1 ingredient")
    @NotNull
    private List<String> ingredients;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date date) {
        this.createdAt = date;
    }

    @PrePersist
    void createdAt() {
        this.createdAt = new Date();
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addIngredient(String ingredient) {
        this.ingredients.add(ingredient);
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public String toString() {
        return "Taco{" +
                "id=" + id +
                ", createdAt=" + createdAt +
                ", name='" + name +
                ", ingredients=[" + Joiner.on("").join(ingredients) + "]}";
    }
}
