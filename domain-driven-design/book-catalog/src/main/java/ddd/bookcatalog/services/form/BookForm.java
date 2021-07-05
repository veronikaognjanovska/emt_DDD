package ddd.bookcatalog.services.form;

import ddd.sharedkernel.domain.financial.Money;
import lombok.Data;

@Data
public class BookForm {
    private String bookTitle;
    private String bookAuthor;
    private Money price;
    private int sales;

}
