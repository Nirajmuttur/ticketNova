package com.ticketnova.bidService.dto;

import com.ticketnova.bidService.entity.Status;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BidDTO {
    @NotNull(message = "Ticket Id is Required")
    private UUID ticketId;

    @NotNull(message = "Buyer Id is Required")
    private UUID buyerId;

    @NotNull(message = "Event Price is Required")
    @DecimalMin(value = "0.01", message = "Ticket price must be greater than zero.")
    private BigDecimal bidAmount;
}
