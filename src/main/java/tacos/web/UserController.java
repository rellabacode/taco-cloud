package tacos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tacos.ComparatorField;
import tacos.User;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@Slf4j
@RequestMapping("/user")
public class UserController {

    @GetMapping("/profile")
    public String showProfile(@AuthenticationPrincipal User user, Model model) throws IllegalAccessException {
        Field[] userFields = user.getClass().getDeclaredFields();
        class KeyValProp {
            private String key;
            private Object value;

            public KeyValProp(String key, Object value) {
                this.key = key;
                this.value = value;
            }

            public String getKey() {
                return key;
            }

            public Object getValue() {
                return value;
            }
        }

        for (Field f :
                userFields) {
            f.setAccessible(true);
        }

        Predicate<Field> allowedField = f -> (!f.getName().equals("id") &&
                !f.getName().equals("userPassword") &&
                !f.getName().equals("log") &&
                !f.getName().equals("serialVersionUID"));

        Stream<Field> stream = Arrays
                .stream(userFields)
                .filter(allowedField)
                .sorted(new ComparatorField());
//                .collect(Collectors.toList());
//                .toArray(Field[]::new);

        KeyValProp[] userProperties = stream.map(prop -> {
            try {
                String name = prop.getName();
                return new KeyValProp(name.substring(0, 1).toUpperCase() + name.toLowerCase().substring(1), prop.get(user));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }).toArray(KeyValProp[]::new);

//        for (Field f :
//                userFields) {
//            Object value = f.get(user);
//            log.info("" + f.getName() + " " + value);
//            fields.put(f.getName(), value);
//        }

        model.addAttribute("userProperties", userProperties);
        return "profile";
    }

}
