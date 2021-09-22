package ddd.usermanagement.domain.valueObjects;

import ddd.sharedkernel.domain.base.DomainObjectId;

import javax.persistence.Embeddable;

@Embeddable
public class OrderId extends DomainObjectId {

    private OrderId() {
        super(OrderId.randomId(OrderId.class).getId());
    }

    public OrderId(String uuid) {
        super(uuid);
    }

    public static OrderId of(String uuid) {
        OrderId p = new OrderId(uuid);
        return p;
    }
}
