package com.example.instagramscheduler.repository;

import com.example.instagramscheduler.model.Campain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CampainRepository extends JpaRepository<Campain, Long>, JpaSpecificationExecutor<Campain>{
}
