package com.dropwear.catalog.repository;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.dropwear.catalog.domain.*;

public interface ProcessedEventRepository extends MongoRepository<ProcessedEvent, String> {
}
