package com.example.instagramscheduler.rest;

import com.example.instagramscheduler.model.Campaign;
import com.example.instagramscheduler.repository.CampaignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/campaigns")
public class CampaignController {

    @Autowired
    private CampaignRepository campaignRepository;

    @GetMapping
    public List<Campaign> getAllCampaigns() {
        return campaignRepository.findAll();
    }
    // Other endpoints (create, update, delete) can be added similarly
    @PostMapping
    public Campaign createCampaign(@RequestBody Campaign campaign) {
        return campaignRepository.save(campaign);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Campaign> getCampaignById(@PathVariable Long id) {
        Optional<Campaign> campaign = campaignRepository.findById(id);
        return campaign.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PutMapping("/{id}")
    public ResponseEntity<Campaign> updateCampaign(@PathVariable Long id, @RequestBody Campaign campaign) {
        if (!campaignRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        campaign.setId(id);
        Campaign updatedCampaign = campaignRepository.save(campaign);
        return ResponseEntity.ok(updatedCampaign);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCampaign(@PathVariable Long id) {
        if (!campaignRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        campaignRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}