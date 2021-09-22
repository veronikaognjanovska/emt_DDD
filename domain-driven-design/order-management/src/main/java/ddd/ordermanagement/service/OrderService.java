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

    /**
     * Method that makes the order with the data given in the orderForm
     *
     * @param orderForm
     * @return OrderId - id of the created order
     */
    OrderId placeOrder(OrderForm orderForm);

    /**
     * Method that returns all the orders for the user with the given username
     * Shopping Cart Included
     * The shopping cart is represented as a Order with OrderStatus ShoppingCart
     *
     * @return List<Order> - list of all the orders for the user
     */
    List<Order> findAll(String username);

    /**
     * Method that returns all the orders for the user with the given username
     * Shopping Cart Excluded
     * The shopping cart is represented as a Order with OrderStatus ShoppingCart
     *
     * @return List<Order> - list of all the orders for the user
     */
    List<Order> findAllMadeOrders(String username);

    /**
     * Method that returns the order with the given id
     *
     * @param id - id of the order needed
     * @return Optional<Order> - the order with the given id
     */
    Optional<Order> findById(OrderId id);

    /**
     * Method that adds a new OrderItem with the data given with OrderItemForm in the Order with the given OrderId
     * Publishes an event when the item is added - OrderItemCreated DomainEvent
     *
     * @param orderId
     * @param orderItemForm
     * @throws OrderIdNotExistException
     */
    void addItem(OrderId orderId, OrderItemForm orderItemForm) throws OrderIdNotExistException;

    /**
     * Method that removes OrderItem with the given orderItemId from the Order with the given OrderId
     *
     * @param orderId
     * @param orderItemId
     * @throws OrderIdNotExistException
     * @throws OrderItemIdNotExistException
     */
    void deleteItem(OrderId orderId, OrderItemId orderItemId) throws OrderIdNotExistException, OrderItemIdNotExistException;

    /**
     * Method that returns the order with the given id made by a user with the given username
     *
     * @param username
     * @param id       - id of the order needed
     * @return Optional<Order>
     */
    Optional<Order> findByUsernameAndId(String username, OrderId id);

    /**
     * Method that returns the shopping cart for the user with the given username
     * The shopping cart is represented as a Order with OrderStatus ShoppingCart
     *
     * @param username
     * @return Optional<Order>
     */
    Optional<Order> findShoppingCart(String username);

    /**
     * Method that makes the order for the user with the given username and the address information
     *
     * @param username
     * @param orderAddressForm - address information
     */
    void makeOrder(String username, OrderAddressForm orderAddressForm);

    /**
     * Method that adds new OrderItem to user's Shopping Cart
     * Publishes an event when the item is added - OrderItemCreated DomainEvent
     *
     * @param username
     * @param orderItemForm - object containing the order item information
     * @return ResponseEntity<Order>
     */
    Optional<Order> addItemToSC(String username, OrderItemForm orderItemForm);

    /**
     * Method that removes a OrderItem from the user's Shopping Cart
     * Publishes an event when the item is removed - OrderItemRemoved DomainEvent
     *
     * @param username
     * @param orderItemIdForm - object containing the order item ID information
     * @return ResponseEntity<Order>
     */
    Optional<Order> removeItemToSC(String username, OrderItemIdForm orderItemIdForm);

    /**
     * Method that creates a new Shopping Cart for the user with the given username
     * The shopping cart is represented as a Order with OrderStatus ShoppingCart
     *
     * @param username
     * @return Order - The shopping cart
     */
    Order createShoppingCart(String username);
}