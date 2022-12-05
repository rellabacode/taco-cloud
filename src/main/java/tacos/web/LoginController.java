package tacos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/login")
public class LoginController {

    @GetMapping
    public String loginPage(@ModelAttribute("username") String username, Model model) {
        log.info("/login with username " + username);
        if (username != null && !username.isEmpty()) {
            model.addAttribute("username", username);
        }
        return "login";
    }
}
