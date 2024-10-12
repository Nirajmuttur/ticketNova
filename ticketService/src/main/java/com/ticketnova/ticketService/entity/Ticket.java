package com.ticketnova.ticketService.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "tickets")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String eventName;
    private String seatInfo;
    private Date eventDate;
    private BigDecimal price;
    private UUID sellerId;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private ValidationStatus validationStatus;

    @CreationTimestamp(source = SourceType.DB)
    private Date createdAt;

    @UpdateTimestamp(source = SourceType.DB)
    private Date updatedAt;
}
