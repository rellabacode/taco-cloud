package tacos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import tacos.Order;
import tacos.User;
import tacos.data.jpa.OrderRepository;
import tacos.data.jpa.UserRepository;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("order")
public class OrderController {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @Autowired
    public OrderController(OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/current")
    public String orderForm(Model model, @AuthenticationPrincipal User user) {
        if (model.getAttribute("order") == null) {
            log.info("OrderController - init new order  ");
            Order order = new Order();
            order.setUser(user);
            model.addAttribute("order", order);
        }

        log.info("OrderController - init order from design user  " + user.getFullName());
        log.info("OrderController - init order from design user  " + user.getUsername());
        log.info("OrderController - init order from design  " + model.getAttribute("order"));
        return "orderForm";
    }

    @PostMapping()
    public String processOrder(@Valid Order order, Errors errors, SessionStatus sessionStatus) {
        log.info("Posted order " + order);

        if (errors.hasErrors()) {
            log.error("Order has errors " + order);
            log.error("" + errors);
            return "orderForm";
        }

        orderRepository.save(order);
        sessionStatus.setComplete();

        log.info("Order after session deleted: " + order);
        return "redirect:/";
    }

}
