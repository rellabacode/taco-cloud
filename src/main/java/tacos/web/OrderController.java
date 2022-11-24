package tacos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import tacos.Order;
import tacos.data.OrderRepository;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("order")
public class OrderController {

    private final OrderRepository orderRepository;
    @Autowired
    public OrderController(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }

    @GetMapping("/current")
    public String orderForm(Model model) {
        if (model.getAttribute("order") == null){
            log.info("OrderController - init new order  ");
            model.addAttribute("order", new Order());
        }
        log.info("OrderController - init order from design  "+ model.getAttribute("order"));
        return "orderForm";
    }

    @PostMapping()
    public String processOrder(@Valid  Order order, Errors errors, SessionStatus sessionStatus) {
        log.info("Posted order "+ order);

        if (errors.hasErrors()) {
            log.error("Order has errors "+ order);
            return "orderForm";
        }
        orderRepository.save(order);
        sessionStatus.setComplete();

        log.info("Order after session deleted: " + order);
        return "redirect:/";
    }

}
