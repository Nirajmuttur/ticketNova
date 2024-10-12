package com.ticketnova.ticketService.repository;

import com.ticketnova.ticketService.entity.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, UUID> {
    @Query(value = "select * from tickets where validation_status=(?1) and event_date>(?2)",nativeQuery = true)
    List<Ticket> findNonExpiredAndValidTickets(String validationStatus, Date presentDate);

    Page<Ticket> findBySellerId(UUID id, Pageable pageable);
}
