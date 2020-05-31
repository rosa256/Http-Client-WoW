package com.api.wow.auction.analysis.repositories;

import com.api.wow.auction.analysis.models.AuthenticationData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthenticationRepository extends JpaRepository<AuthenticationData, Integer> {

}
