package ddd.bookcatalog.services;

import ddd.bookcatalog.domain.models.Book;
import ddd.bookcatalog.domain.models.BookId;
import ddd.bookcatalog.services.form.BookForm;

import java.util.List;

public interface BookService {
    Book findById(BookId id);

    Book createBook(BookForm form);

    Book orderItemCreated(BookId bookId, int quantity);

    Book orderItemRemoved(BookId bookId, int quantity);

    List<Book> getAll();

}
