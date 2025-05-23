package com.itzarnabpal.URLShortener.Repositories;

import com.itzarnabpal.URLShortener.Models.URLModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface URLRepository extends MongoRepository<URLModel, String> {
    Optional<URLModel> findByShortCode(String shortCode);
}
