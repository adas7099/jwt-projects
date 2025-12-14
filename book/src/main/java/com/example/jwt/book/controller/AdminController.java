package com.example.jwt.book.controller;

import com.example.jwt.book.entity.Review;
import com.example.jwt.book.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AdminController {
    @Autowired
    ReviewRepository reviewRepository;

    @GetMapping("/admin/reviews")
    public List<Review> getReviews(){
        return reviewRepository.findAll();
    }
    @DeleteMapping("/admin/reviews/{reviewId}")
    public ResponseEntity<String> getReviews(@PathVariable int reviewId){
        Review review= reviewRepository.findById((long)reviewId).orElseThrow();
        reviewRepository.delete(review);
        return ResponseEntity.ok("deleted Successfully");
    }

}
