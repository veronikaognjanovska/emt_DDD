package ddd.sharedkernel.domain.events.orders;


import ddd.sharedkernel.domain.config.TopicHolder;
import ddd.sharedkernel.domain.events.DomainEvent;
import lombok.Getter;

@Getter
public class OrderItemCreated extends DomainEvent {

    private String bookId;
    private int quantity;

    public OrderItemCreated(String topic) {
        super(TopicHolder.TOPIC_ORDER_ITEM_CREATED);
    }

    public OrderItemCreated(String bookId, int quantity) {
        super(TopicHolder.TOPIC_ORDER_ITEM_CREATED);
        this.bookId = bookId;
        this.quantity = quantity;
    }
}