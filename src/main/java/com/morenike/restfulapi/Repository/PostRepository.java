package com.morenike.restfulapi.Repository;

import com.morenike.restfulapi.Model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findByTitleContaining(String keyword);
}
