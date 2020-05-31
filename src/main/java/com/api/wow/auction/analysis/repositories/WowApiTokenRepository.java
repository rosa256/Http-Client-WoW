package com.api.wow.auction.analysis.repositories;

import com.api.wow.auction.analysis.models.AccessTokenData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WowApiTokenRepository extends JpaRepository<AccessTokenData, Integer> {
}
