package com.ticketnova.bidService.controller;

import com.ticketnova.bidService.dto.BidDTO;
import com.ticketnova.bidService.entity.Bids;
import com.ticketnova.bidService.service.BidService;
import com.ticketnova.bidService.util.Response;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/bid")
@RequiredArgsConstructor
public class BidController {

    @Autowired
    private BidService bidService;

    @PostMapping("/create")
    public ResponseEntity<Response<Bids>> register(@Valid @RequestBody BidDTO bidDTO){
        Bids bid = bidService.createBid(bidDTO);
        Response<Bids> response = Response.successfulResponse("Bid created successfully", bid);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<Bids>> updateBid(@Valid @RequestBody BidDTO bidDTO, @PathVariable UUID id){
        Bids bid = bidService.updateBid(bidDTO,id);
        Response<Bids> response = Response.successfulResponse("Bid updated successfully", bid);
        return ResponseEntity.ok(response);
    }
}
