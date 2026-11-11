package com.dropwear.user.repository;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.dropwear.user.domain.*;

public interface UserProfileRepository extends JpaRepository<UserProfile, UUID> {

    boolean existsByEmail(String email);
}
