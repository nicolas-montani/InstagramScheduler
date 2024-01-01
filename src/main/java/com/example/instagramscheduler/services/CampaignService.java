package com.example.instagramscheduler.services;

import com.example.instagramscheduler.model.Campaign;
import com.example.instagramscheduler.repository.CampaignRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;


@Service
public class CampaignService {

    private final CampaignRepository repository;

    public CampaignService(CampaignRepository repository) {
        this.repository = repository;
    }

    public Optional<Campaign> get(Long id) {
        return repository.findById(id);
    }

    public Campaign update(Campaign entity) {
        return repository.save(entity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Page<Campaign> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<Campaign> list(Pageable pageable, Specification<Campaign> filter) {
        return repository.findAll(filter, pageable);
    }

    public int count() {
        return (int) repository.count();
    }

    public void save(Campaign item) {
        repository.save(item);
    }

    public List<Campaign> findAll() {
        return repository.findAll();
    }
}
