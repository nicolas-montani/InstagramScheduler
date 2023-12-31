package com.example.instagramscheduler.services;

import com.example.instagramscheduler.model.Campain;
import com.example.instagramscheduler.repository.CampainRepository;

import org.springframework.stereotype.Service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;


@Service
public class CampainService {

    private final CampainRepository repository;

    public CampainService(CampainRepository repository) {
        this.repository = repository;
    }

    public Optional<Campain> get(Long id) {
        return repository.findById(id);
    }

    public Campain update(Campain entity) {
        return repository.save(entity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Page<Campain> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<Campain> list(Pageable pageable, Specification<Campain> filter) {
        return repository.findAll(filter, pageable);
    }

    public int count() {
        return (int) repository.count();
    }

}
