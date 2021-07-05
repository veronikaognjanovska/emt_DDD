package ddd.ordermanagement.domain.model;


import ddd.ordermanagement.domain.valueObjects.Book;
import ddd.sharedkernel.domain.base.AbstractEntity;
import ddd.sharedkernel.domain.financial.Currency;
import ddd.sharedkernel.domain.financial.Money;
import lombok.Getter;
import lombok.NonNull;

import javax.persistence.*;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="orders")
@Getter
public class Order extends AbstractEntity<OrderId> {

    private Instant orderedOnDate;

    @Enumerated(EnumType.STRING)
    private OrderState orderState;

    @Column(name="order_currency")
    @Enumerated(EnumType.STRING)
    private Currency currency;


    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<OrderItem> orderItemList = new HashSet<>();

    private Order() {
        super(OrderId.randomId(OrderId.class));
    }
    public Order(Instant now, Currency currency) {
        super(OrderId.randomId(OrderId.class));
        this.orderedOnDate = now;
        this.currency = currency;
    }

    public Money totalPrice() {
        return orderItemList.stream().map(OrderItem::subtotal).reduce(new Money(currency, 0), Money::add);
    }

    public OrderItem addItem(@NonNull Book book, int qty) {
        Objects.requireNonNull(book,"Book must not be null");
        var p = book.getBookPrice();

        var item  = new OrderItem(book.getId(),book.getBookPrice(),qty);
        orderItemList.add(item);
        return item;
    }

    public void removeItem(@NonNull OrderItemId orderItemId) {
        Objects.requireNonNull(orderItemId,"Order Item must not be null");
        orderItemList.removeIf(v->v.getId().equals(orderItemId));
    }
}