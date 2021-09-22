package ddd.ordermanagement.domain.valueObjects;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ddd.sharedkernel.domain.base.ValueObject;
import ddd.sharedkernel.domain.financial.Currency;
import ddd.sharedkernel.domain.financial.Money;
import lombok.Getter;

@Getter
public class Book implements ValueObject {

    private final BookId id;
    private final String bookTitle;
    private final String bookAuthor;
    private final Money bookPrice;
    private final int bookQuantity;

    private Book() {
        this.id=BookId.randomId(BookId.class);
        this.bookTitle= "";
        this.bookAuthor= "";
        this.bookPrice = Money.valueOf(Currency.MKD,0);
        this.bookQuantity = 0;
    }

    public Book(BookId id) {
        this.id=id;
        this.bookTitle= "";
        this.bookAuthor= "";
        this.bookPrice = Money.valueOf(Currency.MKD,0);
        this.bookQuantity = 0;
    }

    @JsonCreator
    public Book(@JsonProperty("id") BookId id,
                @JsonProperty("bookTitle") String bookTitle,
                @JsonProperty("bookAuthor") String bookAuthor,
                @JsonProperty("bookPrice") Money bookPrice,
                @JsonProperty("bookQuantity") int bookQuantity) {
        this.id = id;
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.bookPrice = bookPrice;
        this.bookQuantity = bookQuantity;
    }
}

