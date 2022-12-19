package com.example.reactprog.Srevice;

import com.example.reactprog.Exception.BookExceeption;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BookServiceMockTest {

    @Mock
    private BookInfoService bookInfoService;
    @Mock
    private ReviewService reviewService;

    @InjectMocks
    private BookService bookService;

    @Test
    void getBooks() {

        Mockito.when(bookInfoService.getBooks())
                .thenCallRealMethod();

        Mockito.when(reviewService.getReviews(Mockito.anyLong()))
                .thenCallRealMethod();

        var books= bookService.getBooks();

        StepVerifier.create(books)
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    void getBooksOnError() {

        Mockito.when(bookInfoService.getBooks())
                .thenCallRealMethod();

        Mockito.when(reviewService.getReviews(Mockito.anyLong()))
                .thenThrow(new IllegalStateException("excp using test"));

        var books= bookService.getBooks();

        StepVerifier.create(books)
                .expectError(BookExceeption.class)
                .verify();
    }

    @Test
    void getBooksErrorRetry() {

        Mockito.when(bookInfoService.getBooks())
                .thenCallRealMethod();

        Mockito.when(reviewService.getReviews(Mockito.anyLong()))
                .thenThrow(new IllegalStateException("excp using test"));

        var books= bookService.getBooksRetry();

        StepVerifier.create(books)
                .expectError(BookExceeption.class)
                .verify();
    }

    @Test
    void getBooksRetryWhen() {

        Mockito.when(bookInfoService.getBooks())
                .thenCallRealMethod();

        Mockito.when(reviewService.getReviews(Mockito.anyLong()))
                .thenThrow(new IllegalStateException("excp using test"));

        var books= bookService.getBooksRetryWhen();

        StepVerifier.create(books)
                .expectError(BookExceeption.class)
                .verify();
    }
}