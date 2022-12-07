package tacos.data.jpa;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import tacos.Order;
import tacos.User;

import java.util.LinkedList;
import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long> {

    //    List<Order> readOrdersByZipAndPlacedAtBetween(
//            String zip, Date startDate, Date endDate);
    @Query(value = "Select o from Order o where o.user.city='Seattle'")
    List<Order> readOrdersDeliveredInSeattle();

    List<Order> findByUserOrderByPlacedAtDesc(User user);
}
