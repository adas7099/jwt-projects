package com.example.jwt.book.repository;

import com.example.jwt.book.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review,Long> {
    public List<Review> findByUserId(long userId);
    public Optional<Review> findByUserIdAndId(long userId, long id);

}
