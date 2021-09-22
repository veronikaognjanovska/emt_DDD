package ddd.bookcatalog.services;

import ddd.bookcatalog.domain.exceptions.BookNotFoundException;
import ddd.bookcatalog.domain.models.Book;
import ddd.bookcatalog.domain.models.BookDto;
import ddd.bookcatalog.domain.models.BookId;

import java.util.List;
import java.util.Optional;

public interface BookService {
    /**
     * Method that returns the book with the given id
     *
     * @param id - id of the book needed
     * @return Optional<Book> - the book with the given id
     */
    Optional<Book> findById(BookId id);

    /**
     * Method that saves a new book and returns the same
     *
     * @param form - object containing the data for creating the new book
     * @return Optional<Book>
     */
    Optional<Book> createBook(BookDto form);

    /**
     * Method that creates a OrderItem
     *
     * @param bookId   - id of the book in the order item
     * @param quantity - quantity for the book
     * @return Book - the book with the given id
     */
    Book orderItemCreated(BookId bookId, int quantity);

    /**
     * Method that removes a OrderItem
     *
     * @param bookId   - id of the book in the order item
     * @param quantity - quantity for the book
     * @return Book - the book with the given id
     */
    Book orderItemRemoved(BookId bookId, int quantity);

    /**
     * Method that returns all the books
     *
     * @return List<Book> - list of all the books
     */
    List<Book> getAll();

    /**
     * Method that deletes a book
     *
     * @param id - id of the book that is being deleted
     */
    void deleteBook(BookId id) throws BookNotFoundException;

    /**
     * Method that edits a book and returns the same
     *
     * @param book_id - id of the book that is being edited
     * @param bookDto - object containing the data for editing the book
     * @return ResponseEntity<Book> - the edited book
     * @throws BookNotFoundException
     */
    Optional<Book> editBook(BookId book_id, BookDto bookDto) throws BookNotFoundException;
}
