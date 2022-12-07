package tacos.data.jpa;

import org.springframework.data.repository.CrudRepository;
import tacos.Order;
import tacos.User;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUserName(String userName);

    List<User> findByZip(String zip);
}
