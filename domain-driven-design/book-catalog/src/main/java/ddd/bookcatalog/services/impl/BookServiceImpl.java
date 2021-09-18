package ddd.bookcatalog.services.impl;


import ddd.bookcatalog.domain.exceptions.BookNotFoundException;
import ddd.bookcatalog.domain.models.Book;
import ddd.bookcatalog.domain.models.BookDto;
import ddd.bookcatalog.domain.models.BookId;
import ddd.bookcatalog.domain.repository.BookRepository;
import ddd.bookcatalog.services.BookService;
import ddd.bookcatalog.services.form.BookForm;
import ddd.sharedkernel.domain.financial.Money;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public Optional<Book> findById(BookId id) {
        return bookRepository.findById(id);
    }

    @Override
    public Optional<Book> createBook(BookDto form) {
        Book p = Book.build(form.getBookTitle(), form.getBookAuthor(),
                Money.valueOf(form.getCurrency(),form.getAmount()), form.getQuantity());
        bookRepository.save(p);
        return Optional.of(p);
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

    @Transactional
    public void deleteBook(BookId id) throws BookNotFoundException {
        Book book = this.findById(id).orElseThrow(() -> new BookNotFoundException());
        this.bookRepository.delete(book);
    }

    @Transactional
    public Optional<Book> editBook(BookId book_id, BookDto bookDto) throws BookNotFoundException {
        Book book = this.findById(book_id).orElseThrow(() -> new BookNotFoundException());
        book.changeBookTitle(bookDto.getBookTitle());
        book.changeBookAuthor(bookDto.getBookAuthor());
        book.changeBookPrice(Money.valueOf(bookDto.getCurrency(),bookDto.getAmount()));
        book.changeBookQuantity(bookDto.getQuantity());
        return Optional.of(this.bookRepository.save(book));
    }
}
