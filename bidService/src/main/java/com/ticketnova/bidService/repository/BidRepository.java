package com.ticketnova.bidService.repository;

import com.ticketnova.bidService.entity.Bids;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BidRepository extends JpaRepository<Bids, UUID> {
    boolean existsByBuyerIdAndTicketId(UUID buyerId, UUID id);

    @Query(value = "SELECT * FROM BIDS b where b.ticket_id = :ticket_id ORDER BY b.bid_amount DESC LIMIT 1", nativeQuery = true)
    Optional<Bids> findHighestBidByTicketId(@Param("ticket_id") UUID ticketId);
}
