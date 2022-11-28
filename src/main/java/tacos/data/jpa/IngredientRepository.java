package tacos.data.jpa;

import org.springframework.data.repository.CrudRepository;
import tacos.Ingredient;

//repo-entityid
public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
