package tacos.data.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tacos.Order;
import tacos.User;

import java.util.List;
import java.util.Optional;

public class JpaOrderRepository implements OrderRepository {

    @Override
    public List<Order> readOrdersDeliveredInSeattle() {
        return this.readOrdersDeliveredInSeattle();
    }

    @Override
    public Page<Order> findByUserOrderByPlacedAtDesc(User user, Pageable pageable) {
        return this.findByUserOrderByPlacedAtDesc(user, pageable);
    }


    @Override
    public <S extends Order> S save(S entity) {
        return this.save(entity);
    }

    @Override
    public <S extends Order> Iterable<S> saveAll(Iterable<S> entities) {
        return this.saveAll(entities);
    }

    @Override
    public Optional<Order> findById(Long aLong) {
        return this.findById(aLong);
    }

    @Override
    public boolean existsById(Long aLong) {
        return this.existsById(aLong);
    }

    @Override
    public Iterable<Order> findAll() {
        return this.findAll();
    }

    @Override
    public Iterable<Order> findAllById(Iterable<Long> longs) {
        return this.findAllById(longs);
    }

    @Override
    public long count() {
        return this.count();
    }

    @Override
    public void deleteById(Long aLong) {
        this.deleteById(aLong);
    }

    @Override
    public void delete(Order entity) {
        this.delete(entity);
    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {
        this.deleteAllById(longs);
    }

    @Override
    public void deleteAll(Iterable<? extends Order> entities) {
        this.deleteAll(entities);
    }

    @Override
    public void deleteAll() {
        this.deleteAll();
    }
}
