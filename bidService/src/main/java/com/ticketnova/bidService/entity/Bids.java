package com.ticketnova.bidService.entity;

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
@Table(name = "bids")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Bids {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID ticketId;

    private UUID buyerId;

    private BigDecimal bidAmount;

    @Enumerated(EnumType.STRING)
    private Status bidStatus;

    @CreationTimestamp(source = SourceType.DB)
    private Date createdAt;

    @UpdateTimestamp(source = SourceType.DB)
    private Date updatedAt;
}
