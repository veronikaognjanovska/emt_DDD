package ddd.sharedkernel.domain.events;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ddd.sharedkernel.domain.events.orders.OrderItemCreated;
import ddd.sharedkernel.domain.events.orders.OrderItemRemoved;
import lombok.Getter;

import java.time.Instant;

@Getter
public class DomainEvent {

    private final String topic;
    private final Instant occurredOn;

    public DomainEvent(String topic) {
        this.occurredOn = Instant.now();
        this.topic = topic;
    }

    public DomainEvent(String topic, String occurredOn) {
        this.occurredOn = Instant.parse(occurredOn);
        this.topic = topic;
    }

    public static <E extends DomainEvent> E fromJson(String json, Class<E> eventClass) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, eventClass);
    }

    @JsonIgnore
    public String toJson() {
//        ObjectMapper objectMapper = new ObjectMapper();
        String output = null;
        try {
            if (this instanceof OrderItemCreated) {
                output = "{\"topic\":\"" + this.topic + "\",\"occurredOn\":\"" + this.occurredOn +
                        "\",\"bookId\":\"" + ((OrderItemCreated) this).getBookId() + "\",\"quantity\":\"" + ((OrderItemCreated) this).getQuantity() + "\"}";
            } else if (this instanceof OrderItemRemoved) {
                output = "{\"topic\":\"" + this.topic + "\",\"occurredOn\":\"" + this.occurredOn +
                        "\",\"bookId\":\"" + ((OrderItemRemoved) this).getBookId() + "\",\"quantity\":\"" + ((OrderItemRemoved) this).getQuantity() + "\"}";
            }
//            output = objectMapper.writeValueAsString(this);
//        } catch (JsonProcessingException e) {
//        }
        } catch (Exception e) {
            System.out.println(e);
        }
        return output;
    }

    public String topic() {
        return topic;
    }
}
