package com.example.reactprog.Domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Review {

    private long reviewID;
    private long bookID;
    private double ratings;
    private String comments;
}
