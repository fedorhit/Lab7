package SpringBootMVCREST.controllers;

import SpringBootMVCREST.DTO.BookDTO;
import SpringBootMVCREST.models.Book;
import SpringBootMVCREST.services.BooksService;
import SpringBootMVCREST.utils.exceptions.*;
///import SpringBootMVCREST.utils.exceptions.BookErrorResponse;
///import SpringBootMVCREST.utils.exceptions.BookNotCreatedException;
///import SpringBootMVCREST.utils.exceptions.BookNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BooksController {

    private final BooksService booksService;
    private final ModelMapper modelMapper;

    @GetMapping()
    public List<BookDTO> findAllBook() {
        return booksService.findAll().stream()
                .map(this::convertToBookDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public BookDTO findBookById(@PathVariable("id") int id) {
        return convertToBookDTO(booksService.findById(id));
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createBook(@RequestBody @Valid BookDTO bookDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error: errors) {
                errorMsg.append(error.getField()).append(" - ").append(error.getDefaultMessage()).append(";");
            }

            throw new BookNotCreatedException(errorMsg.toString());
        }

        booksService.create(convertToBook(bookDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping("/up/{id}")
    public ResponseEntity<HttpStatus> updateBook(@RequestBody BookDTO book, @PathVariable("id") int id) {
        booksService.update(convertToBook(book), id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/del/{id}")
    public ResponseEntity<HttpStatus> deleteBook(@PathVariable("id") int id) {
        booksService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<BookErrorResponse> handleException(BookNotFoundException e) {
        BookErrorResponse response = new BookErrorResponse(
                "Book not found",
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<BookErrorResponse> handleException(BookNotCreatedException e) {
        BookErrorResponse response = new BookErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private Book convertToBook(BookDTO bookDTO) {
        return modelMapper.map(bookDTO, Book.class);
    }

    private BookDTO convertToBookDTO(Book book) {
        return modelMapper.map(book, BookDTO.class);
    }
}
