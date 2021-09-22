package ddd.bookcatalog.xport.rest;

import ddd.bookcatalog.domain.models.Book;
import ddd.bookcatalog.domain.models.BookDto;
import ddd.bookcatalog.domain.models.BookId;
import ddd.bookcatalog.services.BookService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
public class BookResource {

    private final BookService bookService;

    /**
     * Method that returns all the books
     *
     * @return List<Book> - list of all the books
     */
    @GetMapping
    public List<Book> getAll() {
        return bookService.getAll();
    }

    /**
     * Method that returns the book with the given id
     *
     * @param id - id of the book needed
     * @return ResponseEntity<Book> - the book with the given id
     */
    @GetMapping("/{id}")
    public ResponseEntity<Book> findBook(@PathVariable String id) {
        return this.bookService.findById(BookId.of(id))
                .map(book -> {
                    return ResponseEntity.ok().body(book);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Method that saves a new book and returns the same
     *
     * @param bookDto - object containing the data for creating the new book
     * @return ResponseEntity<Book> - the newly created book
     */
    @PostMapping("/add")
    public ResponseEntity<Book> saveBook(@RequestBody BookDto bookDto) {
        return this.bookService.createBook(bookDto)
                .map(product -> ResponseEntity.ok().body(product))
                .orElseGet(() -> ResponseEntity.badRequest().build());

    }

    /**
     * Method that edits a book and returns the same
     *
     * @param id      - id of the book that is being edited
     * @param bookDto - object containing the data for editing the book
     * @return ResponseEntity<Book> - the edited book
     */
    @PutMapping("/edit/{id}")
    public ResponseEntity<Book> editBook(@PathVariable String id, @RequestBody BookDto bookDto) {
        return this.bookService.editBook(BookId.of(id), bookDto)
                .map(product -> ResponseEntity.ok().body(product))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    /**
     * Method that deletes a book
     *
     * @param id - id of the book that is being deleted
     * @return ResponseEntity
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteBook(@PathVariable String id) {
        this.bookService.deleteBook(BookId.of(id));
        if (this.bookService.findById(BookId.of(id)).isEmpty()) return ResponseEntity.ok().build();
        return ResponseEntity.badRequest().build();
    }

}
