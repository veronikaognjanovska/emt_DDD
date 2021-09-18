package ddd.usermanagement.domain.valueObjects;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ddd.sharedkernel.domain.base.ValueObject;
import ddd.sharedkernel.domain.financial.Currency;
import lombok.Getter;

import java.time.Instant;


@Getter
public class Order implements ValueObject {

    private final OrderId id;
    private final Instant orderedOnDate;
    private final OrderState orderState;
    private final Currency currency;

    private Order() {
        this.id = OrderId.randomId(OrderId.class);
        this.orderedOnDate = Instant.now();
        this.orderState = OrderState.SHOPPING_CART;
        this.currency = Currency.MKD;
    }

    @JsonCreator
    public Order(@JsonProperty("id") OrderId id,
                 @JsonProperty("orderedOnDate") Instant orderedOnDate,
                 @JsonProperty("orderState") OrderState orderState,
                 @JsonProperty("currency") Currency currency) {
        this.id = id;
        this.orderedOnDate = orderedOnDate;
        this.orderState = orderState;
        this.currency = currency;
    }

}

