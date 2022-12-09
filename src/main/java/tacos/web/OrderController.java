package tacos.web;

import com.google.common.base.Joiner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import tacos.Order;
import tacos.User;
import tacos.data.jpa.OrderRepository;
import tacos.data.jpa.UserRepository;

import javax.validation.Valid;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/order")
@SessionAttributes("order")
@ConfigurationProperties(prefix = "taco.orders")
public class OrderController {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    private int pageSize = 10;


    @Autowired
    public OrderController(OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/current")
    public String orderForm(Model model) {
        log.info("OrderController - init order from design  " + model.getAttribute("order"));
        return "orderForm";
    }

    @GetMapping("/list")
    public String ordersForUser(@AuthenticationPrincipal User user,
                                @RequestParam(name = "numPage", required = false, defaultValue = "0") Integer numPage,
                                Model model) {

        int nP = numPage > 0 ? numPage-1 : numPage;

        Pageable pageable = PageRequest.of(nP, pageSize);
        Page<Order> orders = orderRepository.findByUserOrderByPlacedAtDesc(user, pageable);
//        log.info("/list orders " + Joiner.on("").join(orders));

        List<Order> orderList = orders.getContent();
        List<Integer> pageList =  new LinkedList<>();
        for (int i = 2; i <= orders.getTotalPages(); i++) {
            pageList.add(i);
        }


        model.addAttribute("orders", orderList);
        model.addAttribute("pages", pageList);
        model.addAttribute("numPage", nP);
        model.addAttribute("numOrders", orders.getTotalElements());
        model.addAttribute("totalPages", orders.getTotalPages());
        model.addAttribute("pageSize", pageSize);
        return "orderList";
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

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
