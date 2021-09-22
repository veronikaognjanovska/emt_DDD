package ddd.bookcatalog.services.form;

import ddd.sharedkernel.domain.financial.Money;
import lombok.Data;

/**
 * BookForm Object for the data received though the API
 */
@Data
public class BookForm {
    private String bookTitle;
    private String bookAuthor;
    private Money price;
    private int quantity;
}
