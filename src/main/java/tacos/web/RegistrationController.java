package tacos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tacos.User;
import tacos.data.jpa.UserRepository;
import tacos.security.RegistrationForm;

import java.util.HashMap;
import java.util.Map;

@Controller
@Slf4j
@RequestMapping("/register")
public class RegistrationController {
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationController(UserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String registerForm(Model model) {
        if (model.getAttribute("form") == null) {
            log.info("RegistrationController - init new form");
            RegistrationForm form = new RegistrationForm();
            model.addAttribute("form", form);
        }

        if (model.getAttribute("formErrors") == null) {
            model.addAttribute("formErrors", new HashMap());
        }

        return "registration";
    }

    @PostMapping
    public String processRegistration(Model model, @ModelAttribute(name = "form") RegistrationForm form,
                                      RedirectAttributes ra) {
        log.info("POST /register Saving form " + form);
        User user = userRepo.findByUserName(form.getUsername());
        form.setNewUser(user == null);

        Map<String, String> errors = form.getErrors();
        log.info("POST /register errors " + errors);

        log.info(""+user);

        if (errors.size() > 0) {
            model.addAttribute("formErrors", errors);
            return "registration";
        }

        userRepo.save(form.toUser(passwordEncoder));
        ra.addFlashAttribute("username", form.getUsername());

        return "redirect:/login";
    }
}
