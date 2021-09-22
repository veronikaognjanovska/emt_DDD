package ddd.sharedkernel.domain.events.orders;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ddd.sharedkernel.domain.config.TopicHolder;
import ddd.sharedkernel.domain.events.DomainEvent;
import lombok.Getter;

/**
 * Domain Event for notifying a new OrderItem being created
 */
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

    @JsonCreator
    public OrderItemCreated(@JsonProperty("bookId") String bookId, @JsonProperty("quantity") int quantity,
                            @JsonProperty("topic") String topic, @JsonProperty("occurredOn") String occurredOn) {
        super(topic, occurredOn);
        this.bookId = bookId;
        this.quantity = quantity;
    }
}
