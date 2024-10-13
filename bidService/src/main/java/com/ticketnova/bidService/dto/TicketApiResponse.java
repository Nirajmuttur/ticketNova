package com.ticketnova.bidService.dto;

import lombok.Data;

@Data
public class TicketApiResponse<T> {
    private int statusCode;
    private String message;
    private boolean success;
    private T data;
}
