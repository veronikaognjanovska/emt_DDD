package ddd.bookcatalog.config;


import ddd.bookcatalog.domain.models.Book;
import ddd.bookcatalog.domain.repository.BookRepository;
import ddd.sharedkernel.domain.financial.Currency;
import ddd.sharedkernel.domain.financial.Money;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Component
@AllArgsConstructor
public class DataInitializer {

    private final BookRepository bookRepository;

    /**
     * Initial data if the database is empty
     */
    @PostConstruct
    public void initData() {
        Book p1 = Book.build("Shadow and Bone", "Leigh Bardugo", Money.valueOf(Currency.MKD, 500), 10);
        Book p2 = Book.build("Siege and Storm", "Leigh Bardugo", Money.valueOf(Currency.MKD, 100), 5);
        Book p3 = Book.build("Ruin and Rising", "Leigh Bardugo", Money.valueOf(Currency.MKD, 100), 5);
        if (bookRepository.findAll().isEmpty()) {
            bookRepository.saveAll(Arrays.asList(p1, p2, p3));
        }
    }
}
