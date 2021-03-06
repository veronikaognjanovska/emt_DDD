package ddd.ordermanagement.service.impl;

import ddd.ordermanagement.domain.exceptions.OrderIdNotExistException;
import ddd.ordermanagement.domain.exceptions.OrderItemIdNotExistException;
import ddd.ordermanagement.domain.model.*;
import ddd.ordermanagement.domain.repository.OrderRepository;
import ddd.ordermanagement.service.OrderService;
import ddd.ordermanagement.service.forms.OrderAddressForm;
import ddd.ordermanagement.service.forms.OrderForm;
import ddd.ordermanagement.service.forms.OrderItemForm;
import ddd.ordermanagement.service.forms.OrderItemIdForm;
import ddd.sharedkernel.domain.events.orders.OrderItemCreated;
import ddd.sharedkernel.domain.events.orders.OrderItemRemoved;
import ddd.sharedkernel.domain.financial.Currency;
import ddd.sharedkernel.infra.DomainEventPublisher;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
@Transactional
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final DomainEventPublisher domainEventPublisher;
    private final Validator validator;

    @Override
    public OrderId placeOrder(OrderForm orderForm) {
        Objects.requireNonNull(orderForm, "Order must not be null.");
        var constraintViolations = validator.validate(orderForm);
        if (constraintViolations.size() > 0) {
            throw new ConstraintViolationException("The order form is not valid", constraintViolations);
        }
        var newOrder = orderRepository.saveAndFlush(toDomainObject(orderForm));
        newOrder.getOrderItemList().forEach(item -> domainEventPublisher.publish(new OrderItemCreated(item.getBookId().getId(), item.getQuantity())));
        return newOrder.getId();
    }

    @Override
    public List<Order> findAll(String username) {
        return orderRepository.findByUsername(username);
    }

    @Override
    public List<Order> findAllMadeOrders(String username) {
        return orderRepository.findByUsernameAndOrderStateNot(username, OrderState.SHOPPING_CART);
    }

    @Override
    public Optional<Order> findByUsernameAndId(String username, OrderId id) {
        return orderRepository.findByUsernameAndId(username, id);
    }

    @Override
    public Optional<Order> findById(OrderId id) {
        return orderRepository.findById(id);
    }

    @Override
    public Optional<Order> findShoppingCart(String username) {
        return orderRepository.findByUsernameAndOrderState(username, OrderState.SHOPPING_CART);
    }

    @Override
    @Transactional
    public void makeOrder(String username, OrderAddressForm orderAddressForm) {
        Optional<Order> order = this.findShoppingCart(username);
        if (order.isPresent()) {
            Order o = order.get();
            o.makeOrder(orderAddressForm.getAddress());
            orderRepository.saveAndFlush(o);
            this.createShoppingCart(username);
        }
    }

    @Override
    public void addItem(OrderId orderId, OrderItemForm orderItemForm) throws OrderIdNotExistException {
        Order order = orderRepository.findById(orderId).orElseThrow(OrderIdNotExistException::new);
        order.addItem(orderItemForm.getBook(), orderItemForm.getQuantity());
        orderRepository.saveAndFlush(order);
        domainEventPublisher.publish(new OrderItemCreated(orderItemForm.getBook().getId().getId(), orderItemForm.getQuantity()));
    }

    @Override
    public void deleteItem(OrderId orderId, OrderItemId orderItemId) throws OrderIdNotExistException, OrderItemIdNotExistException {
        Order order = orderRepository.findById(orderId).orElseThrow(OrderIdNotExistException::new);
        order.removeItem(orderItemId);
        orderRepository.saveAndFlush(order);
    }

    @Override
    @Transactional
    public Optional<Order> addItemToSC(String username, OrderItemForm orderItemForm) {
        Order order = this.findShoppingCart(username).orElseThrow(OrderIdNotExistException::new);
        order.addItem(orderItemForm.getBook(), orderItemForm.getQuantity());
        orderRepository.saveAndFlush(order);
        domainEventPublisher.publish(new OrderItemCreated(orderItemForm.getBook().getId().getId(), orderItemForm.getQuantity()));
        return Optional.of(order);
    }

    @Override
    @Transactional
    public Optional<Order> removeItemToSC(String username, OrderItemIdForm orderItemIdForm) {
        Order order = this.findShoppingCart(username).orElseThrow(OrderIdNotExistException::new);
        OrderItem orderItem = order.getOrderItemList().stream().filter(item -> item.getId().getId().equals(orderItemIdForm.getOrderItemId().getId())).findFirst().orElseThrow(OrderIdNotExistException::new);
        order.removeItem(orderItem.getId());
        orderRepository.saveAndFlush(order);
        domainEventPublisher.publish(new OrderItemRemoved(orderItem.getBookId().getId(), orderItem.getQuantity()));
        return Optional.of(order);
    }

    @Override
    @Transactional
    public Order createShoppingCart(String username) {
        OrderForm orderForm = new OrderForm();
        orderForm.setCurrency(Currency.MKD);
        orderForm.setItems(Collections.emptyList());
        return orderRepository.saveAndFlush(toDomainObject(orderForm, username));
    }

    private Order toDomainObject(OrderForm orderForm) {
        var order = new Order(Instant.now(), orderForm.getCurrency());
        orderForm.getItems().forEach(item -> order.addItem(item.getBook(), item.getQuantity()));
        return order;
    }

    private Order toDomainObject(OrderForm orderForm, String username) {
        var order = new Order(Instant.now(), orderForm.getCurrency(), username);
        orderForm.getItems().forEach(item -> order.addItem(item.getBook(), item.getQuantity()));
        return order;
    }
}
