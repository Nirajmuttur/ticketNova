package com.ticketnova.ticketService.dto;

import com.ticketnova.ticketService.entity.Status;
import com.ticketnova.ticketService.entity.ValidationStatus;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Data
public class TicketDTO {
    @NotBlank(message = "Event Name is Required")
    @Size(min = 2, max = 100, message = "Event name must be between 2 and 100 characters.")
    private String eventName;

    @NotBlank(message = "Seat information is Required")
    @Size(min = 1, max = 50, message = "Seat information must be between 1 and 50 characters.")
    private String seatInfo;

    @NotNull(message = "Event Date is required")
    @FutureOrPresent(message = "Event date must be in the present or future.")
    private Date eventDate;

    @NotNull(message = "Event Price is Required")
    @DecimalMin(value = "0.01", message = "Ticket price must be greater than zero.")
    private BigDecimal price;

    @NotNull(message = "Seller Id is Required")
    private UUID sellerId;
}
