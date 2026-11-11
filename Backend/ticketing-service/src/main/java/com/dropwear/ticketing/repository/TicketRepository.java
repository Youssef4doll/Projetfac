package com.dropwear.ticketing.repository;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.dropwear.ticketing.domain.*;

public interface TicketRepository extends MongoRepository<Ticket, String> {

    List<Ticket> findByCustomerId(String customerId);

    boolean existsByOrderIdAndCategoryAndStatusNot(String orderId, TicketCategory category, TicketStatus status);
}
