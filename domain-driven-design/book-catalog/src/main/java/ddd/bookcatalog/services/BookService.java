package ddd.bookcatalog.services;

import ddd.bookcatalog.domain.exceptions.BookNotFoundException;
import ddd.bookcatalog.domain.models.Book;
import ddd.bookcatalog.domain.models.BookDto;
import ddd.bookcatalog.domain.models.BookId;

import java.util.List;
import java.util.Optional;

public interface BookService {
    Optional<Book> findById(BookId id);

    Optional<Book> createBook(BookDto form);

    Book orderItemCreated(BookId bookId, int quantity);

    Book orderItemRemoved(BookId bookId, int quantity);

    List<Book> getAll();

    void deleteBook(BookId id) throws BookNotFoundException;

    Optional<Book> editBook(BookId book_id, BookDto bookDto) throws BookNotFoundException;
}
