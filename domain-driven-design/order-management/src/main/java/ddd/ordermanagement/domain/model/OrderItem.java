package ddd.ordermanagement.domain.model;


import ddd.ordermanagement.domain.valueObjects.BookId;
import ddd.sharedkernel.domain.base.AbstractEntity;
import ddd.sharedkernel.domain.base.DomainObjectId;
import ddd.sharedkernel.domain.financial.Money;
import lombok.Getter;
import org.springframework.lang.NonNull;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "order_item")
@Getter
public class OrderItem extends AbstractEntity<OrderItemId> {

    private Money orderItemPrice;

    @Column(name = "qty", nullable = false)
    private int quantity;

    @AttributeOverride(name = "id", column = @Column(name = "book_id", nullable = false))
    private BookId bookId;

    private OrderItem() {
        super(DomainObjectId.randomId(OrderItemId.class));
    }

    public OrderItem(@NonNull BookId bookId, @NonNull Money itemPrice, int qty) {
        super(DomainObjectId.randomId(OrderItemId.class));
        this.bookId = bookId;
        this.orderItemPrice = itemPrice;
        this.quantity = qty;
    }

    public Money subtotal() {
        return orderItemPrice.multiply(quantity);
    }
}
