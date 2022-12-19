package com.example.reactprog.Srevice;

import com.example.reactprog.Domain.Review;
import reactor.core.publisher.Flux;

import java.util.List;

public class ReviewService {

    public Flux<Review> getReviews(long bookId){
        var reviewList= List.of(
                new Review(1,bookId,9.1,"Good Book"),
                new Review(1,bookId,9.1,"Good Book")
        );
        return Flux.fromIterable(reviewList);
    }
}
