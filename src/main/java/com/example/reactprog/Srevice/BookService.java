package com.example.reactprog.Srevice;


import com.example.reactprog.Domain.Book;
import com.example.reactprog.Domain.Review;
import com.example.reactprog.Exception.BookExceeption;
import lombok.extern.slf4j.Slf4j;
import reactor.core.Exceptions;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.List;

@Slf4j
public class BookService {

    private BookInfoService bookInfoService;
    private ReviewService reviewService;

    public BookService(BookInfoService bookInfoService, ReviewService reviewService){
        this.bookInfoService=bookInfoService;
        this.reviewService=reviewService;
    }

    public Flux<Book> getBooks(){
        var allBooks= bookInfoService.getBooks();
            return  allBooks
                    .flatMap(bookInfo -> {
                        Mono<List<Review>> reviews=
                                reviewService.getReviews(bookInfo.getBookId()).collectList();
                        return reviews
                                .map(review ->new Book(bookInfo,review));
                    })
                    .onErrorMap(throwable -> {
                        log.error("Exception is: "+ throwable);
                        return new BookExceeption("EXc err");
                    })
                                .log();
    }


    public Mono<Book> getBookById(long bookId){
        var book= bookInfoService.getBookByID(bookId);
        var review = reviewService.getReviews(bookId).collectList();
        return book.zipWith(review,(b,r)-> new Book(b,r));
    }

    public Flux<Book> getBooksRetry(){
        var allBooks= bookInfoService.getBooks();
        return  allBooks
                .flatMap(bookInfo -> {
                    Mono<List<Review>> reviews=
                            reviewService.getReviews(bookInfo.getBookId()).collectList();
                    return reviews
                            .map(review ->new Book(bookInfo,review));
                })
                .onErrorMap(throwable -> {
                    log.error("Exception is: "+ throwable);
                    return new BookExceeption("EXc err");
                })
                .retry(3)
                .log();
    }

    public Flux<Book> getBooksRetryWhen(){

        var retrySpecs= Retry.backoff(3, Duration.ofMillis(1000)).onRetryExhaustedThrow(((retryBackoffSpec, retrySignal) ->
                Exceptions.propagate(retrySignal.failure())
                ));

        var allBooks= bookInfoService.getBooks();
        return  allBooks
                .flatMap(bookInfo -> {
                    Mono<List<Review>> reviews=
                            reviewService.getReviews(bookInfo.getBookId()).collectList();
                    return reviews
                            .map(review ->new Book(bookInfo,review));
                })
                .onErrorMap(throwable -> {
                    log.error("Exception is: "+ throwable);
                    return new BookExceeption("EXc err");
                })
                .retryWhen(retrySpecs)
                .log();
    }


}
