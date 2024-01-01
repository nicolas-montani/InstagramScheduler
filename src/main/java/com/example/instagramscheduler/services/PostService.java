package com.example.instagramscheduler.services;

import com.example.instagramscheduler.model.Post;
import com.example.instagramscheduler.repository.PostRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import org.springframework.stereotype.Service;

@Service
public class PostService {

        private final PostRepository repository;

        public PostService(PostRepository repository) {
            this.repository = repository;
        }

        public Optional<Post> get(Long id) {
            return repository.findById(id);
        }

        public Post update(Post entity) {
            return repository.save(entity);
        }

        public void delete(Long id) {
            repository.deleteById(id);
        }

        public Page<Post> list(Pageable pageable) {
            return repository.findAll(pageable);
        }

        public Page<Post> list(Pageable pageable, Specification<Post> filter) {
            return repository.findAll(filter, pageable);
        }

        public int count() {
            return (int) repository.count();
        }

    public void save(Post newPost) {
        repository.save(newPost);
    }

    public List<Post> findAll() {
        return repository.findAll();
    }
}
