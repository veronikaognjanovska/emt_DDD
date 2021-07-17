package ddd.bookcatalog.xport.events;


import ddd.bookcatalog.domain.models.BookId;
import ddd.bookcatalog.services.BookService;
import ddd.sharedkernel.domain.config.TopicHolder;
import ddd.sharedkernel.domain.events.DomainEvent;
import ddd.sharedkernel.domain.events.orders.OrderItemCreated;
import ddd.sharedkernel.domain.events.orders.OrderItemRemoved;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BookEventListener {

    private final BookService bookService;

    @KafkaListener(topics = TopicHolder.TOPIC_ORDER_ITEM_CREATED, groupId = "BookCatalog")
    public void consumeOrderItemCreatedEvent(@Payload(required = false) String jsonMessage) {
        try {
            OrderItemCreated event = DomainEvent.fromJson(jsonMessage, OrderItemCreated.class);
            bookService.orderItemCreated(BookId.of(event.getBookId()), event.getQuantity());
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    @KafkaListener(topics = TopicHolder.TOPIC_ORDER_ITEM_REMOVED, groupId = "BookCatalog")
    public void consumeOrderItemRemovedEvent(@Payload(required = false) String jsonMessage) {
        try {
            OrderItemRemoved event = DomainEvent.fromJson(jsonMessage, OrderItemRemoved.class);
            bookService.orderItemRemoved(BookId.of(event.getBookId()), event.getQuantity());
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

