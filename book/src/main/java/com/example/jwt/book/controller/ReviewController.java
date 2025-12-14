package com.example.jwt.book.controller;

import com.example.jwt.book.entity.Book;
import com.example.jwt.book.entity.Review;
import com.example.jwt.book.entity.User;
import com.example.jwt.book.model.ReviewRequest;
import com.example.jwt.book.repository.BookRepository;
import com.example.jwt.book.repository.ReviewRepository;
import com.example.jwt.book.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
public class ReviewController {
    @Autowired
    ReviewRepository  reviewRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    UserRepository userRepository;

    @PostMapping("/reviews/{bookId}")
    public void addReview(Principal principal, @PathVariable int bookId,@RequestBody ReviewRequest request){
        User user=userRepository.findByUsername(principal.getName()).orElseThrow(()->new UsernameNotFoundException("User not Found"));
        Book book=bookRepository.findById((long)bookId).orElseThrow();
        Review review=new Review(0L,request.getRating(),request.getComment(),book,user);
        reviewRepository.save(review);
    }
    @GetMapping("/reviews/my")
    public List<Review> getReview(Principal principal){
        User user=userRepository.findByUsername(principal.getName()).orElseThrow(()->new UsernameNotFoundException("User not Found"));
        List<Review> reviews=reviewRepository.findByUserId(user.getId());
        return reviews;
    }
    @PutMapping("/reviews/{reviewId}")
    public ResponseEntity<String> updateReview(Principal principal , @PathVariable int reviewId, @RequestBody ReviewRequest request){
        User user=userRepository.findByUsername(principal.getName()).orElseThrow(()->new UsernameNotFoundException("User not Found"));
        Review review=reviewRepository.findByUserIdAndId(user.getId(), reviewId).orElseThrow();
        review.setRating(request.getRating());
        review.setComment(request.getComment());
        reviewRepository.save(review);
        return ResponseEntity.ok("updated successfully");
    }
    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<String> deleteReview(Principal principal , @PathVariable int reviewId){
        User user=userRepository.findByUsername(principal.getName()).orElseThrow(()->new UsernameNotFoundException("User not Found"));
        Review review=reviewRepository.findByUserIdAndId(user.getId(), reviewId).orElseThrow();
        reviewRepository.delete(review);
        return ResponseEntity.ok("deleted successfully");
    }
}
