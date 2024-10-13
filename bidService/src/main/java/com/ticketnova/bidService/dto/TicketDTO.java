package com.ticketnova.bidService.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Data
public class TicketDTO {
    private UUID id;
    private String eventName;
    private String seatInfo;
    private Date eventDate;
    private BigDecimal price;
    private UUID sellerId;
    private String status;
    private String validationStatus;
}
