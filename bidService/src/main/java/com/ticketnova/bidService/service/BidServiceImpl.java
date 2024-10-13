package com.ticketnova.bidService.service;

import com.ticketnova.bidService.dto.BidDTO;
import com.ticketnova.bidService.dto.TicketApiResponse;
import com.ticketnova.bidService.dto.TicketDTO;
import com.ticketnova.bidService.entity.Bids;
import com.ticketnova.bidService.entity.Status;
import com.ticketnova.bidService.repository.BidRepository;
import com.ticketnova.bidService.serviceClient.TicketServiceClient;
import com.ticketnova.bidService.util.Response;
import jakarta.validation.ValidationException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class BidServiceImpl implements BidService{
    @Autowired
    private BidRepository bidRepository;

    private final TicketServiceClient ticketServiceClient;

    private TicketDTO getTicketDTO(BidDTO bidDTO) {
        TicketApiResponse<TicketDTO> response = ticketServiceClient.getTicket(bidDTO.getTicketId());
        return response.getData();
    }
    @Override
    public Bids createBid(BidDTO bidDTO) {
        TicketDTO ticketDTO = getTicketDTO(bidDTO);
        validateTicket(ticketDTO);
        validateBidder(bidDTO.getBuyerId(), ticketDTO);

        updateCurrentHighestBid(bidDTO,ticketDTO);

        var bid = Bids.builder()
                .bidAmount(bidDTO.getBidAmount())
                .buyerId(bidDTO.getBuyerId())
                .ticketId(bidDTO.getTicketId())
                .bidStatus(Status.ACTIVE)
                .build();
        return bidRepository.save(bid);
    }



    @Override
    public Bids updateBid(BidDTO bidDTO, UUID bidId) {
        TicketDTO ticketDTO = getTicketDTO(bidDTO);
        Optional<Bids> existingBidOpt = bidRepository.findById(bidId);
        if(existingBidOpt.isEmpty()){
            throw new ValidationException("Bid is not present");
        }
        Bids existingBid = existingBidOpt.get();

        // Ensure the new bid amount is higher than the previous bid
        if (bidDTO.getBidAmount().compareTo(existingBid.getBidAmount()) <= 0) {
            throw new IllegalArgumentException("New bid amount must be higher than the previous bid.");
        }

        updateCurrentHighestBid(bidDTO,ticketDTO);

        // Update the bid amount
        existingBid.setBidAmount(bidDTO.getBidAmount());
        existingBid.setBidStatus(Status.ACTIVE);

        return bidRepository.save(existingBid);
    }
    
    private void updateCurrentHighestBid(BidDTO bidDTO, TicketDTO ticketDTO){
        // Check if the bid amount is greater than the current highest bid
        Optional<Bids> highestBidOpt = bidRepository.findHighestBidByTicketId(bidDTO.getTicketId());
        highestBidOpt.ifPresent(highestBid -> {
            if (bidDTO.getBidAmount().compareTo(highestBid.getBidAmount()) <= 0) {
                throw new IllegalArgumentException("Bid amount must be higher than the current highest bid.");
            }
        });

        // Check if the bid amount meets the minimum price threshold for the ticket
        if (bidDTO.getBidAmount().compareTo(ticketDTO.getPrice()) < 0) {
            throw new IllegalArgumentException("Bid amount must be at least equal to the ticket price.");
        }

        // Update the previous highest bid to "OUTBID" status
        if (highestBidOpt.isPresent()) {
            highestBidOpt.get().setBidStatus(Status.OUTBID);
            bidRepository.save(highestBidOpt.get());  // Persist the change
        }
    }

    private void validateTicket(TicketDTO ticketDTO){
        if(ticketDTO == null){
            throw new ValidationException("Ticket does not Exist");
        }
        if(!"ACTIVE".equals(ticketDTO.getStatus())){
            throw new ValidationException("Ticket is no longer available for bidding");
        }
        if(ticketDTO.getEventDate().before(new Date())){
            throw new ValidationException("Cannot place a bid for an event that has already occurred.");
        }
        if(!"VALID".equals(ticketDTO.getValidationStatus())){
            throw new ValidationException("Ticket is not Valid");
        }
    }

    private void validateBidder(UUID buyerId, TicketDTO ticketDTO) {
        if(buyerId.equals(ticketDTO.getSellerId())) {
            throw new ValidationException("Seller cannot place a bid on their own ticket.");
        }
    }
}
