package ddd.sharedkernel.infra;


import ddd.sharedkernel.domain.events.DomainEvent;

public interface DomainEventPublisher {
    void publish(DomainEvent event);
}

