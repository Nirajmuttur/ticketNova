package com.ticketnova.ticketService.service;

import com.ticketnova.ticketService.dto.TicketDTO;
import com.ticketnova.ticketService.entity.Status;
import com.ticketnova.ticketService.entity.Ticket;
import com.ticketnova.ticketService.entity.ValidationStatus;
import com.ticketnova.ticketService.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService{
    @Autowired
    private TicketRepository ticketRepository;
    @Override
    public Ticket createTicket(TicketDTO ticketDTO) {

        var ticket = Ticket.builder()
                .eventName(ticketDTO.getEventName())
                .eventDate(ticketDTO.getEventDate())
                .sellerId(ticketDTO.getSellerId())
                .seatInfo(ticketDTO.getSeatInfo())
                .status(Status.VALIDATING)
                .validationStatus(ValidationStatus.PENDING)
                .price(ticketDTO.getPrice())
                .build();

        return ticketRepository.save(ticket);
    }

    @Override
    public List<Ticket> getNotExpiredAndValidTickets() {
        return ticketRepository.findNonExpiredAndValidTickets("VALID", new Date());
    }

    @Override
    public Optional<Ticket> getTicketById(UUID id) {
        Optional<Ticket> ticket = ticketRepository.findById(id);
        if(ticket.isEmpty()){
            throw new RuntimeException("Ticket Not Found");
        }
        return ticket;
    }

    @Override
    public Page<Ticket> getTicketBySellerId(UUID id, int pageNumber , int numberOfItems) {
        Pageable pageable = PageRequest.of(pageNumber,numberOfItems);
        Page<Ticket> ticket = ticketRepository.findBySellerId(id,pageable);
        if(ticket.isEmpty()){
            throw new RuntimeException("Seller Id Not Found");
        }
        return ticket;
    }
}
