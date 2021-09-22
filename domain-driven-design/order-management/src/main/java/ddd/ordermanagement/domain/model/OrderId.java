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

    public static OrderId of(String uuid) {
        OrderId p = new OrderId(uuid);
        return p;
    }
}
