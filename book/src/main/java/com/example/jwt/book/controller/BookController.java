package com.example.jwt.book.controller;

import com.example.jwt.book.entity.Book;
import com.example.jwt.book.exception.ConflictException;
import com.example.jwt.book.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
public class BookController {

    @Autowired
    BookRepository bookRepository;

    @GetMapping("/books/{bookId}")
    public ResponseEntity<Book> getBookById(@PathVariable int bookId){
        Book book= bookRepository.findById((long)bookId).orElseThrow();
        return ResponseEntity.ok(book);
    }
    @GetMapping("/books")
    public List<Book> getBooks(){
        return bookRepository.findAll();
    }
    @PostMapping("/admin/books")
    public ResponseEntity<String> addBook(@RequestBody Book book) throws URISyntaxException {
        book=bookRepository.save(book);
        return ResponseEntity.created(new URI("http://localhost:8091/books/"+book.getId())).build();
    }
    @PutMapping("/admin/books/{bookId}")
    public ResponseEntity<String> updateBook(@PathVariable int bookId,@RequestBody Book updateBook) throws ConflictException {
        if(bookId<=0 || !bookRepository.existsById((long) bookId)){
            throw  new ConflictException("Book doesnt exist");
        }
        updateBook.setId((long)bookId);
        bookRepository.save(updateBook);
        return ResponseEntity.ok("");
    }
    @DeleteMapping("/admin/books/{bookId}")
    public ResponseEntity<String> deleteBook(@PathVariable int bookId){
        Book book= bookRepository.findById((long)bookId).orElseThrow();
        bookRepository.delete(book);
        return ResponseEntity.ok("Book is deleted");
    }
}
