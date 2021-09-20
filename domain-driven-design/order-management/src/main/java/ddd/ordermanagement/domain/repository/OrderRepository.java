package ddd.ordermanagement.domain.repository;

import ddd.ordermanagement.domain.model.Order;
import ddd.ordermanagement.domain.model.OrderId;
import ddd.ordermanagement.domain.model.OrderState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, OrderId> {
    List<Order> findByUsername(String username);

    Optional<Order> findByUsernameAndId(String username, OrderId id);

    Optional<Order> findByUsernameAndOrderState(String username, OrderState state);

    List<Order> findByUsernameAndOrderStateNot(String username, OrderState state);

}

