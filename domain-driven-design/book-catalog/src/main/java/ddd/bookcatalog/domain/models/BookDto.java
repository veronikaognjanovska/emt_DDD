package ddd.bookcatalog.domain.models;

import ddd.sharedkernel.domain.financial.Currency;
import lombok.Data;

@Data
public class BookDto {
    private String bookTitle;
    private String bookAuthor;
    private Integer amount;
    private Currency currency;
    private int quantity;
}
