package com.hse.somport.somport.entities.repositories;

import com.hse.somport.somport.dto.FeedConfigDetails;
import com.hse.somport.somport.entities.FeedConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface FeedConfigRepository extends JpaRepository<FeedConfigEntity, Long> {

    @Transactional
    @Modifying
    @Query("update FeedConfigEntity fce set fce.config = :feedConfig where fce.id = :configId")
    void updateFeedConfigById(FeedConfigDetails feedConfig, Long configId);
}
