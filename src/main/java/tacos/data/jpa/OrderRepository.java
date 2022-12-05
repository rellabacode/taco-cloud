package tacos.data.jpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import tacos.Order;

import java.util.Date;
import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long> {
//    List<Order> findByZip(String deliveryZip);
//    List<Order> readOrdersByZipAndPlacedAtBetween(
//            String zip, Date startDate, Date endDate);
//    @Query(value = "Select o from Order o where o.user.city='Seattle'")
//    List<Order> readOrdersDeliveredInSeattle();
}
