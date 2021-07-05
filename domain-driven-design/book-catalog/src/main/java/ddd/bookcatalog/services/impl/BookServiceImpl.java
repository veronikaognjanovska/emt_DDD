package ddd.bookcatalog.services.impl;


import ddd.bookcatalog.domain.exceptions.BookNotFoundException;
import ddd.bookcatalog.domain.models.Book;
import ddd.bookcatalog.domain.models.BookId;
import ddd.bookcatalog.domain.repository.BookRepository;
import ddd.bookcatalog.services.BookService;
import ddd.bookcatalog.services.form.BookForm;
import lombok.AllArgsConstructor;
        import org.springframework.stereotype.Service;
        import org.springframework.transaction.annotation.Transactional;

        import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public Book findById(BookId id) {
        return bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
    }

    @Override
    public Book createBook(BookForm form) {
        Book p = Book.build(form.getBookTitle(),form.getBookAuthor(),form.getPrice(),form.getSales());
        bookRepository.save(p);
        return p;
    }

    @Override
    public Book orderItemCreated(BookId BookId, int quantity) {
        Book p = bookRepository.findById(BookId).orElseThrow(BookNotFoundException::new);
        p.removeBookQuantity(quantity);
        bookRepository.saveAndFlush(p);
        return p;
    }

    @Override
    public Book orderItemRemoved(BookId BookId, int quantity) {
        Book p = bookRepository.findById(BookId).orElseThrow(BookNotFoundException::new);
        p.addBookQuantity(quantity);
        bookRepository.saveAndFlush(p);
        return p;
    }

    @Override
    public List<Book> getAll() {
        return bookRepository.findAll();
    }
}
