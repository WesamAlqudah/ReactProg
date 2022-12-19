package com.example.reactprog.Srevice;

import com.example.reactprog.Domain.BookInfo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public class BookInfoService {

    public Flux<BookInfo> getBooks(){

        var books = List.of(
                new BookInfo(1,"Book one","Author One","12345"),
                new BookInfo(2,"Book two","Author 2","23456"),
                new BookInfo(3,"Book 3","Author 3","3456")
        );

        return Flux.fromIterable(books);
    }


    public Mono<BookInfo> getBookByID(long bookId){

        var book= new BookInfo(bookId,"Book one","Author One","12345");
        return Mono.just(book);
    }

}
