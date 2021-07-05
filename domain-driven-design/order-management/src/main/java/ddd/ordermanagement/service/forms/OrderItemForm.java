package ddd.ordermanagement.service.forms;

import ddd.ordermanagement.domain.valueObjects.Book;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class OrderItemForm {

    @NotNull
    private Book book;

    @Min(1)
    private int quantity = 1;
}
