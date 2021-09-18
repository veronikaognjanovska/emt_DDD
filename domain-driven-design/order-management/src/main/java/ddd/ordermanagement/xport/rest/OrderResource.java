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

    @GetMapping
    public List<Order> getAll() {
        return orderService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> findOrder(@PathVariable String id) {
        return this.orderService.findById(OrderId.of(id))
                .map(book -> {
                    return ResponseEntity.ok().body(book);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
