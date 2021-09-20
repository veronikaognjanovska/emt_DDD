package ddd.ordermanagement.xport.rest;

import ddd.ordermanagement.domain.model.Order;
import ddd.ordermanagement.domain.model.OrderId;
import ddd.ordermanagement.service.OrderService;
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

    @GetMapping("/{username}")
    public List<Order> getAll(@PathVariable String username) {
        return orderService.findAllMadeOrders(username);
    }

    @GetMapping("/{username}/{id}")
    public ResponseEntity<Order> findOrder(@PathVariable String username, @PathVariable String id) {
        return this.orderService.findByUsernameAndId(username, OrderId.of(id))
                .map(order -> {
                    return ResponseEntity.ok().body(order);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{username}/items")
    public ResponseEntity<Order> getItems(@PathVariable String username) {
        return this.orderService.findShoppingCart(username)
                .map(order -> {
                    return ResponseEntity.ok().body(order);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{username}/makeorder")
    public ResponseEntity<Order> makeOrder(@PathVariable String username) {
        try {
            this.orderService.makeOrder(username);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{username}/add/{bookId}")
    public ResponseEntity<Order> addToShoppingCartBook(@PathVariable String username, @PathVariable String bookId) {
        return this.orderService.addItemToSC(username, bookId)
                .map(order -> {
                    return ResponseEntity.ok().body(order);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{username}/remove/{bookId}")
    public ResponseEntity<Order> onRemoveItemFromSC(@PathVariable String username, @PathVariable String bookId) {
        return this.orderService.removeItemToSC(username, bookId)
                .map(order -> {
                    return ResponseEntity.ok().body(order);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
