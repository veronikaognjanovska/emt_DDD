package ddd.sharedkernel.domain.events.orders;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ddd.sharedkernel.domain.config.TopicHolder;
import ddd.sharedkernel.domain.events.DomainEvent;
import lombok.Getter;

@Getter
public class UserRegistered extends DomainEvent {

    private final String username;

//    public UserRegistered(String topic) {
//        super(TopicHolder.TOPIC_USER_REGISTERED);
//    }

    public UserRegistered(String username) {
        super(TopicHolder.TOPIC_USER_REGISTERED);
        this.username = username;
    }

    @JsonCreator
    public UserRegistered(@JsonProperty("username") String username,
                          @JsonProperty("topic") String topic, @JsonProperty("occurredOn") String occurredOn) {
        super(topic, occurredOn);
        this.username = username;
    }
}