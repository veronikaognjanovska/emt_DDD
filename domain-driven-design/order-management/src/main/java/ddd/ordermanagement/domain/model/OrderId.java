package ddd.ordermanagement.domain.model;

import ddd.sharedkernel.domain.base.DomainObjectId;
import lombok.NonNull;

public class OrderId extends DomainObjectId {

    private OrderId() {
        super(OrderId.randomId(OrderId.class).getId());
    }

    public OrderId(@NonNull String uuid) {
        super(uuid);
    }
}
