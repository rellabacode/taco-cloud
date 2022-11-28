package tacos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import tacos.Ingredient;
import tacos.Ingredient.Type;
import tacos.Order;
import tacos.Taco;
import tacos.data.jpa.IngredientRepository;
import tacos.data.jpa.TacoRepository;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignTacoController {

    private final IngredientRepository ingredientRepo;
    private final TacoRepository tacoRepository;

    @ModelAttribute(name = "order")
    public Order order() {
        return new Order();
    }

    @ModelAttribute(name = "design")
    public Taco taco() {
        return new Taco();
    }

    @Autowired
    public DesignTacoController(IngredientRepository ingredientRepo, TacoRepository tacoRepository) {
        this.ingredientRepo = ingredientRepo;
        this.tacoRepository = tacoRepository;
    }

    private void attachViewIngredients(Model model, List<String> viewIngredientsIds) {
        List<Ingredient> ingredients = new ArrayList<>();
        ingredientRepo.findAll().forEach(i -> ingredients.add(i));

        if (viewIngredientsIds != null && !viewIngredientsIds.isEmpty()) {
            viewIngredientsIds.remove(0);
            for (String viewIngredientId : viewIngredientsIds) {
                ingredients.stream()
                        .filter((viewIng) -> viewIng.getId().equals(viewIngredientId))
                        .forEach((ing) -> {
                            ing.setChecked(true);
                        });
            }
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
        log.info("DesignTacoController /design " + model.getAttribute("design") + model.getAttribute("order"));
        return "design";
    }

    private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
        return ingredients
                .stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }

    @PostMapping
    public String processDesign(@Valid Taco design, Errors errors, Model model) {
        log.info("Submitted taco design "+ design);
        if (errors.hasErrors()) {
            log.error("Design Form has errors");
            Taco taco = (Taco) model.getAttribute("design");
            attachViewIngredients(model, taco.getIngredients());
            return "design";
        }

        Order order = (Order) model.getAttribute("order");

        design.getIngredients().remove(0);

        log.info("Order before add design: " + order);

        Taco saved = tacoRepository.save(design);
        order.addDesign(saved);

        log.info("Processing design: " + design);
        log.info("Order Processing design: " + order);
        return "redirect:/orders/current";
    }


}
