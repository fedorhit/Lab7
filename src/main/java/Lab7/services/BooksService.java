package Lab7.services;

import Lab7.aop.annotation.MET;
import Lab7.models.Book;
import Lab7.repositories.BooksRepository;
import Lab7.utils.exceptions.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BooksService {

    private final BooksRepository booksRepository;

    @MET
    public List<Book> findAll() {
        return booksRepository.findAll();
    }

    @MET
    public Book findById(int id) {
        Optional<Book> foundBook = booksRepository.findById(id);
        return foundBook.orElseThrow(BookNotFoundException::new);
    }

    @MET
    @Transactional
    @Deprecated
    public void create(Book book) {
        enrichBook(book);
        booksRepository.save(book);
    }

    @MET
    @Transactional
    public void update(Book book, int id) {
        book.setId(id);
        enrichBook(book);
        booksRepository.save(book);
    }

    @MET
    @Transactional
    public void delete(int id) {
        booksRepository.deleteById(id);
    }

    private void enrichBook(Book book) {
        book.setCreatedAt(LocalDateTime.now());
        book.setUpdatedAt(LocalDateTime.now());
        book.setUpdatedBy("Fedor");
    }
}
