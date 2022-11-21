package tacos.web;

import lombok.extern.slf4j.Slf4j;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import tacos.Ingredient;
import tacos.Ingredient.Type;
import tacos.Taco;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/design")
public class DesignTacoController {

    private void attachViewIngredients(Model model, List<String> viewIngredientsIds) {
        List<Ingredient> ingredients = Arrays.asList(
                new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP),
                new Ingredient("COTO", "Corn Tortilla", Ingredient.Type.WRAP),
                new Ingredient("GRBF", "Ground Beef", Ingredient.Type.PROTEIN),
                new Ingredient("CARN", "Carnitas", Ingredient.Type.PROTEIN),
                new Ingredient("TMTO", "Diced Tomatoes", Ingredient.Type.VEGGIES),
                new Ingredient("LETC", "Lettuce", Ingredient.Type.VEGGIES),
                new Ingredient("CHED", "Cheddar", Ingredient.Type.CHEESE),
                new Ingredient("JACK", "Monterrey Jack", Ingredient.Type.CHEESE),
                new Ingredient("SLSA", "Salsa", Ingredient.Type.SAUCE),
                new Ingredient("SRCR", "Sour Cream", Ingredient.Type.SAUCE)
        );

        if (viewIngredientsIds != null && !viewIngredientsIds.isEmpty()) {
            viewIngredientsIds.remove(0);
            for (String viewIngredientId : viewIngredientsIds) {
//                for (int i = 0; i < ingredients.size() && !ingredients.get(i).getId().equals(viewIngredientId); i++){};

                log.info("searching " + viewIngredientId);
                log.info("ingredients " + ingredients);

                ingredients.stream()
                        .filter((viewIng) -> viewIng.getId().equals(viewIngredientId))
                        .forEach((ing) -> {
                            log.info("Seteando");
                            ing.setChecked(true);
                        });
            }

            log.info(""+ingredients);

        }

        Type[] types = Ingredient.Type.values();
        for (Type type : types) {
            model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
        }

        if (viewIngredientsIds == null)
            model.addAttribute("design", new Taco());
    }

    @GetMapping
    public String showDesignForm(Model model) {
        attachViewIngredients(model, null);
        return "design";
    }

    private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
        return ingredients
                .stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }

    @PostMapping
    public String processDesign(@Valid @ModelAttribute("design") Taco design, Errors errors, Model model) {
        log.info("Submited design " + design);
        log.info("Errors " + errors);
        log.info("Model " + model);

        if (errors.hasErrors()) {
            log.error("Design Form has errors");
            Taco taco = (Taco) model.getAttribute("design");
            attachViewIngredients(model, taco.getIngredients());
            return "design";
        }
        //save taco design
        log.info("Processing design: " + design);
        return "redirect:/orders/current";
    }


}
