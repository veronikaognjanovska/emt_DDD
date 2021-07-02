package ddd.bookcatalog.domain.models;


import ddd.sharedkernel.domain.base.AbstractEntity;
import ddd.sharedkernel.domain.financial.Money;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "book")
@Getter
public class Book extends AbstractEntity<BookId> {

    private String bookTitle;
    private String bookAuthor;

    private int bookQuantity = 0;

    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "price_amount")),
            @AttributeOverride(name = "currency", column = @Column(name = "price_currency"))
    })
    private Money bookPrice;

    private Book() {
        super(BookId.randomId(BookId.class));
    }

    public static Book build(String bookTitle, String bookAuthor, Money price, int bookQuantity) {
        Book p = new Book();
        p.bookPrice = price;
        p.bookTitle = bookTitle;
        p.bookAuthor = bookAuthor;
        p.bookQuantity = bookQuantity;
        return p;
    }

//    public void addSales(int qty) {
//        this.sales = this.sales - qty;
//    }
//
//    public void removeSales(int qty) {
//        this.sales -= qty;
//    }

    public void addBookQuantity(int qty) {
        this.bookQuantity += qty;
    }

    public void removeBookQuantity(int qty) {
        this.bookQuantity -= qty;
    }
}
