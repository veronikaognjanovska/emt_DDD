package ddd.ordermanagement.service;


import ddd.ordermanagement.domain.exceptions.OrderIdNotExistException;
import ddd.ordermanagement.domain.exceptions.OrderItemIdNotExistException;
import ddd.ordermanagement.domain.model.Order;
import ddd.ordermanagement.domain.model.OrderId;
import ddd.ordermanagement.domain.model.OrderItemId;
import ddd.ordermanagement.service.forms.OrderAddressForm;
import ddd.ordermanagement.service.forms.OrderForm;
import ddd.ordermanagement.service.forms.OrderItemForm;
import ddd.ordermanagement.service.forms.OrderItemIdForm;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    OrderId placeOrder(OrderForm orderForm);

    List<Order> findAll(String username);

    List<Order> findAllMadeOrders(String username);

    Optional<Order> findById(OrderId id);

    void addItem(OrderId orderId, OrderItemForm orderItemForm) throws OrderIdNotExistException;

    void deleteItem(OrderId orderId, OrderItemId orderItemId) throws OrderIdNotExistException, OrderItemIdNotExistException;

    Optional<Order> findByUsernameAndId(String username, OrderId id);

    Optional<Order> findShoppingCart(String username);

    void makeOrder(String username, OrderAddressForm orderAddressForm);

    Optional<Order> addItemToSC(String username, OrderItemForm orderItemForm);

    Optional<Order> removeItemToSC(String username, OrderItemIdForm orderItemIdForm);

    Order createShoppingCart(String username);
}
