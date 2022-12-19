package com.example.reactprog.Srevice;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

class BookServiceTest {

    private BookInfoService bookInfoService = new BookInfoService();

    private ReviewService reviewService= new ReviewService();

    private  BookService bookService= new BookService(bookInfoService,reviewService);


    @Test
    void getBooks(){

        var books = bookService.getBooks();
        StepVerifier.create(books)
                .assertNext(book -> {
                    assertEquals("Book one",book.getBookInfo().getTitle());
                    assertEquals(2,book.getReviewList().size());
                })
                .assertNext(book -> {
                    assertEquals("Book two",book.getBookInfo().getTitle());
                    assertEquals(2,book.getReviewList().size());
                })
                .assertNext(book -> {
                    assertEquals("Book 3",book.getBookInfo().getTitle());
                    assertEquals(2,book.getReviewList().size());
                })
                .verifyComplete();

    }

    @Test
    void getBookById() {
        var book = bookService.getBookById(1).log();
        StepVerifier.create(book)
                .assertNext(b -> {
                    assertEquals("Book one",b.getBookInfo().getTitle());
                    assertEquals(2,b.getReviewList().size());
                }).verifyComplete();

    }


}