package com.ticketnova.bidService.serviceClient;

import com.ticketnova.bidService.dto.TicketApiResponse;
import com.ticketnova.bidService.dto.TicketDTO;
import com.ticketnova.bidService.util.Response;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.UUID;

@Component
@AllArgsConstructor
public class TicketServiceClient {
    @Autowired
    private final RestTemplate restTemplate;

    public TicketApiResponse<TicketDTO> getTicket(UUID ticketId){
        String url = "http://localhost:8084/api/v1/ticket/" + ticketId;
        ResponseEntity<TicketApiResponse<TicketDTO>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<TicketApiResponse<TicketDTO>>() {});
        TicketApiResponse<TicketDTO> response = responseEntity.getBody();
        return response;
    }
}
