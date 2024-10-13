package com.ticketnova.bidService.service;

import com.ticketnova.bidService.dto.BidDTO;
import com.ticketnova.bidService.entity.Bids;

import java.util.UUID;

public interface BidService {
    Bids createBid(BidDTO bidDTO);

    Bids updateBid(BidDTO bidDTO, UUID bidId);
}
