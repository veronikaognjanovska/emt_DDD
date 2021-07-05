package ddd.bookcatalog.xport.rest;

import ddd.bookcatalog.domain.models.Book;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/book")
@AllArgsConstructor
public class BookResource {

    private final BookResource bookResource;

    @GetMapping
    public List<Book> getAll() {
        return bookResource.getAll();
    }

}
