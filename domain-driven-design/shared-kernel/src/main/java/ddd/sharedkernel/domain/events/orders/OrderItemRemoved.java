package ddd.sharedkernel.domain.events.orders;


import ddd.sharedkernel.domain.config.TopicHolder;
import ddd.sharedkernel.domain.events.DomainEvent;
import lombok.Getter;

@Getter
public class OrderItemRemoved extends DomainEvent {

    private String bookId;
    private int quantity;

    public OrderItemRemoved(String topic) {
        super(TopicHolder.TOPIC_ORDER_ITEM_REMOVED);
    }

    public OrderItemRemoved(String topic, String bookId, int quantity) {
        super(TopicHolder.TOPIC_ORDER_ITEM_REMOVED);
        this.bookId = bookId;
        this.quantity = quantity;
    }
}

