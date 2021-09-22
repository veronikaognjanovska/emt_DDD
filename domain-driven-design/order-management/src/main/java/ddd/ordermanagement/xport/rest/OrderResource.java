package ddd.ordermanagement.xport.rest;

import ddd.ordermanagement.domain.model.Order;
import ddd.ordermanagement.domain.model.OrderId;
import ddd.ordermanagement.service.OrderService;
import ddd.ordermanagement.service.forms.OrderAddressForm;
import ddd.ordermanagement.service.forms.OrderItemForm;
import ddd.ordermanagement.service.forms.OrderItemIdForm;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
public class OrderResource {

    private final OrderService orderService;

    /**
     * Method that returns all the orders made by a user with the given username
     *
     * @param username
     * @return List<Order> - list of orders
     */
    @GetMapping("/{username}")
    public List<Order> getAll(@PathVariable String username) {
        return orderService.findAllMadeOrders(username);
    }

    /**
     * Method that returns all a order with the given id made by a user with the given username
     *
     * @param username
     * @param id
     * @return ResponseEntity<Order>
     */
    @GetMapping("/{username}/{id}")
    public ResponseEntity<Order> findOrder(@PathVariable String username, @PathVariable String id) {
        return this.orderService.findByUsernameAndId(username, OrderId.of(id))
                .map(order -> {
                    return ResponseEntity.ok().body(order);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Method that returns all the shopping cart for a user with the given username
     * The shopping cart is represented as a Order with OrderStatus ShoppingCart
     *
     * @param username
     * @return ResponseEntity<Order>
     */
    @GetMapping("/{username}/items")
    public ResponseEntity<Order> getItems(@PathVariable String username) {
        return this.orderService.findShoppingCart(username)
                .map(order -> {
                    return ResponseEntity.ok().body(order);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Method that makes an order for a user with the given username
     *
     * @param username
     * @param orderAddressForm - object containing the address information
     * @return ResponseEntity<Order>
     */
    @PutMapping("/{username}/makeorder")
    public ResponseEntity<Order> makeOrder(@PathVariable String username,
                                           @RequestBody OrderAddressForm orderAddressForm) {
        try {
            this.orderService.makeOrder(username, orderAddressForm);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Method that adds new OrderItem to user's Shopping Cart
     *
     * @param username
     * @param orderItemForm - object containing the order item information
     * @return ResponseEntity<Order>
     */
    @PutMapping("/{username}/add")
    public ResponseEntity<Order> addToShoppingCartBook(@PathVariable String username,
                                                       @RequestBody OrderItemForm orderItemForm) {
        return this.orderService.addItemToSC(username, orderItemForm)
                .map(order -> {
                    return ResponseEntity.ok().body(order);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Method that removes a OrderItem from the user's Shopping Cart
     *
     * @param username
     * @param orderItemIdForm - object containing the order item ID information
     * @return ResponseEntity<Order>
     */
    @PutMapping("/{username}/remove")
    public ResponseEntity<Order> onRemoveItemFromSC(@PathVariable String username, @RequestBody OrderItemIdForm orderItemIdForm) {
        return this.orderService.removeItemToSC(username, orderItemIdForm)
                .map(order -> {
                    return ResponseEntity.ok().body(order);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
