package ddd.ordermanagement.xport.events;


import ddd.ordermanagement.service.OrderService;
import ddd.sharedkernel.domain.config.TopicHolder;
import ddd.sharedkernel.domain.events.DomainEvent;
import ddd.sharedkernel.domain.events.orders.UserRegistered;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

/**
 * Listener endpoint for the Order Management Module
 */
@Service
@AllArgsConstructor
public class OrderEventListener {

    private final OrderService orderService;

    /**
     * Method that listens on the TOPIC_USER_REGISTERED topic
     *
     * @param jsonMessage - event message
     */
    @KafkaListener(topics = TopicHolder.TOPIC_USER_REGISTERED, groupId = "OrderManagement")
    public void consumeUserRegisteredEvent(@Payload(required = false) String jsonMessage) {
        try {
            UserRegistered event = DomainEvent.fromJson(jsonMessage, UserRegistered.class);
            orderService.createShoppingCart(event.getUsername());
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}

