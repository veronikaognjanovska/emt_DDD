package ddd.bookcatalog.xport.rest;

import ddd.bookcatalog.domain.models.Book;
import ddd.bookcatalog.domain.models.BookDto;
import ddd.bookcatalog.domain.models.BookId;
import ddd.bookcatalog.services.BookService;
import ddd.bookcatalog.services.form.BookForm;
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

    @GetMapping
    public List<Book> getAll() {
        return bookService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> findBook(@PathVariable String id) {
        return this.bookService.findById(BookId.of(id))
                .map(book -> {
                    return ResponseEntity.ok().body(book);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<Book> saveBook(@RequestBody BookDto bookDto) {
        return this.bookService.createBook(bookDto)
                .map(product -> ResponseEntity.ok().body(product))
                .orElseGet(() -> ResponseEntity.badRequest().build());

    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Book> editBook(@PathVariable String id, @RequestBody BookDto productDto) {
        return this.bookService.editBook(BookId.of(id), productDto)
                .map(product -> ResponseEntity.ok().body(product))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteBook(@PathVariable String id) {
        this.bookService.deleteBook(BookId.of(id));
        if (this.bookService.findById(BookId.of(id)).isEmpty()) return ResponseEntity.ok().build();
        return ResponseEntity.badRequest().build();
    }

}
