package com.ticketnova.ticketService.controller;

import com.ticketnova.ticketService.dto.TicketDTO;
import com.ticketnova.ticketService.entity.Ticket;
import com.ticketnova.ticketService.service.TicketService;
import com.ticketnova.ticketService.util.Response;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/ticket")
@RequiredArgsConstructor
public class TicketController {
    @Autowired
    private TicketService ticketService;

    @PostMapping("/register")
    public ResponseEntity<Response<Ticket>> register(@Valid @RequestBody TicketDTO ticketDTO){
        Ticket ticket = ticketService.createTicket(ticketDTO);
        Response<Ticket> response = Response.successfulResponse("Ticket created successfully", ticket);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Response<List<Ticket>>> getValidTickets(){
        List<Ticket> ticket = ticketService.getNotExpiredAndValidTickets();
        Response<List<Ticket>> response = Response.successfulResponse("Non Expired Tickets", ticket);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<Ticket>> getTicketById(@PathVariable UUID id){
        Optional<Ticket> ticket = ticketService.getTicketById(id);
        Response<Ticket> response = Response.successfulResponse("Ticket By ID", ticket.orElse(null));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/u/{id}/{pageNumber}/{numberOfItems}")
    public ResponseEntity<Response<Page<Ticket>>> getTicketBySellerId(@PathVariable UUID id,@PathVariable int pageNumber , @PathVariable int numberOfItems){
        Page<Ticket> ticket = ticketService.getTicketBySellerId(id,pageNumber,numberOfItems);
        Response<Page<Ticket>> response = Response.successfulResponse("Ticket By ID", ticket);
        return ResponseEntity.ok(response);
    }
}
