package ddd.ordermanagement.service;


import ddd.ordermanagement.domain.exceptions.OrderIdNotExistException;
import ddd.ordermanagement.domain.exceptions.OrderItemIdNotExistException;
import ddd.ordermanagement.domain.model.Order;
import ddd.ordermanagement.domain.model.OrderId;
import ddd.ordermanagement.domain.model.OrderItemId;
import ddd.ordermanagement.service.forms.OrderForm;
import ddd.ordermanagement.service.forms.OrderItemForm;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    OrderId placeOrder(OrderForm orderForm);

    List<Order> findAll();

    Optional<Order> findById(OrderId id);

    void addItem(OrderId orderId, OrderItemForm orderItemForm) throws OrderIdNotExistException;

    void deleteItem(OrderId orderId, OrderItemId orderItemId) throws OrderIdNotExistException, OrderItemIdNotExistException;


}
