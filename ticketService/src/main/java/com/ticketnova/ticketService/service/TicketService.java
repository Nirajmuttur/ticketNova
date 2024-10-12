package com.ticketnova.ticketService.service;

import com.ticketnova.ticketService.dto.TicketDTO;
import com.ticketnova.ticketService.entity.Ticket;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TicketService {
    Ticket createTicket(TicketDTO ticketDTO);

    List<Ticket> getNotExpiredAndValidTickets();

    Optional<Ticket> getTicketById(UUID id);

    Page<Ticket> getTicketBySellerId(UUID id, int pageNumber , int numberOfItems);
}
