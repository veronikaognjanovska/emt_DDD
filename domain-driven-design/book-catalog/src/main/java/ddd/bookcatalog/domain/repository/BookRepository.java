package ddd.bookcatalog.domain.repository;

import ddd.bookcatalog.domain.models.Book;
import ddd.bookcatalog.domain.models.BookId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, BookId> {
}
